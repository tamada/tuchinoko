package com.github.tuchinoko;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * {@link com.github.tuchinoko.ProcessTarget <code>ProcessTarget</code>}
 * の型を表すためのクラスです．
 *
 * @author Haruaki Tamada
 */
public abstract class TargetType{
    private static final List<TargetType> TARGETS = new ArrayList<TargetType>();

    /**
     * 名前の接尾辞で型を判断する{@link TargetType}です．
     */
    public static final class SuffixTargetType extends StringTargetType{
        public SuffixTargetType(String name, String... suffix){
            super(name, suffix);
        }

        public boolean isType(String fileName){
            boolean flag = false;
            if(fileName != null){
                for(String string: getStrings()){
                    if(fileName.endsWith(string)){
                        flag = true;
                        break;
                    }
                }
            }
            return flag;
        }
    }

    /**
     * 名前の接頭辞で型を判断する{@link TargetType}です．
     */
    public static final class PrefixTargetType extends StringTargetType{
        public PrefixTargetType(String name, String... prefix){
            super(name, prefix);
        }

        public boolean isType(String fileName){
            boolean flag = false;
            if(fileName != null){
                for(String string: getStrings()){
                    if(fileName.startsWith(string)){
                        flag = true;
                    }
                }
            }
            return flag;
        }
    }

    /**
     * 名前で型を判断する{@link TargetType}です．
     */
    private abstract static class StringTargetType extends TargetType{
        private String[] strings;
        private String name;

        public StringTargetType(String name, String... strings){
            this.name = name;
            this.strings = strings;
        }

        public String toString(){
            return name;
        }

        public String[] getStrings(){
            return strings;
        }
    }
    /** APTファイルを表すタイプ */
    public static final TargetType APT_FILE = new SuffixTargetType("APT_FILE", ".apt");
    /** HTMLファイルを表すタイプ */
    public static final TargetType HTML_FILE = new SuffixTargetType("HTML_FILE", ".html", ".htm");
    /** xmlファイルを表すタイプ */
    public static final TargetType XML_FILE = new SuffixTargetType("XML_FILE", ".xml");
    /** クラスファイルを表すタイプ */
    public static final TargetType CLASS_FILE = new SuffixTargetType("CLASS_FILE", ".class");
    /** Javaソースファイルを表すタイプ */
    public static final TargetType JAVA_FILE = new SuffixTargetType("JAVA_FILE", ".java");
    /** マニフェストファイルを表すタイプ */
    public static final TargetType MANIFEST = new PrefixTargetType("MANIFEST", "META-INF/MANIFEST.MF");
    /** JNLPファイルを表すタイプ */
    public static final TargetType JNLP_FILE = new SuffixTargetType("JNLP_FILE", ".jnlp");
    /** プロパティファイルを表すタイプ */
    public static final TargetType PROPERTIES_FILE = new SuffixTargetType("PROPERTIES_FILE", ".properties");
    /** サービスデスクリプタを表すタイプ */
    public static final TargetType SERVICE_DESCRIPTOR = new PrefixTargetType("SERVICE_DESCRIPTOR", "META-INF/services/");
    /** BMP画像ファイルを表すタイプ */
    public static final TargetType BMP_FILE = new SuffixTargetType("BMP_FILE", ".bmp");
    /** GIF画像ファイルを表すタイプ */
    public static final TargetType GIF_FILE = new SuffixTargetType("GIF_FILE", ".gif");
    /** JPEG画像ファイルを表すタイプ */
    public static final TargetType JPG_FILE = new SuffixTargetType("JPG_FILE", ".jpg", ".jpeg", ".jpe");
    /** PNG画像ファイルを表すタイプ */
    public static final TargetType PNG_FILE = new SuffixTargetType("PNG_FILE", ".png");
    /** jarファイルを表すタイプ */
    public static final TargetType JAR_FILE = new SuffixTargetType("JAR_FILE", ".jar");
    /** zipファイルを表すタイプ */
    public static final TargetType ZIP_FILE = new SuffixTargetType("ZIP_FILE", ".zip");
    /** warファイルを表すタイプ */
    public static final TargetType WAR_FILE = new SuffixTargetType("WAR_FILE", ".war");
    /** earファイルを表すタイプ */
    public static final TargetType EAR_FILE = new SuffixTargetType("EAR_FILE", ".ear");
    /** tarファイルを表すタイプ */
    public static final TargetType TAR_FILE = new SuffixTargetType("TAR_FILE", ".tar");
    /** bz2ファイルを表すタイプ */
    public static final TargetType BZIP2_FILE = new SuffixTargetType("BZIP2_FILE", ".bz2");
    /** gzファイルを表すタイプ */
    public static final TargetType GZIP_FILE = new SuffixTargetType("GZIP_FILE", ".gz");
    /**
     * その他のファイルを表すタイプ
     */
    public static final TargetType FILE = new TargetType(){
        @Override
        public boolean isType(String fileName){
            return fileName != null;
        }

        public String toString(){
            return "FILE";
        }
    };

    /**
     * <p>
     * 登録されている型一覧の列挙を返します．
     * </p>
     */
    public static Iterator<TargetType> values(){
        return TARGETS.iterator();
    }

    /**
     * <p>
     * 登録されている型一覧から，引数に与えられた名前のものに合致するものがあれば，
     * その型を返します．
     * 何も合致しない場合，{@link #FILE <code>FILE</code>}であると判断されます．
     * </p><p>
     * 引数にnullが与えられた場合，IllegalArgumentExceptionが投げられます．
     * </p>
     */
    public static TargetType getType(String name){
        if(name == null){
            throw new IllegalArgumentException();
        }
        TargetType target = TargetType.FILE;
        for(TargetType type: TARGETS){
            if(type.isType(name)){
                target = type;
                break;
            }
        }
        return target;
    }

    /**
     * サブクラスから呼び出されるコンストラクタ．
     * このコンストラクタが呼び出されれば，自動的にその型は登録されます．
     */
    protected TargetType(){
        TARGETS.add(this);
    };

    /**
     * 引数に与えられた名前が，このオブジェクトが表す型であるかを判断します．
     * 多くの場合，拡張子で型が判断されます．
     */
    public abstract boolean isType(String fileName);
}
