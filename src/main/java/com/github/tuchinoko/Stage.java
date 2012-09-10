package com.github.tuchinoko;

/**
 * <p>
 * {@link AbstractProcessor <code>AbstractProcessor</code>}の状態です．
 * AbstractProcessorの状態は以下のどれかです．
 * </p>
 * <dl>
 *   <dt>UNINITIALIZED</dt>
 *   <dd>初期化されていない状態．</dd>
 *   <dt>INITIALIZED</dt>
 *   <dd>初期化された状態で，処理を行う準備ができた状態．</dd>
 *   <dt>PROCESSING</dt>
 *   <dd>処理を行っている状態．</dd>
 *   <dt>PROCESSED</dt>
 *   <dd>処理を全て終了した状態で，後処理が未完了の状態．</dd>
 *   <dt>FINISHED</dt>
 *   <dd>後処理まで終了した状態．</dd>
 * </dl>
 * <p>
 * 状態の遷移の順序についてはAbstractProcessorを参照してください．
 * </p>
 *
 * @author Haruaki Tamada
 * @see AbstractProcessor
 */
public enum Stage{
    /**
     * 初期化されていない状態．
     */
    UNINITIALIZED,
    /**
     * 初期化された状態で，処理を行う準備ができた状態．
     */
    INITIALIZED,
    /**
     * 処理を行っている状態．
     */
    PROCESSING,
    /**
     * 処理を全て終了した状態で，後処理が未完了の状態．
     */
    PROCESSED,
    /**
     * 後処理まで終了した状態．
     */
    FINISHED,
}
