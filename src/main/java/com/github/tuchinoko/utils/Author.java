package com.github.tuchinoko.utils;

import java.io.Serializable;

/**
 * 著者情報を表すためのクラスです．
 * 
 * @author Haruaki Tamada
 */
public class Author implements Serializable{
    private static final long serialVersionUID = -1102911800329646249L;

    private String name;
    private String email;

    /**
     * <p>
     * 名前とメールアドレスを指定し，オブジェクトを構築します．
     * </p><p>
     * 名前がnullの場合はNullPointerExceptionが投げられます．
     * 電子メールがnullの場合はgetEmailがnullを返すようになります．
     * </p>
     */
    public Author(String name, String email){
        if(name == null){
            throw new NullPointerException();
        }
        this.name = name;
        this.email = email;
    }

    /**
     * <p>
     * 名前を指定し，オブジェクトを構築します．
     * </p><p>
     * 名前がnullの場合はNullPointerExceptionが投げられます．
     * </p><p>
     */
    public Author(String name){
        this(name, null);
    }

    /**
     * 著者の名前を返します．
     */
    public String getName(){
        return name;
    }

    /**
     * 著者のメールアドレスを返します．
     * 設定されていない場合はnullを返します．
     */
    public String getEmail(){
        return email;
    }

    /**
     * 著者情報の文字列表現を返します．
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        if(getEmail() != null){
            sb.append(" <").append(getEmail()).append(">");
        }
        return new String(sb);
    }
}
