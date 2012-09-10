package com.github.tuchinoko.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.github.tuchinoko.Destination;
import com.github.tuchinoko.ProcessTarget;

/**
 * ディレクトリを出力先とする{@link Destination <code>Destination</code>}の実装クラスです．
 *
 * @author Haruaki Tamada
 */
public class DirectoryDestination extends AbstractDestination{
    private File base;

    /**
     * 基準となるディレクトリを指定してオブジェクトを構築します．
     * もし，baseが存在し，ディレクトリでない場合はIOExceptionが投げられます．
     * baseが存在しなかった場合は，適切なタイミングでディレクトリが作成されます．
     */
    public DirectoryDestination(File base) throws IOException{
        if(base.exists() && !base.isDirectory()){
            throw new IOException(base + ": not directory");
        }
        this.base = base;
    }

    /**
     * <p>
     * 引数で与えられた文字列に出力するための出力ストリームを作成して，返します．
     * </p><p>
     * 引数にFile.separatorが含まれていれば，ディレクトリ階層として扱われます．
     * ディレクトリが存在しない場合は，自動的に作成されます．
     * </p><p>
     * ディレクトリ作成に失敗した場合はIOExceptionが投げられます．
     * 引数にnullが与えられた場合はNullPointerExceptionが投げられます．
     * </p>
     *
     * @param name 出力先のファイル名．
     * @throws IOException ディレクトリ作成に失敗した場合
     */
    @Override
    public OutputStream getOutput(String name) throws IOException{
	if(name == null){
	    throw new NullPointerException();
	}
        File file = new File(base, name);
        File parent = file.getParentFile();
        if(!parent.exists()){
            boolean flag = parent.mkdirs();
            if(!flag){
                throw new IOException(parent.getPath() + ": mkdir failed");
            }
        }
        return new FileOutputStream(file);
    }

    /**
     * <p>
     * 引数で与えられたProcessTargetを出力するための出力ストリームを作成して，返します．
     * </p><p>
     * targetの{@link ProcessTarget#getName <code>getName</code>}
     * メソッドで得られる文字列をもとに出力先が決められます．
     * その文字列にFile.separatorが含まれていれば，ディレクトリ階層として扱われます．
     * ディレクトリが存在しない場合は，自動的に作成されます．
     * </p><p>
     * ディレクトリ作成に失敗した場合はIOExceptionが投げられます．
     * 引数にnullが与えられた場合はNullPointerExceptionが投げられます．
     * </p>
     * 
     * @param target 出力するProcessTarget．
     * @throws IOException 既にcloseメソッドが呼ばれ，出力ストリームが閉じれているとき．
     */
    @Override
    public OutputStream getOutput(ProcessTarget target) throws IOException{
	if(target == null){
	    throw new NullPointerException();
	}
        return getOutput(target.getName());
    }

    /**
     * 何も行いません．
     */
    @Override
    public void close(){
    }
}
