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
    String getName();

    /**
     * デフォルトのクラスローダを使いProcessorServiceをロードし，
     * 新たなProcessorServicePoolに格納して返します．
     */
    ProcessorServicePool loadServices();

    /**
     * デフォルトのクラスローダを使いProcessorServiceをロードし，
     * 指定されたProcessorServicePoolに格納します．
     */
    ProcessorServicePool loadServices(ProcessorServicePool pool);

    /**
     * 指定されたクラスローダを使いProcessorServiceをロードし，
     * 指定されたProcessorServicePoolに格納します．
     */
    ProcessorServicePool loadServices(ProcessorServicePool pool, ClassLoader loader);
}
