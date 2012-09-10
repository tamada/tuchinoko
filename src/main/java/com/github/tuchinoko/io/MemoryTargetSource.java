package com.github.tuchinoko.io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;

/**
 * <p>
 * メモリに格納された{@link ProcessTarget <code>ProcessTarget</code>}
 * を管理するTargetSourceです．
 * </p>
 *
 * @author Haruaki Tamada
 */
public class MemoryTargetSource implements TargetSource{
    private List<ProcessTarget> list = new ArrayList<ProcessTarget>();

    /**
     * 空のTargetSourceを構築します．
     */
    public MemoryTargetSource(){
    }

    /**
     * 指定されたProcessTargetを持つTargetSourceを構築します．
     */
    public MemoryTargetSource(ProcessTarget... targets){
        for(ProcessTarget target: targets){
            addTarget(target);
        }
    }

    /**
     * このオブジェクトに指定されたProcessTargetを追加します．
     */
    public final void addTarget(ProcessTarget target){
        ProcessTarget pt = target;
        if(pt.getTargetSource() != this){
            if(pt instanceof DelegateProcessTarget){
                ((DelegateProcessTarget)pt).setTargetSource(this);
            }
            else{
                pt = new DelegateProcessTarget(this, pt);
            }
        }
        list.add(pt);
    }

    /**
     * このオブジェクトに格納されているProcessTargetの数を返します．
     */
    public int getTargetCount(){
        return list.size();
    }

    /**
     * このTargetSourceの名前を返します．
     */
    @Override
    public String getName(){
        return getClass().getName() + ":" + System.identityHashCode(this);
    }

    /**
     * このオブジェクトに含まれるProcessTargetの列挙を返します．
     */
    @Override
    public Iterator<ProcessTarget> iterator(){
        return list.iterator();
    }

    /**
     * このTargetSourceを解放します．
     * この呼び出しにより，このオブジェクトに登録されている全てのProcessTargetが削除されます．
     */
    public void close(){
        list.clear();
    }

    /**
     * このTargetSourceが指定された文字列が表すデータを保持していればtrueを返します．
     */
    @Override
    public boolean contains(String target){
        boolean flag = false;
        for(ProcessTarget pt: list){
            if(pt.getName().equals(target)){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
