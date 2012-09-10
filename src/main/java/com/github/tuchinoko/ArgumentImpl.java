package com.github.tuchinoko;

import java.io.Serializable;

/**
 * 1つの引数を表すクラスです．
 * {@link Argument <code>Argument</code>}のデフォルト実装クラスです．
 * 
 * @see Argument
 * @author Haruaki Tamada
 */
public final class ArgumentImpl implements Serializable, Argument{
    private static final long serialVersionUID = 5126931208339916608L;

    private String name;
    private String value;
    private String description;

    /**
     * セルフコンストラクタ．
     * 引数の Argument が持つ，名前，値，説明と同じ内容を持つ引数オブジェクトを作成します．
     */
    public ArgumentImpl(Argument arg){
        this(arg.getName(), arg.getValue(), arg.getDescription());
    }

    /**
     * 指定された名前で，値と説明がnullの引数オブジェクトを作成します．
     * 名前がnullの場合はIllegalArgumentExceptionが投げられます．
     * @param name 引数の名前
     * @throws IllegalArgumentException nameがnullの場合．
     */
    public ArgumentImpl(String name){
        this(name, null, null);
    }

    /**
     * 指定された名前と値を持ち，説明がnullの引数オブジェクトを作成します．
     * 名前がnullの場合はIllegalArgumentExceptionが投げられます．
     * @param name 引数の名前
     * @param value  引数の値
     * @throws IllegalArgumentException nameがnullの場合．
     */
    public ArgumentImpl(String name, String value){
        this(name, value, null);
    }

    /**
     * 指定された名前と値，説明を持つ引数オブジェクトを作成します．
     * 名前がnullの場合はIllegalArgumentExceptionが投げられます．
     * @param name 引数の名前
     * @param value  引数の値
     * @param description 引数の説明
     * @throws IllegalArgumentException nameがnullの場合．
     */
    public ArgumentImpl(String name, String value, String description){
        if(name == null){
            throw new IllegalArgumentException();
        }
        this.name = name;
        setValue(value);
        setDescription(description);
    }

    /**
     * 引数の名前を返します．
     * 返り値は必ずnull以外の値となります．
     */
    @Override
    public String getName(){
        return name;
    }

    /**
     * 引数の値を返します．
     * nullが返される場合もあります．
     */
    @Override
    public String getValue(){
        return value;
    }

    /**
     * 引数の値を設定します．
     * @param value 引数の値
     */
    public void setValue(String value){
        this.value = value;
    }

    /**
     * 引数の説明を返します．
     * nullが返される場合もあります．
     */
    @Override
    public String getDescription(){
        return description;
    }

    /**
     * 引数の説明を設定します．
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * この引数の文字列表現を返します．
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getName()).append("=");
        if(getValue() == null){
            sb.append("<null>");
        }
        else{
            sb.append(getValue());
        }
        if(getDescription() != null){
            sb.append("(").append(description).append(")");
        }
        return new String(sb);
    }
}
