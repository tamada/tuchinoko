package com.github.tuchinoko;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * ある処理器({@link Processor <code>Processor</code>})の処理結果を扱うためのクラスです．
 * 処理結果を出力するために用いられます．
 * </p><p>
 * サマリは複数のエントリから成り，1つのエントリにはキーとそれに対応する値が含まれる．
 * エントリは追加された順序を保持します．
 * キーは親子関係があり，階層は.で区切られます．このサマリのトップの階層は
 * {@link #getProcessorId() <code>getProcessorId</code>}メソッドで
 * 返される処理器の名前です．
 * </p><p>
 * サマリ同士が階層関係を結ぶ場合もあります．
 * </p>
 *
 * @author Haruaki Tamada
 */
public class Summary implements Iterable<Summary.Entry>{
    private String processorId;
    private Map<String, Summary.Entry> entries = new LinkedHashMap<String, Summary.Entry>();

    /**
     * 指定された名前でサマリオブジェクトを作成します．
     */
    public Summary(String processorId){
        setProcessorId(processorId);
    }

    /**
     * <p>
     * 処理器のIdを設定します．
     * </p><p>
     * idがnullのNullPointerExceptionが投げられます．
     * </p>
     * @param id 処理器のId
     */
    public void setProcessorId(String id){
        if(id == null){
            throw new NullPointerException();
        }
        this.processorId = id;
    }

    /**
     * 処理器のidを返します．
     * @return 処理器のid
     */
    public String getProcessorId(){
        return processorId;
    }

    /**
     * <p>サマリに，1つのエントリを追加します．</p>
     * <p>keyがnullの場合，valueがnullの場合はNullPointerExceptionが投げられます．</p>
     * @param key 追加するエントリのキー
     * @param value キーに対応する値
     */
    public void putEntry(String key, String value){
        if(key == null || value == null){
            throw new NullPointerException();
        }
        entries.put(key, new Summary.Entry(key, value));
    }

    /**
     * <p>
     * keyに対応する値を返します．keyに対応する値が存在しない場合はnullが返されます．
     * </p>
     * <p>
     * keyがnullの場合はNullPointerExceptionが投げられます．
     * </p>
     * @param key 取得したい値に対応付けられているキー．
     * @return keyに対応付けられた値．
     */
    public String getEntry(String key){
        if(key == null){
            throw new NullPointerException();
        }
        Entry entry = entries.get(key);
        String value = null;
        if(entry != null){
            value = entry.getValue();
        }
        return value;
    }

    /**
     * <p>
     * サマリから指定されたキーを持つエントリを削除します．
     * keyがnullの場合や，与えられたkeyに値が対応付けられていない場合は何も行いません．
     * </p>
     * 
     * @param key 削除するエントリのキー
     */
    public void removeEntry(String key){
        entries.remove(key);
    }

    /**
     * このサマリに格納されているエントリの数を返します．
     * @return このサマリオブジェクトが持つエントリの数．
     */
    public int getEntryCount(){
        return entries.size();
    }

    /**
     * このサマリに格納されているエントリの列挙を返します．
     */
    @Override
    public Iterator<Summary.Entry> iterator(){
        return entries.values().iterator();
    }

    /**
     * サマリに追加するエントリを表すクラス．
     */
    public static class Entry{
        private String key;
        private String value;

        /**
         * 与えられたキーとそれに対応する値で，
         * サマリのエントリを構築します．
         * キーもしくは，値がnullの場合はNullPointerExceptionが投げられます．
         */
        public Entry(String key, String value){
            if(key == null || value == null){
                throw new NullPointerException();
            }
            this.key = key;
            this.value = value;
        }

        /**
         * このエントリのキーを返します．
         */
        public String getKey(){
            return key;
        }

        /**
         * このエントリの値を返します．
         */
        public String getValue(){
            return value;
        }
    }
}
