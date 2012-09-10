package com.github.tuchinoko;

/**
 * 指定されたProcessorが見つからないときに投げられる例外クラスです．
 *
 * @author Haruaki Tamada
 */
public class UnknownProcessorException extends ProcessorBuildException{
    private static final long serialVersionUID = -6874084548050295366L;

    /**
     * メッセージと原因を指定せずに例外クラスを構築します．
     */
    public UnknownProcessorException(){
        super();
    }

    /**
     * 指定されたメッセージと原因を指定して，例外クラスを構築します．
     */
    public UnknownProcessorException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * 詳細メッセージを指定して例外クラスを構築します．
     * 原因は指定されません．
     */
    public UnknownProcessorException(String message){
        super(message);
    }

    /**
     * 原因を指定して例外クラスを構築します．
     * メッセージは指定されません．
     */
    public UnknownProcessorException(Throwable cause){
        super(cause);
    }
}
