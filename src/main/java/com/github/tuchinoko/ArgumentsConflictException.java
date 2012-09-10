package com.github.tuchinoko;

import java.util.Arrays;

/**
 * Processorに渡す引数の値が衝突した場合に投げられる例外クラスです．
 * 詳細は{@link Context <code>Context</code>}クラスをご覧ください．
 */
public class ArgumentsConflictException extends ProcessorException{
    private static final long serialVersionUID = -8371652147928164195L;

    /**
     * デフォルトコンストラクタ．
     * 詳細メッセージを指定せずにオブジェクトを作成します．
     */
    public ArgumentsConflictException(){
        super();
    }

    /**
     * 衝突した引数の名前のリストをもとにした詳細メッセージを持つ
     * 例外オブジェクトを作成します．
     */
    public ArgumentsConflictException(String[] conflicts){
        super(Arrays.toString(conflicts));
    }
}
