package com.github.tuchinoko;

import com.github.tuchinoko.spi.ProcessorService;

/**
 * <p>
 * Tuchinokoの1処理の基本となるクラスです．
 * </p>
 * <p>
 * 1処理とは{@link TargetSource <code>TargetSource</code>}から順番に
 * {@link ProcessTarget <code>ProcessTarget</code>}を取り出して処理を施し，
 * {@link Destination <code>Destination</code>}に出力することを指します．
 * </p><p>
 * Tuchinokoでは1つの {@link TargetSource <code>TargetSource</code>}
 * を処理するために複数のProcessorが起動します． これを1セッションと呼びます． 
 * 各Processorは1セッションの間，重複しない名前を持ちます．
 * また，1つのProcessorオブジェクトは1セッションの間に複数回
 * {@link #execute <code>execute</code>}メソッドが呼び出されます．
 * </p>
 * 
 * @author Haruaki Tamada
 */
public interface Processor{
    /**
     * 1セッションの間，他と重複しない名前を返します．
     */
    public String getId();

    /**
     * 1セッションの間，他と重複しない名前を設定します．
     * @see #getId
     */
    public void setId(String id);

    /**
     * 処理内容を表す単純な名前を返す．
     */
    public String getProcessorName();

    /**
     * <p>
     * Processorの初期設定を行います．
     * </p><p>
     * 初期化のパラメータは{@link #getArguments <code>getArguments</code>}
     * で返される {@link Arguments <code>Arguments</code>}により行われます．
     * </p>
     */
    public void init() throws ProcessorException;

    /**
     * 実際に処理を行います．
     * 
     * @param source
     * @param dest
     * @throws ProcessorException
     */
    public void execute(TargetSource source, Destination dest) throws ProcessorException;

    /**
     * 終了処理を行います．
     * @throws ProcessorException
     */
    public void finish() throws ProcessorException;

    /**
     * サービスプロバイダを返します．
     * このメソッドは常にnullではない値を返します．
     */
    public ProcessorService getProvider();

    /**
     * この処理器の現在設定されているパラメータを返します．
     */
    public Arguments getArguments();

    /**
     * この処理器の処理内容を表すオブジェクトを返します．
     */
    public Summary getSummary();
}
