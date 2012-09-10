package com.github.tuchinoko;

/**
 * Tuchinokoでの処理が何らかの原因により失敗したことを表す例外クラスです．
 *
 * @author Haruaki Tamada
 */
public class ProcessorException extends Exception{
    private static final long serialVersionUID = 1708707816870023098L;

    /**
     * メッセージと原因を指定せずに例外クラスを構築します．
     */
    public ProcessorException(){
        super();
    }

    /**
     * 指定されたメッセージと原因を指定して，例外クラスを構築します．
     */
    public ProcessorException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * 詳細メッセージを指定して例外クラスを構築します．
     * 原因は指定されません．
     */
    public ProcessorException(String message){
        super(message);
    }

    /**
     * 原因を指定して例外クラスを構築します．
     * メッセージは指定されません．
     */
    public ProcessorException(Throwable cause){
        super(cause);
    }

}
