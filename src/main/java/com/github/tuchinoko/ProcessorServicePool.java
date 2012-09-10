package com.github.tuchinoko;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.tuchinoko.spi.ProcessorService;

/**
 * {@link ProcessorService}の集合を管理するクラスです．
 * このオブジェクトはProcessorServiceの追加された順序を保持します．
 *
 * @author Haruaki Tamada
 */
public class ProcessorServicePool implements Iterable<ProcessorService>{
    private Map<String, ProcessorService> services = new LinkedHashMap<String, ProcessorService>();

    /**
     * デフォルトコンストラクタです．
     */
    public ProcessorServicePool(){
    }

    /**
     * セルフコンストラクタで，引数に与えられたProcessorServicePoolが持つ
     * ProcessorServiceを持つオブジェクトを構築します．
     */
    public ProcessorServicePool(ProcessorServicePool pool){
        services.putAll(pool.services);
    }

    /**
     * このオブジェクトが持つProcessorServiceの列挙を返します．
     */
    @Override
    public Iterator<ProcessorService> iterator(){
        return Collections.unmodifiableCollection(services.values()).iterator();
    }

    /**
     * <p>
     * このオブジェクトに新たにProcessorServiceを登録します．
     * </p><p>
     * 引数にnullが与えられた場合はNullPointerExceptionが投げられます．
     * </p><p>
     * 既に指定されたProcessorServiceが登録されている場合は，
     * 引数に与えられたオブジェクトで登録を更新します．
     * </p>
     */
    public void addProvider(ProcessorService service){
        services.put(service.getProcessorName(), service);
    }

    /**
     * <p>
     * このオブジェクトが指定された名前を持つProcessorServiceを保持していれば
     * trueを返し，保持していなければfalseを返します．
     * </p><p>
     * 引数にnullが与えられた場合は，falseが返されます．
     * </p>
     */
    public boolean contains(String processorName){
        return services.containsKey(processorName);
    }

    /**
     * <p>
     * このオブジェクトが指定されたProcessorServiceを保持していればtrue，
     * 保持していなければfalseを返します．
     * </p><p>
     * 引数にnullが与えられた場合はfalseが返されます．
     * </p>
     */
    public boolean contains(ProcessorService service){
        return services.containsValue(service);
    }

    /**
     * <p>
     * 指定されたProcessorServiceをこのオブジェクトの登録から削除します．
     * 削除に成功すればtrueを返し，失敗すればfalseを返します．
     * </p><p>
     * 指定されたProcessorServiceがこのオブジェクトに登録されていないとき，削除に失敗します．
     * </p><p>
     * 引数にnullが与えられた場合はNullPointerExceptionが投げられます．
     * </p>
     */
    public boolean removeProvider(ProcessorService service){
        return services.remove(service.getProcessorName()) != null;
    }

    /**
     * <p>
     * このオブジェクトに登録されているProcessorServiceの数を返します．
     * </p><p>
     * このオブジェクトは必ず0以上の数値を返します．
     * </p>
     */
    public int getServiceCount(){
        return services.size();
    }

    /**
     * <p>
     * 指定された名前を持つProcessorServiceを返します．
     * </p><p>
     * 指定された名前を持つProcessorServiceをこのオブジェクトが保持していない場合，
     * もしくはnullが与えられた場合，このメソッドはnullを返します．
     * </p>
     */
    public ProcessorService getService(String processorName){
        return services.get(processorName);
    }
}
