package com.github.tuchinoko.spi;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link ProcessorServiceLoader <code>ProcessorServiceLoader</code>}
 * を構築するためのファクトリクラスです．
 *
 * @author Haruaki Tamada
 * @see DefaultProcessorServiceLoader
 */
public class ProcessorServiceLoaderFactory{
    private static ProcessorServiceLoaderFactory factory = new ProcessorServiceLoaderFactory();
    private Map<String, ProcessorServiceLoader> loaders;

    /**
     * シングルトンパターンのため，privateに．
     */
    private ProcessorServiceLoaderFactory(){
        loaders = new HashMap<String, ProcessorServiceLoader>();
        putLoader(loaders, new DefaultProcessorServiceLoader());
    }

    /**
     * 指定された名前を持つProcessorServiceLoaderを返します．
     * もし，指定された名前のProcessorServiceLoaderが存在しない場合は，
     * nullを返します．
     * @see ProcessorServiceLoader#getName
     */
    public ProcessorServiceLoader getLoader(String loaderName){
        return loaders.get(loaderName);
    }

    /**
     * デフォルトのProcessorServiceLoaderを返します．
     */
    public ProcessorServiceLoader getLoader(){
        return loaders.get("default");
    }

    /**
     * このクラスの唯一のオブジェクトを返します．
     */
    public static ProcessorServiceLoaderFactory getInstance(){
        return factory;
    }

    private void putLoader(Map<String, ProcessorServiceLoader> loaders, ProcessorServiceLoader loader){
        loaders.put(loader.getName(), loader);
    }
}
