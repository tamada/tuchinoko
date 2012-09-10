package com.github.tuchinoko.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.github.tuchinoko.Destination;
import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;

/**
 * <p>
 * DestinationとTargetSourceを繋ぐためのDestinationの実装クラスです．
 * </p><p>
 * Destinationとして扱われますが，{@link #getTargetSource <code>getTargetSource</code>}
 * メソッドを呼び出してTargetSourceを取り出せます．
 * </p>
 *
 * @author Haruaki Tamada
 */
public class GlueDestination implements Destination{
    private MemoryTargetSource source = new MemoryTargetSource();
    private boolean closed = false;

    /**
     * <p>
     * このオブジェクトが持つTargetSourceを返します．
     * </p><p>
     * 返されるTargetSourceには{@link #getOutput <code>getOutput</code>}
     * メソッドなどを使って出力したデータが含まれます．
     * </p><p>
     * このメソッドは常に有効なTargetSourceを返します．
     * </p>
     */
    public TargetSource getTargetSource(){
        return source;
    }

    /**
     * <p>
     * 引数で与えられた文字列に出力するための出力ストリームを作成して，返します．
     * 返される出力ストリームは，必要なデータを出力し終えたら閉じてください．
     * </p>
     *
     * @param name 出力する名前．
     */
    @Override
    public OutputStream getOutput(final String name) throws IOException{
        return new ByteArrayOutputStream(){
            @Override
            public void close() throws IOException{
                super.close();

                byte[] data = toByteArray();
                source.addTarget(new ByteArrayProcessTarget(source, name, data));
            }
        };
    }

    /**
     * <p>
     * 引数で与えられたProcessTargetを出力するための出力ストリームを作成して，返します．
     * 返される出力ストリームは，必要なデータを出力し終えたら閉じてください．
     * </p>
     *
     * @param target 出力するProcessTarget．
     */
    @Override
    public OutputStream getOutput(ProcessTarget target) throws IOException{
        return getOutput(target.getName());
    }

    /**
     * このオブジェクトが持つTargetSourceにそのまま引数のProcessTargetを追加します．
     */
    @Override
    public void output(ProcessTarget target) throws IOException{
        source.addTarget(target);
    }

    /**
     * 何も行いません．
     */
    @Override
    public synchronized void close(){
        closed = true;
    }

    @Override
    public boolean isClosed(){
        return closed;
    }
}
