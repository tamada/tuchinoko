package com.github.tuchinoko;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 処理器のパラメータを取りまとめるクラスです．
 * このクラスは複数の{@link Argument <code>Argument</code>}を管理します．
 * 
 * @see Argument
 * @author Haruaki Tamada
 */
public class Arguments implements Iterable<Argument>, Serializable{
    private static final long serialVersionUID = 529727017881875610L;

    private Map<String, Argument> arguments = new HashMap<String, Argument>();

    /**
     * 空のArgumentを持つオブジェクトを構築します．
     */
    public Arguments(){
    }

    /**
     * セルフコンストラクタ．引数に与えられたArgumentsが持っている全てのArgument
     * を保持するオブジェクトを構築します．
     */
    public Arguments(Arguments args){
        for(Argument param: args){
            arguments.put(param.getName(), param);
        }
    }

    /**
     * 引数に与えられたArgumentを持つオブジェクトを構築します．
     */
    public Arguments(Argument[] args){
        for(Argument param: args){
            arguments.put(param.getName(), param);
        }
    }

    /**
     * このオブジェクトが持つArgumentの列挙を返します．
     */
    @Override
    public Iterator<Argument> iterator(){
        return Collections.unmodifiableMap(arguments).values().iterator();
    }

    /**
     * 与えられた Arguments とこの Arguments を統合します．両方の Arguments
     * に同じ名前の異なる値があった場合，このArgumentsにある値が保持されます．
     * 
     * @see #mergeOverwrite(Arguments)
     * @param args
     */
    public void merge(Arguments args){
        for(Argument param: args){
            if(arguments.get(param.getName()) == null){
                arguments.put(param.getName(), param);
            }
        }
    }

    /**
     * 与えられた Arguments とこの Arguments を統合します．
     * 両方の Arguments に同じ名前の異なる値があった場合，
     * 与えられた Arguments の値でこのArgumentsの値が上書きされます．
     * 
     * @param args
     */
    public void mergeOverwrite(Arguments args){
        for(Argument param: args){
            arguments.put(param.getName(), param);
        }
    }

    /**
     * <p>
     * 与えられた名前のArgumentをこのオブジェクトが持っていれば，trueを返し，
     * 持っていなければfalseを返します．
     * オブジェクトを持っているとは，このArgumentsオブジェクトに
     * 指定された名前を持つ値が設定されていることです．その値がnullの場合もあり得ます．
     * </p><p>
     * 引数にnullが与えられれば，NullPointerExceptionが投げられます．
     * </p>
     * 
     * @param name 存在を確認する引数の名前
     * @return 存在していればtrue, 存在していなければ false．
     */
    public boolean hasArgument(String name){
        if(name == null){
            throw new NullPointerException();
        }
        return arguments.get(name) != null;
    }

    /**
     * <p>
     * 与えられた名前に割り当てられたArgumentの値を返します．
     * </p><p>
     * nameに引数が設定されていなかった場合(nameに対応する値がnullの場合)，
     * defaultValueを返します．
     * </p><p>
     * {@link #hasArgument(String) <code>hasArgument(name)</code>}がfalseを返す場合，
     * このメソッドはnullを返します．
     * </p><p>
     * nameがnullの場合，NullPointerExceptionが投げられます．
     * </p>
     * 
     * @param name 値を取得するArgumentの名前
     * @param defaultValue 指定したArgumentに値が割り当てられていなかった場合に返すデフォルト値
     * @return Argumentに割り当てられた値．値がnullであれば，第２引数に渡されたdefaultValue．
     */
    public String getValue(String name, String defaultValue){
        String value = null;
        if(name == null){
            throw new NullPointerException();
        }
        if(arguments.containsKey(name)){
            Argument param = arguments.get(name);
            if(param != null){
                value = param.getValue();
            }
            if(value == null){
                value = defaultValue;
            }
        }
        return value;
    }

    /**
     * <p>
     * 与えられた名前に割り当てられたArgumentの解説を返します．
     * </p><p>
     * {@link #hasArgument(String) <code>hasArgument(name)</code>}がfalseを返す場合や，
     * 解説が設定されていなかった場合はnullを返します．
     * </p><p>
     * 引数にnullが与えられた場合，NullPointerExceptionが投げられます．
     * </p>
     * 
     * @param name 解説を取得するArgumentの名前
     * @return 指定されたArgumentの解説．
     */
    public String getDescription(String name){
        String desc = null;
        if(name == null){
            throw new NullPointerException();
        }
        if(arguments.containsKey(name)){
            Argument param = arguments.get(name);
            if(param != null){
                desc = param.getDescription();
            }
        }
        return desc;
    }

    /**
     * <p>
     * 与えられた名前に割り当てられたArgumentの値を返します．値が存在しなかったり，
     * {@link #hasArgument(String) <code>hasArgument(name)</code>}
     * がfalseを返す場合，nullを返します．
     * </p><p>
     * {@link #getValue(String, String) <code>getValue(name, null)</code>}．
     * </p>
     * 
     * @param name 値を取得するArgumentの名前
     * @return Argumentの名前に割り当てられた値．
     */
    public String getValue(String name){
        return getValue(name, null);
    }

    /**
     * Argumentの解説を設定します．与えられた名前のArgumentが設定されていない場合，
     * name がnullの場合は NullPointerExceptionが投げられます．
     * 
     * @param name 解説を設定しようとするArgumentの名前
     * @param description 新たに設定する解説
     */
    public void setDescription(String name, String description){
        Argument arg = arguments.get(name);
        if(name == null){
            throw new NullPointerException("name is null");
        }
        else if(arg == null){
            throw new NullPointerException(name + " is not contained.");
        }
        else{
            arg = new ArgumentImpl(name, arg.getValue(), description);
        }
        arguments.put(name, arg);
    }

    /**
     * 引数のargを新たなArgumentの1つとして登録します．
     * 
     * @param arg 新たに登録するArgument．
     */
    public void putValue(Argument arg){
        arguments.put(arg.getName(), arg);
    }

    /**
     * <p>
     * Argumentに値を割り当てます． nameが存在しない場合は新たなArgumentが作成されます．
     * </p><p>
     * nameがnullの場合，NullPointerExceptionが投げられます．
     * valueがnullの場合，Argumentの値がnullとして設定されます．
     * </p><p>
     * nameに割り当てられたArgumentが存在した場合，値が変更されるだけで，解説には影響を与えません．
     * </p>
     */
    public void putValue(String name, String value){
        if(name == null){
            throw new NullPointerException();
        }
        Argument arg = arguments.get(name);
        if(arg == null){
            arg = new ArgumentImpl(name, value);
        }
        else{
            arg = new ArgumentImpl(name, value, arg.getDescription());
        }
        arguments.put(name, arg);
    }

    /**
     * <p>
     * Argumentに値を割り当てます．
     * </p><p>
     * nameがnullの場合，NullPointerExceptionが投げられます．
     * value，descriptionがnullの場合，nullで上書きされます．
     * </p>
     */
    public void putValue(String name, String value, String description){
        if(name == null){
            throw new NullPointerException();
        }
        arguments.put(name, new ArgumentImpl(name, value, description));
    }

    /**
     * <p>
     * このオブジェクトが保持するArgumentの数を返します．返される値は必ず0以上になります．
     * </p>
     */
    public int getArgumentCount(){
        return arguments.size();
    }

    /**
     * <p>
     * nameが表す引数を削除します．
     * </p><p>
     * 削除に成功した場合はtrueを返します．
     * {@link #hasArgument(String) <code>hasArgument(name)</code>}がfalseを返す場合は
     * このメソッドもfalseを返します．
     * </p>
     */
    public boolean removeArgument(String name){
        return arguments.remove(name) != null;
    }
}
