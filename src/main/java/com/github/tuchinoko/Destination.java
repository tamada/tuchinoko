package com.github.tuchinoko;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 出力先を表すインターフェースです．
 *
 * @author Haruaki Tamada
 */
public interface Destination{
    /**
     * クラス名から出力先を決定し，出力ストリームを返します．
     * 返された出力ストリームは必要がなくなればcloseを呼び出して閉じてください．
     */
    OutputStream getOutput(String className) throws IOException;

    /**
     * {@link ProcessTarget}から出力先を決定し，出力ストリームを返します．
     * 返された出力ストリームは必要がなくなればcloseを呼び出して閉じてください．
     */
    OutputStream getOutput(ProcessTarget target) throws IOException;

    /**
     * {@link #getOutput(ProcessTarget) <code>getOutput</code>}から出力先を取得し，
     * ProcessTargetの内容をそのまま出力ストリームに書き出します．
     */
    void output(ProcessTarget target) throws IOException;

    /**
     * この出力先を閉じます．
     * このメソッドが呼び出された後は，{@link #getOutput(String)
     * <code>getOutput</code>}, {@link #output <code>output</code>}
     * メソッドは正常に処理しない場合があります．
     */
    void close() throws IOException;

    /**
     * このメソッドが閉じられているかを返します．
     * このメソッドがtrueを返す場合，他のメソッドの動作は保証されません．
     * @return この出力先が閉じられていればtrue，出力可能であればfalseを返す．
     */
    boolean isClosed();
}
