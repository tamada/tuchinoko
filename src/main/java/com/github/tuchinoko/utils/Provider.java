package com.github.tuchinoko.utils;

import java.io.Serializable;

/**
 * プロバイダの情報を表すクラス．
 *
 * @author Haruaki Tamada
 * @see Author
 * @see Organization
 */
public class Provider implements Serializable{
    private static final long serialVersionUID = 8577013159346892136L;

    /**
     * Tuchinokoプロバイダためのオブジェクト．
     */
    public static final Provider TUCHINOKO_PROVIDER = new Provider(
        "Tuchinoko Provider",
        new Author[] { new Author("Haruaki Tamada", "----"), }
    );
    /**
     * 不明のプロバイダのためのオブジェクト．
     */
    public static final Provider UNKNOWN = new Provider("unknown");

    private String name;
    private Author[] authors;
    private Organization org;
    
    /**
     * このプロバイダの名前を指定してオブジェクトを作成します．
     * もしプロバイダの名前がnullの場合はNullPointerExceptionが投げられます．
     * このコンストラクタを使った場合，著者と所属団体は設定されません．
     */
    public Provider(String name){
        this(name, null, null);
    }

    /**
     * このプロバイダの名前と著者情報を指定してオブジェクトを作成します．
     * もしプロバイダの名前がnullの場合はNullPointerExceptionが投げられます．
     * このコンストラクタを使った場合，所属団体は設定されません．
     */
    public Provider(String name, Author[] authors){
        this(name, authors, null);
    }

    /**
     * このプロバイダの名前と所属団体を指定してオブジェクトを作成します．
     * もしプロバイダの名前がnullの場合はNullPointerExceptionが投げられます．
     * このコンストラクタを使った場合，著者情報は設定されません．
     */
    public Provider(String name, Organization org){
        this(name, null, org);
    }

    /**
     * このプロバイダの名前，著者，団体を指定してオブジェクトを作成します．
     * もしプロバイダの名前がnullの場合はNullPointerExceptionが投げられます．
     * 著者や所属団体がnullの場合は，それらが設定されないオブジェクトが作成されます．
     */
    public Provider(String name, Author[] initAuthors, Organization org){
        if(name == null){
            throw new NullPointerException();
        }
        this.name = name;
        this.org = org;
        if(initAuthors != null && initAuthors.length != 0){
            authors = new Author[initAuthors.length];
            System.arraycopy(initAuthors, 0, authors, 0, authors.length);
        }
        else{
            authors = new Author[0];
        }
    }

    /**
     * このプロバイダに所属する著者一覧を返します．
     * 著者情報が設定されていない場合は長さ0の配列が返されます．
     */
    public Author[] getAuthors(){
        Author[] newAuthors = new Author[authors.length];
        System.arraycopy(authors, 0, newAuthors, 0, authors.length);
        return newAuthors;
    }

    /**
     * このプロバイダの団体情報を返します．
     * 団体情報が設定されていない場合はnullが返されます．
     */
    public Organization getOrganization(){
        return org;
    }

    /**
     * このプロバイダの名前を返します．
     */
    public String getName(){
        return name;
    }
}
