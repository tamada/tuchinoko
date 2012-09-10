package com.github.tuchinoko.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;
import com.github.tuchinoko.TargetType;

/**
 * バイト配列を入力ソースとする{@link ProcessTarget <code>ProcessTarget</code>}です．
 *
 * @author Haruaki Tamada
 */
public class ByteArrayProcessTarget extends AbstractProcessTarget{
    private byte[] data;
    private String name;

    /**
     * <p>
     * 名前とデータであるバイト配列を指定してオブジェクトを構築します．
     * データの型はクラスファイルであることを前提としています．
     * </p><p>
     * <code>this(source, name, data, TargetType.CLASS_FILE);</code>
     * </p>
     * @param name この入力ソースの名前
     * @param data 入力データ
     * @see #ByteArrayProcessTarget(TargetSource, String, byte[], TargetType)
     */
    public ByteArrayProcessTarget(TargetSource source, String name, byte[] data){
        this(source, name, data, TargetType.CLASS_FILE);
    }

    /**
     * 名前とデータであるバイト配列，そして，データの型を指定してオブジェクトを構築します．
     * @param source このProcessTargetを保持するTargetSource．
     * @param name この入力ソースの名前
     * @param initData 入力データ
     * @param type 入力データの型
     */
    public ByteArrayProcessTarget(TargetSource source, String name, byte[] initData, TargetType type){
        super(source, name, type);
        this.name = name;
        data = new byte[initData.length];
        System.arraycopy(initData, 0, data, 0, data.length);
    }

    /**
     * この入力ソースの名前を返します．
     * @return 入力ソースの名前
     */
    @Override
    public String getName(){
        return name;
    }

    /**
     * この入力ソースからデータを読み取るための入力ストリームを構築して返します．
     * @return 入力ソースからデータを読み取るための入力ストリーム
     */
    @Override
    public InputStream openStream() throws IOException{
        return new ByteArrayInputStream(data);
    }
}
