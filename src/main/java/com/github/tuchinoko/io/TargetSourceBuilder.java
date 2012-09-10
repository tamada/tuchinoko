package com.github.tuchinoko.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;
import com.github.tuchinoko.utils.NullIterator;

/**
 * 与えられた文字列配列や，リストに格納された文字列から
 * {@link TargetSource <code>TargetSource</code>}
 * オブジェクトを構築するためのクラス．
 *
 * @author Haruaki Tamada
 */
public class TargetSourceBuilder{
    /**
     * 与えられた文字列配列の各要素に対応するファイルを読み込むための
     * TargetSourceを作成し，返します．
     * 
     * @see DirectoryTargetSource
     * @see JarFileTargetSource
     * @see FileProcessTarget
     * @see MultipleTargetSource
     */
    public TargetSource build(String[] targets) throws IOException{
        List<TargetSource> list = new ArrayList<TargetSource>();
        for(String target: targets){
            File file = new File(target);
            if(file.isDirectory()){
                list.add(new DirectoryTargetSource(file));
            }
            else if(file.getName().endsWith(".jar") || file.getName().endsWith(".zip")){
                list.add(new JarFileTargetSource(file));
            }
            else if(file.getName().endsWith(".class")){
                MemoryTargetSource source = new MemoryTargetSource();
                source.addTarget(new FileProcessTarget(source, new File(target)));
                list.add(source);
            }
        }
        return build(list);
    }

    private TargetSource build(List<TargetSource> list){
        TargetSource source;
        if(list.size() == 0){
            source = new NullTargetSource();
        }
        else if(list.size() == 1){
            source = list.get(0);
        }
        else{
            source = new MultipleTargetSource(list.toArray(new TargetSource[list.size()]));
        }
        return source;
    }

    private static final class NullTargetSource implements TargetSource{
        @Override
        public String getName(){
            return "";
        }

        @Override
        public Iterator<ProcessTarget> iterator(){
            return new NullIterator<ProcessTarget>();
        }

        @Override
        public void close(){
        }

        @Override
        public boolean contains(String target){
            return false;
        }
    }
}
