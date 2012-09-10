package com.github.tuchinoko.spi;

import com.github.tuchinoko.ProcessorServicePool;

/**
 * ProcessorServiceをServiceLoaderを使って読み込むためのインターフェースです．
 * 
 * @author Haruaki Tamada
 */
public interface ProcessorServiceLoader{
    /**
     * このProcessorServiceLoaderの名前を返します．
     */
    public String getName();

    /**
     * デフォルトのクラスローダを使いProcessorServiceをロードし，
     * 新たなProcessorServicePoolに格納して返します．
     */
    public ProcessorServicePool loadServices();

    /**
     * デフォルトのクラスローダを使いProcessorServiceをロードし，
     * 指定されたProcessorServicePoolに格納します．
     */
    public ProcessorServicePool loadServices(ProcessorServicePool pool);

    /**
     * 指定されたクラスローダを使いProcessorServiceをロードし，
     * 指定されたProcessorServicePoolに格納します．
     */
    public ProcessorServicePool loadServices(ProcessorServicePool pool, ClassLoader loader);
}
