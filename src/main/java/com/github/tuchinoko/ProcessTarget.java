package com.github.tuchinoko;

import java.io.IOException;
import java.io.InputStream;

/**
 * Tuchinokoの処理対象となるものを表すためのインターフェースです．
 *
 * @author Haruaki Tamada
 */
public interface ProcessTarget{
    /**
     * <p>
     * このProcessTargetの名前を返します．
     * １セッションの間，このメソッドが返す名前と，このオブジェクトが所属するTargetSourceによって，
     * ProcessTargetを一意に特定できます．
     * </p>
     *
     * @return このProcessTargetの名前
     */
    public String getName();

    /**
     * <p>
     * このProcessTargetがクラスファイルであった場合，クラス名を返します．
     * このメソッドで得られた名前は他のProcessTargetと重複する可能性があります．
     * </p><p>
     * このProcessTargetがクラスファイルでない場合(getTypeがTargetType.CLASS_FILE以外を返す場合)，
     * このメソッドはIllegalStateExceptionが投げられます．
     * </p>
     * 
     * @return このProcessTargetが表すクラスの名前
     */
    public String getClassName();

    /**
     * このProcessTargetのデータを読み込むための入力ストリームを返します．
     * 
     * @return 入力ストリーム
     * @throws IOException 入出力エラーが起こった場合
     */
    public InputStream getSource() throws IOException;

    /**
     * このProcessTargetの種類を返します．
     *
     * @return このオブジェクトの種類
     * @see TargetType
     */
    public TargetType getType();

    /**
     * このProcessTargetが所属するProcessTargetを返します．
     */
    public TargetSource getTargetSource();
}
