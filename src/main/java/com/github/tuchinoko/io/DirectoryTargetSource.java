package com.github.tuchinoko.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;

/**
 * <p>
 * ディレクトリを対象としたTargetSourceです．
 * 特定のディレクトリ以下の全てのファイルをProcessTargetとするTargetSourceを表します．
 * </p>
 *
 * @author Haruaki Tamada
 */
public class DirectoryTargetSource implements TargetSource{
    private File file;

    /**
     * <p>
     * 指定されたディレクトリ以下の全てのファイルをProcessTargetとするオブジェクトを構築します．
     * </p><p>
     * fileがディレクトリでなかった場合はIOExceptionが投げられます．
     * 引数がnullの場合はNullPointerExceptionが投げられます．
     * </p>
     */
    public DirectoryTargetSource(File file) throws IOException{
        if(file == null){
            throw new NullPointerException();
        }
        if(!file.isDirectory()){
            throw new IOException(file.getName() + ": not directory");
        }
        this.file = file;
    }

    /**
     * 基準となるディレクトリ名をこのオブジェクトの名前として返します．
     */
    @Override
    public String getName(){
        return file.getPath();
    }

    /**
     * このディレクトリ以下の全てのファイルを列挙する列挙子を返します．
     */
    @Override
    public Iterator<ProcessTarget> iterator(){
        final List<File> list = new ArrayList<File>();
        listFiles(list, file);

        return new Iterator<ProcessTarget>(){
            private Iterator<File> iterator = list.iterator();

            @Override
            public boolean hasNext(){
                return iterator.hasNext();
            }

            @Override
            public ProcessTarget next(){
                File target = iterator.next();
                String name = target.getPath();
                name = name.substring(file.getPath().length());
                if(name.startsWith(File.separator)){
                    name = name.substring(1);
                }
                name = name.replace(File.separatorChar, '/');

                return new FileProcessTarget(DirectoryTargetSource.this, name, target);
            }

            @Override
            public void remove(){
            }
        };
    }

    /**
     * このTargetSourceが指定されたファイルを保持していればtrueを返します．
     */
    @Override
    public boolean contains(String target){
        File targetFile = new File(file, target);
        return targetFile.exists();
    }

    /**
     * 何も行いません．
     */
    public void close(){
        // do nothing...
    }

    private void listFiles(List<File> list, File base){
        if(base.isDirectory()){
            File[] files = base.listFiles();
            for(File file: files){
                listFiles(list, file);
            }
        }
        else{
            list.add(base);
        }
    }
}
