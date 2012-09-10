package com.github.tuchinoko;

import com.github.tuchinoko.spi.ProcessorService;

/**
 * <p>
 * 抽象処理器クラスです．{@link Processor <code>Processor</code>}にライフサイクルの概念が導入されています．
 * </p><p>
 * ライフサイクルは{@link Stage}クラスで表され，以下のように状態が遷移します．
 * <pre> UNINITIALIZED
 *     ↓ {@link #init() <code>init</code>}
 * INITIALIZED
 *     ↓ <em>{@link #prepare(Arguments) <code>prepare</code>}</em>
 *     ↓ {@link #execute(TargetSource, Destination) <code>execute</code>}
 * PROCESSING
 *     ↓ <em>{@link #perform(TargetSource, Destination) <code>perform</code>}</em>
 * PROCESSED
 *     ↓ {@link #finish() <code>finish</code>}
 *     ↓ <em>{@link #summarize() <code>summarize</code>}</em>
 * FINISHED</pre>
 * <p>
 * 矢印の右に書かれている文字はメソッドを表しており，
 * そのメソッドが呼び出されることで，ライフサイクルの状態が遷移します．
 * 斜体で示されているメソッドが抽象メソッドであり，サブクラスでオーバーライドしなければならないメソッドです．
 * その他のメソッドはfinal宣言されているため，オーバーライドできません．
 * なお，状態は以降，ステージ，もしくはstageとして表記されます．
 * </p><p>
 * このオブジェクトが作成された直後のステージはUNINITIALIZEDです．
 * initメソッドが呼び出されることにより，INITIALIZEDに移行します．
 * なお，initメソッドからprepareメソッドを呼び出しますが，
 * prepareメソッド呼び出しの直前にINITIALIZEDにステージは移行します．
 * 続いて，executeメソッドが呼び出され，PROCESSINGステージに移行し，
 * 自動的にperformメソッドが呼び出されます．
 * そして，performメソッドの呼び出しが終了すると，PROCESSEDステージに移行します．
 * 最後にfinishメソッドが呼び出され，finishメソッドからsummarizeメソッドが呼び出されます．
 * summarizeメソッドの呼び出しが終了すると，FINISHEDステージに移行します．
 * </p><p>
 * この処理器には5つのステージが存在しますが，それぞれのステージで呼び出せるメソッドは限定されています．
 * 呼び出し可能か否かを表したのが以下の表です．
 * </p><p>
 * 縦の列がステージを表しており，横の行が各メソッドを表しています．
 * ○のセルがあるのは，その行にあるメソッドがその列のステージのときに呼び出せることを表しています．
 * ×のセルの場合は，IllegalStateException が投げられます．
 * </p>
 * <table border="1">
 *   <thead>
 *     <th></th>
 *     <th></th>
 *     <th>UNINITIALIZED</th>
 *     <th>INITIALIZED</th>
 *     <th>PROCESSING</th>
 *     <th>PROCESSED</th>
 *     <th>FINISHED</th>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td rowspan="6">情報取得</td>
 *       <td>{@link #getProcessorName <code>getProcessorName</code>}</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #getProvider <code>getProvider</code>}</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #getCurrentStage <code>getCurrentStage</code>}</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #getId <code>getId</code>}</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #getArguments <code>getArguments</code>}</td>
 *       <td>○</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #getSummary <code>getSummary</code>}</td>
 *       <td>×</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *     </tr>
 *     <tr>
 *       <td rowspan="2">情報設定</td>
 *       <td>{@link #setId <code>setId</code>}</td>
 *       <td>○</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #putEntry <code>putEntry</code>}</td>
 *       <td>×</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>○</td>
 *       <td>×</td>
 *     </tr>
 *     <tr>
 *       <td rowspan="3">処理メソッド</td>
 *       <td>{@link #init <code>init</code>}</td>
 *       <td>○</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #execute <code>execute</code>}</td>
 *       <td>×</td>
 *       <td>○</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #finish <code>finish</code>}</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>○</td>
 *       <td>×</td>
 *     </tr>
 *     <tr>
 *       <td rowspan="3">オーバーライド</td>
 *       <td>{@link #prepare <code>prepare</code>}</td>
 *       <td>×</td>
 *       <td>○</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #perform <code>perform</code>}</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>○</td>
 *       <td>×</td>
 *       <td>×</td>
 *     </tr>
 *     <tr>
 *       <td>{@link #summarize <code>summarize</code>}</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>×</td>
 *       <td>○</td>
 *       <td>×</td>
 *     </tr>
 *   </tbody>
 * </table>
 * 
 * @author Haruaki Tamada
 */
public abstract class AbstractProcessor implements Processor{
    private Arguments arguments;
    private Summary summary;
    private ProcessorService provider;
    private Stage stage = Stage.UNINITIALIZED;
    private String id;

    /**
     * 指定されたサービスプロバイダをもとに処理器オブジェクトを作成します．
     * 
     * @param provider サービスプロバイダ
     */
    protected AbstractProcessor(ProcessorService provider){
        this.provider = provider;
        this.arguments = provider.getDefaultArguments();
    }

    /**
     * この処理器の名前を返します．
     * 
     * @see ProcessorService#getProcessorName
     */
    @Override
    public final String getProcessorName(){
        return getProvider().getProcessorName();
    }

    /**
     * 現在のステージを返します．
     * @return 現在のステージ．
     */
    public final Stage getCurrentStage(){
        return stage;
    }

    /** 
     * <p>
     * Idを設定します．
     * </p><p>
     * Idは1セッションの間に，処理器を互いに区別するための文字列です．
     * </p><p>
     * このメソッドはステージがUNINITIALIZEDの時だけ呼び出せます．
     * ステージがUNINITIALIZED以外のときには，IllegalStateException が投げられます．
     * </p>
     *
     * @throws IllegalStateException 状態がUNINITIALIZED以外のときに，このメソッドが呼び出された場合．
     */
    public final void setId(String id){
        if(stage != Stage.UNINITIALIZED){
            throw new IllegalStateException("expects UNINITIALIZED, but " + stage);
        }
        this.id = id;
    }

    /**
     * <p>
     * 設定されたIdを返します．
     * Idが設定されていない場合({@link #setId <code>setId</code>}
     * が一度も呼び出されていない場合)はnullを返します．
     * </p><p>
     * このメソッドは例外を投げません．
     * </p>
     */
    public final String getId(){
        return id;
    }

    /**
     * <p>
     * 初期設定を行います．
     * ユーザ独自の初期設定を行う場合は {@link #prepare(Arguments) <code>prepare</code>}
     * メソッドをオーバーライドしてください．
     * このメソッドはfinal宣言されていますので，オーバーライドできません．
     * </p><p>
     * このメソッド呼び出し前は{@link Stage#UNINITIALIZED <code>UNINITIALIZED</code>}であり，
     * 呼び出し後は {@link Stage#INITIALIZED <code>INITIALIZED</code>}になります．
     * そうでない場合は，<code>IllegalStateException</code>が投げられます．
     * </p>
     * 
     * @throws IllegalStateException 適切な状態でないときにこのメソッドが呼び出された場合．
     */
    @Override
    public final synchronized void init() throws ProcessorException{
        if(stage != Stage.UNINITIALIZED){
            throw new IllegalStateException("expects UNINITIALIZED, but " + stage);
        }
        summary = new Summary(getId());
        stage = Stage.INITIALIZED;
        prepare(arguments);
    }

    /**
     * <p>
     * ユーザ定義の初期設定を行うメソッドです．引数に与えられた{@link Arguments <code>Arguments</code>}
     * に従って処理を行うよう処理器を設定しなければいけません．
     * </p><p>
     * 初期設定時に例外が起こった場合は，ProcessorExceptionを投げるよう実装してください．
     * なお，このメソッドが呼び出される直前にステージはINITIALIZEDに変更されています．
     * </p>
     * @param args 処理器に与えるパラメータ．
     */
    protected abstract void prepare(Arguments args) throws ProcessorException;

    /**
     * <p>
     * 処理を行うためのメソッドです．
     * </p><p>
     * 具体的な処理内容は{@link #perform <code>perform</code>}メソッドを
     * オーバーライドしてください．
     * </p>
     * 
     * @throws IllegalStateException 適切な状態でないときにこのメソッドが呼び出された場合．
     */
    @Override
    public void execute(TargetSource source, Destination dest) throws ProcessorException{
        if(stage != Stage.INITIALIZED){
            throw new IllegalStateException("expects INITIALIZED, but " + stage);
        }
        stage = Stage.PROCESSING;
        try{
            perform(source, dest);
        } finally{
            stage = Stage.PROCESSED;
        }
    }

    /**
     * サブクラスでこのメソッドをオーバーライドして実際の処理を実装してください．
     */
    protected abstract void perform(TargetSource source, Destination dest) throws ProcessorException;

    /**
     * ユーザ定義の終了処理を行うメソッドです．この処理器で使用したリソースを適切に開放しなければいけません．
     * 終了処理時に例外が起こった場合は，ProcessorExceptionを投げるよう実装してください．
     */
    protected abstract void summarize() throws ProcessorException;

    /**
     * <p>
     * 終了処理を行います．
     * </p><p>
     * ユーザ独自の終了処理を行う場合は {@link #summarize() <code>summarize</code>}
     * メソッドをオーバーライドしてください．
     * </p><p>
     * このメソッドはfinal宣言されていますので，オーバーライドできません．
     * </p>
     */
    @Override
    public synchronized void finish() throws ProcessorException{
        if(stage != Stage.PROCESSED){
            throw new IllegalStateException("expects PROCESSED, but " + stage);
        }
        summarize();
        stage = Stage.FINISHED;
    }

    /**
     * サービスプロバイダを返します．
     * コンストラクタに与えられたサービスプロバイダをそのまま返します．
     */
    @Override
    public ProcessorService getProvider(){
        return provider;
    }

    /**
     * <p>
     * 処理器の設定項目を返します．
     * ステージがUNINITIALIZEDの場合は，
     * このメソッドで返されたオブジェクトの値を変更して，
     * この処理器の設定を変えられます．
     * </p><p>
     * ステージがUNINITIALIZEDでない場合は，IllegalStateExceptionが投げられます．
     * </p>
     */
    @Override
    public Arguments getArguments(){
        if(stage != Stage.UNINITIALIZED){
            throw new IllegalStateException(
                "current stage is " + stage + ". Initialization is already done."
            );
        }
        return arguments;
    }

    /**
     * <p>
     * この処理器のサマリを返します．
     * サマリとは処理内容を提示するためのオブジェクトです．
     * </p><p>
     * サマリに情報を追加するには{@link #putEntry <code>putEntry</code>}
     * メソッドを使います．このメソッドに渡された項目がサマリオブジェクトに追加されます．
     * </p><p>
     * ステージがUNINITIALIZEDの場合は，IllegalStateExceptionが投げられます．
     * </p>
     * @see Summary
     */
    @Override
    public Summary getSummary(){
        if(stage == Stage.UNINITIALIZED){
            throw new IllegalStateException(
                "current stage is UNINITIALIZED. Summary is not constructed."
            );
        }
        return summary;
    }

    /**
     * <p>
     * Summaryに出力するためのエントリを追加します．
     * </p><p>
     * 引数に与えられた{@link Arguments <code>Arguments</code>}にある全ての
     * {@link Argument <code>Argument</code>}が{@link Summary <code>Summary</code>}
     * に追加されます．Argumentのnameがkeyに割り当てられます．
     * ただし，Argumentのvalueがnullの場合は追加されず，無視されます．
     * </p><p>
     * 現在の状態がFINISHEDのとき，もしくは，UNINITIALIZEDのときに，
     * このメソッドが呼び出された場合，IllegalStateException が投げられます．
     * </p>
     *
     * @param args
     * @throws IllegalStateException 現在の状態がFINISHEDもしくはUNINITIALIZEDのとき．
     * @see Summary
     */
    protected synchronized void putEntry(Arguments args){
        for(Argument arg: args){
            String value = arg.getValue();
            if(value != null){
                putEntry(arg.getName(), value);
            }
        }
    }

    /**
     * <p>
     * Summaryに出力するためのエントリを追加します．
     * </p><p>
     * 現在の状態がFINISHEDのとき，もしくは，UNINITIALIZEDのときに，
     * このメソッドが呼び出された場合，IllegalStateException が投げられます．
     * </p>
     * @param key エントリのキー
     * @param value エントリのキーに対応する値
     * @throws IllegalStateException 現在の状態がFINISHEDもしくはUNINITIALIZEDのとき．
     * @see Summary
     */
    protected synchronized void putEntry(String key, String value){
        if(stage == Stage.FINISHED){
            throw new IllegalStateException("current stage is FINISHED. Summary is already closed.");
        }
        if(stage == Stage.UNINITIALIZED){
            throw new IllegalStateException("current stage is UNINITIALIZED. Summary is not constructed.");
        }
        summary.putEntry(key, value);
    }
}
