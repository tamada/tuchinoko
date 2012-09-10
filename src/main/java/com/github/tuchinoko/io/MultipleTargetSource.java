package com.github.tuchinoko.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;
import com.github.tuchinoko.utils.NullIterator;

/**
 * <p>
 * 複数のTargetSourceを1つのTargetSourceとして扱うクラスです．
 * </p>
 * @author Haruaki Tamada
 */
public class MultipleTargetSource implements TargetSource{
    private List<TargetSource> list = new ArrayList<TargetSource>();

    /**
     * 指定されたTargetSourceのリストをまとめるTargetSourceを構築します．
     */
    public MultipleTargetSource(TargetSource... sources){
        for(TargetSource source: sources){
            list.add(source);
        }
    }

    /**
     * このTargetSourceの名前を返します．
     * このオブジェクトが保持する全てのTargetSourceの名前をコンマ「,」
     * で区切った文字列がこのオブジェクトの名前になります．
     */
    @Override
    public String getName(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            if(i != 0){
                sb.append(", ");
            }
            sb.append(list.get(i).getName());
        }
        return new String(sb);
    }

    /**
     * このオブジェクトが保持するProcessTargetの列挙を返します．
     * 全てのTargetSourceから順にProcessTargetを取り出す列挙を返します．
     */
    @Override
    public Iterator<ProcessTarget> iterator(){
        return new MultipleTargetSourceIterator(this, list);
    }

    /**
     * このオブジェクトが保持する全てのTargetSourceのcloseメソッドを呼び出します．
     */
    @Override
    public void close() throws IOException{
        for(TargetSource target: list){
            target.close();
        }
    }

    /**
     * このTargetSourceが指定された文字列が表すデータを保持していればtrueを返します．
     */
    @Override
    public boolean contains(String target){
        boolean flag = false;
        for(TargetSource source: list){
            if(source.contains(target)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    private static final class MultipleTargetSourceIterator implements Iterator<ProcessTarget>{
        private List<TargetSource> list;
        private Iterator<ProcessTarget> currentIterator;
        private int current = 1;
        private TargetSource source;

        public MultipleTargetSourceIterator(TargetSource source, List<TargetSource> list){
            this.source = source;
            this.list = list;
            if(list.size() > 0){
                currentIterator = list.get(0).iterator();
            }
            else{
                currentIterator = new NullIterator<ProcessTarget>();
            }
        }

        @Override
        public boolean hasNext(){
            while(current < list.size()){
                boolean next = currentIterator.hasNext();
                if(next){
                    return true;
                }
                currentIterator = list.get(current).iterator();
                current++;
            }
            return false;
        }

        @Override
        public ProcessTarget next(){
            if(!currentIterator.hasNext()){
                while(current < list.size()){
                    currentIterator = list.get(current).iterator();
                    if(currentIterator.hasNext()){
                        return buildProcessTarget(source, currentIterator.next());
                    }
                    current++;
                }
                throw new NoSuchElementException();
            }
            return buildProcessTarget(source, currentIterator.next());
        }

        @Override
        public void remove(){
        }

        private ProcessTarget buildProcessTarget(TargetSource source, ProcessTarget target){
            if(target.getTargetSource() != source){
                if(target instanceof DelegateProcessTarget){
                    ((DelegateProcessTarget)target).setTargetSource(source);
                }
                else{
                    target = new DelegateProcessTarget(source, target);
                }
            }
            return target;
        }
    }
}
