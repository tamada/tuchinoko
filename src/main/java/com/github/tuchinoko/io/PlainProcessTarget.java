package com.github.tuchinoko.io;

import java.io.IOException;
import java.io.InputStream;

import com.github.tuchinoko.TargetSource;
import com.github.tuchinoko.TargetType;

/**
 * 入力ストリームと名前が指定された単純なProcessTargetです．
 *
 * @author Haruaki Tamada
 */
class PlainProcessTarget extends AbstractProcessTarget{
    private InputStream in;

    /**
     * 名前と入力ストリームが指定されたProcessTargetを構築します．
     * 型は名前の拡張子から判断されます．
     * @see TargetType#getType(String)
     */
    public PlainProcessTarget(TargetSource source, String name, InputStream in){
        super(source, name);
        this.in = in;
    }

    /**
     * 名前と入力ストリーム，型が指定されたProcessTargetを構築します．
     */
    public PlainProcessTarget(TargetSource source, String name, InputStream in, TargetType type){
        super(source, name, type);
        this.in = in;
    }

    /**
     * 入力ストリームを返します．
     */
    @Override
    public InputStream openStream() throws IOException{
        return in;
    }
}
