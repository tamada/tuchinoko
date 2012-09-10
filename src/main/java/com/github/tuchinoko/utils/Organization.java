package com.github.tuchinoko.utils;

import java.io.Serializable;
import java.net.URL;

/**
 * 著者の所属団体を表すクラスです．
 *
 * @author Haruaki Tamada
 */
public class Organization implements Serializable{
    private static final long serialVersionUID = -8593199866656231894L;

    private String name;
    private URL url;

    /**
     * <p>
     * 著者の所属団体名を指定してオブジェクトを作成します．
     * </p><p>
     * 所属団体名がnullの場合はIllegalArgumentExceptionが投げられます．
     * </p>
     */
    public Organization(String name){
        this(name, null);
    }

    /**
     * <p>
     * 著者の所属団体名とURLを指定してオブジェクトを作成します．
     * </p><p>
     * 所属団体名がnullの場合はIllegalArgumentExceptionが投げられます．
     * URLがnullの場合は，{@link #getUrl}がnullを返すようになります．
     * </p>
     */
    public Organization(String name, URL url){
        if(name == null){
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.url = url;
    }

    /**
     * 著者の所属団体名を返します．
     */
    public String getName(){
        return name;
    }

    /**
     * 著者の所属団体のURLを返します．
     * 設定されていない場合はnullを返します．
     */
    public URL getUrl(){
        return url;
    }

    /**
     * このオブジェクトの文字列表現を返します．
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        if(getUrl() != null){
            sb.append(" (").append(getUrl()).append(")");
        }
        return new String(sb);
    }
}
