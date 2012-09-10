package com.github.tuchinoko.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;
import com.github.tuchinoko.TargetType;

/**
 * 抽象ProcessTargetクラス．
 *
 * @author Haruaki Tamada
 */
public abstract class AbstractProcessTarget implements ProcessTarget{
    private TargetSource source;
    private String name;
    private TargetType type;
    private InputStream in;

    /**
     * <p>
     * 名前と種類を指定してオブジェクトを構築します．
     * </p>
     * <p>
     * もし，sourceがnullの場合はNullPointerExceptionが投げられます．
     * </p>
     */
    public AbstractProcessTarget(TargetSource source, String name, TargetType type){
        this.source = source;
        this.name = name;
        this.type = type;
    }

    /**
     * <p>
     * 名前を指定してオブジェクトを構築します．
     * 種類は名前の拡張子から自動的に判断されます．
     * </p>
     * <p>
     * もし，sourceがnullの場合はNullPointerExceptionが投げられます．
     * </p>
     * @see TargetType#getType(String)
     */
    public AbstractProcessTarget(TargetSource source, String name){
        this.source = source;
        this.name = name;
        this.type = TargetType.getType(name);
    }

    /**
     * <p>
     * {@link #getType <code>getType</code>}が
     * {@link TargetType#CLASS_FILE <code>CLASS_FILE</code>}を返す場合にクラス名を返します．
     * </p><p>
     * このオブジェクトがCLASS_FILEでない場合は，IllegalStateExceptionが投げられます．
     * </p><p>
     * クラス名は，{@link #getName()}が返す文字列から拡張子である「.class」を取り除き，
     * ファイル名の「/」を「.」に変えたものです．
     * </p><p>
     */
    @Override
    public String getClassName(){
        if(type == TargetType.CLASS_FILE){
            String className = name.substring(0, name.lastIndexOf('.'));
            className = className.replace('/', '.');
            return className.replace(File.separatorChar, '.');
        }
        throw new IllegalStateException("not class file");
    }

    /**
     * このオブジェクトの名前を返します．
     */
    @Override
    public String getName(){
        return name;
    }

    /**
     * このオブジェクトの種類を返します．
     */
    @Override
    public final TargetType getType(){
        return type;
    }

    /**
     * <p>
     * このオブジェクトが表すデータを読み込むための入力ストリームを返します．
     * 実際の入力ストリームは{@link #openStream}メソッドで開かれます．
     * </p><p>
     * 返される入力ストリームは必要がなくなれば適宜closeを呼び出し，閉じてください．
     * </p>
     */
    @Override
    public final InputStream getSource() throws IOException{
        if(in == null){
            in = openStream();
        }
        return in;
    }

    /**
     * このオブジェクトが所属するTargetSourceを返します．
     */
    public final TargetSource getTargetSource(){
        return source;
    }

    /**
     * このオブジェクトが表すデータを読み込むための入力ストリームを返すように，
     * サブクラスでこのメソッドをオーバーライドしてください．
     * 返される入力ストリームはユーザ側で閉じられます．
     */
    protected abstract InputStream openStream() throws IOException;
}
