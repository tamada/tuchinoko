package com.github.tuchinoko.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

import com.github.tuchinoko.Destination;
import com.github.tuchinoko.ProcessTarget;

/**
 * Jarファイルを出力先とする{@link Destination <code>Destination</code>}の実装クラスです．
 *
 * @author Haruaki Tamada
 */
public class JarFileDestination extends AbstractDestination{
    private File file;
    private JarOutputStream jarOut;
    private boolean closed = false;
    private Manifest manifest;

    /**
     * 出力先のファイルを指定してオブジェクトを構築します．
     * @param file 出力するjarファイルのパス．
     */
    public JarFileDestination(File file){
        this.file = file;
    }

    /**
     * 出力先のファイルとマニフェストを指定してオブジェクトを構築します．
     * @param file 出力するjarファイルのパス．
     * @param manifest 出力するjarファイルのマニフェストファイル．
     */
    public JarFileDestination(File file, Manifest manifest){
        this.file = file;
        this.manifest = manifest;
    }

    /**
     * <p>
     * 引数で与えられた文字列に出力するための出力ストリームを作成して，返します．
     * 返される出力ストリームに必要なデータを出力し終えたら，閉じてください．
     * </p><p>
     * 引数に「/」が含まれていれば，jarファイル内のディレクトリ階層として扱われます．
     * ディレクトリが存在しない場合は，自動的に作成されます．
     * </p><p>
     * 既に出力ストリームが閉じられていた場合
     * ({@link #close <code>close</code>}メソッドが呼び出されていた場合)
     * はIOExceptionが投げられます．
     * 引数にnullが与えられた場合はIllegalArgumentExceptionが投げられます．
     * </p>
     *
     * @param name 出力するファイル名(jarファイルのエントリ)．
     * @throws IOException 既にcloseメソッドが呼ばれ，出力ストリームが閉じられたとき．
     */
    @Override
    public OutputStream getOutput(String name) throws IOException{
	if(closed){
	    throw new IOException("already closed");
	}
	if(name == null){
	    throw new IllegalArgumentException();
	}
        if(jarOut == null){
            if(manifest != null){
                jarOut = new JarOutputStream(new FileOutputStream(file), manifest);
            } else{
                jarOut = new JarOutputStream(new FileOutputStream(file));
            }
        }
        ZipEntry entry = new ZipEntry(name);
        jarOut.putNextEntry(entry);
        return new FilterOutputStream(jarOut){
            @Override
            public void close() throws IOException{
                jarOut.closeEntry();
            }
        };
    }

    /**
     * <p>
     * 引数で与えられたProcessTargetを出力するための出力ストリームを作成して，返します．
     * 返される出力ストリームに必要なデータを出力し終えたら，閉じてください．
     * </p><p>
     * targetの{@link ProcessTarget#getName <code>getName</code>}
     * メソッドで得られる文字列をもとに出力先が決められます．
     * 引数に「/」が含まれていれば，jarファイル内のディレクトリ階層として扱われます．
     * ディレクトリが存在しない場合は，自動的に作成されます．
     * </p><p>
     * 既に出力ストリームが閉じられていた場合
     * ({@link #close <code>close</code>}メソッドが呼び出されていた場合)
     * はIOExceptionが投げられます．
     * 引数にnullが与えられた場合はIllegalArgumentExceptionが投げられます．
     * </p>
     *
     * @param target 出力するファイル名(jarファイルのエントリ)．
     * @throws IOException 既にcloseメソッドが呼ばれ，出力ストリームが閉じられているとき．
     */
    @Override
    public OutputStream getOutput(ProcessTarget target) throws IOException{
        return getOutput(target.getName());
    }

    /**
     * 出力するjarファイルを閉じて，出力を完了します．
     * このメソッド呼び出し以降，このオブジェクトの全てのメソッドはIOExceptionが投げられるようになります．
     * @throws IOException クローズ時にI/Oエラーが発生した場合，もしくは既にcloseメソッドが呼び出されている場合．
     */
    @Override
    public synchronized void close() throws IOException{
	if(closed){
	    throw new IOException("already closed");
	}
        if(jarOut != null && !closed){
            jarOut.close();
	    closed = true;
        }
    }

    /**
     * このメソッドが閉じられているかを返します．
     * このメソッドがtrueを返す場合，他のメソッドの動作は保証されません．
     * @return この出力先が閉じられていればtrue，出力可能であればfalseを返す．
     */
    public boolean isClosed(){
	return closed;
    }
}
