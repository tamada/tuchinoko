package com.github.tuchinoko;

import java.io.IOException;
import java.util.Iterator;

import com.github.tuchinoko.spi.ProcessorService;
import com.github.tuchinoko.spi.ProcessorServiceLoader;
import com.github.tuchinoko.spi.ProcessorServiceLoaderFactory;

/**
 * Tuchinokoの実行環境を表すクラスです．
 * このクラスは基本的にTuchinoko実行中に1つだけ作成されます．
 * 主に処理器のロードに用いられます．
 * 
 * @author Haruaki Tamada
 */
public class Environment implements Iterable<ProcessorService>{
    private ProcessorServicePool pool;

    /**
     * 指定されたクラスローダから処理器をロードするオブジェクトを構築します．
     */
    public Environment(ClassLoader loader){
        initialize(loader);
    }

    /**
     * デフォルトの場所からロードするクラスローダを使って，
     * 処理器をロードするオブジェクトを構築します．
     * @see LocalClassLoaderBuilder
     */
    public Environment(){
        initialize(buildClassLoader());
    }

    /**
     * ロード可能な処理器のサービスプロバイダの列挙を返します．
     * @see ProcessorService
     */
    public Iterator<ProcessorService> iterator(){
        return pool.iterator();
    }

    /**
     * 指定された名前を持つ処理器のサービスプロバイダを返します．
     * 指定された名前を持つ処理器が存在しない場合はnullを返します．
     * 引数にnullが与えられた場合はNullPointerExceptionが投げられます．
     */
    public ProcessorService getService(String name){
        if(name == null){
            throw new NullPointerException();
        }
        return pool.getService(name);
    }

    /**
     * クラスローダを更新し，指定されたクラスローダから処理器のサービスプロバイダを取得します．
     * 指定されたクラスローダがnullの場合はNullPointerExceptionが投げられます．
     */
    public void updateClassLoader(ClassLoader loader){
        ProcessorServiceLoader serviceLoader = ProcessorServiceLoaderFactory.getInstance().getLoader();
        serviceLoader.loadServices(pool, loader);
    }

    /**
     * 現在この環境オブジェクトが保持する処理器のサービスプロバイダの数を返します．
     * 返り値は必ず0以上になります．
     */
    public int getServiceCount(){
        return pool.getServiceCount();
    }

    private ClassLoader buildClassLoader(){
        ClassLoaderBuilder builder = new LocalClassLoaderBuilder();
        ClassLoader loader = null;
        try{
            loader = builder.createLoader();
        } catch(IOException e){
            e.printStackTrace();
        }
        return loader;
    }

    private void initialize(ClassLoader loader){
        pool = new ProcessorServicePool();

        updateClassLoader(loader);
    }
}
