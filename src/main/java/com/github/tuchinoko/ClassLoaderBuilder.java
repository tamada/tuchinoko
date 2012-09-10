package com.github.tuchinoko;

import java.io.IOException;

/**
 * {@link ClassLoader <code>ClassLoader</code>}を構築するクラスのためのインターフェース．
 *
 * @author Haruaki Tamada
 */
public interface ClassLoaderBuilder{
    /**
     * {@link ClassLoader <code>ClassLoader</code>}を構築して返します．
     * I/Oエラーが起こった場合，IOException が投げられます．
     */
    public ClassLoader createLoader() throws IOException;

    /**
     * このクラスの名前を返します．
     */
    public String getName();
}
