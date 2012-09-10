package com.github.tuchinoko;

import java.io.IOException;
import java.util.Iterator;

/**
 * <p>
 * Tuchinokoへの入力であるProcessTargetの集合を表します．
 * </p><p>
 * 入力は一般的にクラスファイルが格納されたJarファイル，
 * もしくは指定されたディレクトリ以下のクラスファイルです．
 * </p>
 *
 * @author Haruaki Tamada
 */
public interface TargetSource extends Iterable<ProcessTarget>{
    /**
     * このオブジェクトを互いに区別する名前を返します．
     * ただし，この名前は他のTargetSourceと重複する可能性があります．
     */
    public String getName();

    /**
     * このオブジェクトが持つProcessTargetの列挙を返します．
     */
    @Override
    public Iterator<ProcessTarget> iterator();

    /**
     * このオブジェクトを閉じます．
     * このメソッド呼び出し以降，このオブジェクトの他のメソッドの動作は保証されません．
     */
    public void close() throws IOException;

    /**
     * このTargetSourceオブジェクトに指定されたファイルが含まれていればtrueを返します．
     * @param target 含まれているか判断するファイル．
     * @return このTargetSourceオブジェクトにtargetが含まれていればtrue，含まれていなければfalse．
     */
    public boolean contains(String target);
}
