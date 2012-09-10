package com.github.tuchinoko.spi;

import java.util.ServiceLoader;

import com.github.tuchinoko.ProcessorServicePool;

/**
 * ProcessorServiceをServiceLoaderを使って読み込む実装クラスです．
 * 
 * @see ServiceLoader
 * @author Haruaki Tamada
 */
public class DefaultProcessorServiceLoader implements ProcessorServiceLoader{

    /**
     * このProcessorServiceLoaderの名前であるdefaultを返します．
     */
    public String getName(){
        return "default";
    }

    /**
     * デフォルトのクラスローダを使いProcessorServiceをロードし，
     * 新たなProcessorServicePoolに格納して返します．
     */
    public ProcessorServicePool loadServices(){
        return loadServices(new ProcessorServicePool(), ClassLoader.getSystemClassLoader());
    }

    /**
     * デフォルトのクラスローダを使いProcessorServiceをロードし，
     * 指定されたProcessorServicePoolに格納します．
     */
    public ProcessorServicePool loadServices(ProcessorServicePool pool){
        return loadServices(pool, ClassLoader.getSystemClassLoader());
    }

    /**
     * 指定されたクラスローダを使いProcessorServiceをロードし，
     * 指定されたProcessorServicePoolに格納します．
     */
    public ProcessorServicePool loadServices(ProcessorServicePool pool, ClassLoader loader){
        for(ProcessorService service: ServiceLoader.load(ProcessorService.class, loader)){
            pool.addProvider(service);
        }
        return pool;
    }
}
