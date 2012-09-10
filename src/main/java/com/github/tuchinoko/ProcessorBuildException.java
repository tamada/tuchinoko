package com.github.tuchinoko;

/**
 * Processorの構築に失敗したことを表す例外クラスです．
 *
 * @author Haruaki Tamada
 */
public class ProcessorBuildException extends ProcessorException{
    private static final long serialVersionUID = 8227335530012426657L;

    /**
     * メッセージと原因を指定せずに例外クラスを構築します．
     */
    public ProcessorBuildException(){
        super();
    }

    /**
     * 指定されたメッセージと原因を指定して，例外クラスを構築します．
     */
    public ProcessorBuildException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * 詳細メッセージを指定して例外クラスを構築します．
     * 原因は指定されません．
     */
    public ProcessorBuildException(String message){
        super(message);
    }

    /**
     * 原因を指定して例外クラスを構築します．
     * メッセージは指定されません．
     */
    public ProcessorBuildException(Throwable cause){
        super(cause);
    }
}
