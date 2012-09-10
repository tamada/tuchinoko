package com.github.tuchinoko.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;
import com.github.tuchinoko.TargetType;

/**
 * ファイルを入力ソースとする{@link ProcessTarget}です．
 *
 * @author Haruaki Tamada
 */
public class FileProcessTarget extends AbstractProcessTarget{
    private String name;
    private File file;

    /**
     * <p>
     * 名前とデータ元であるファイルを指定してオブジェクトを構築します．
     * データの型はファイル名の拡張子から判断されます．
     * </p>
     * @param name この入力ソースの名前．
     * @param file 入力データとなるファイル．
     * @see TargetType#getType
     */
    public FileProcessTarget(TargetSource source, String name, File file){
        super(source, name);
        this.name = name;
        this.file = file;
    }

    /**
     * <p>
     * データ元であるファイルを指定してオブジェクトを構築します．
     * このProcessTargetの名前はファイルの名前となり，
     * データの型はファイル名の拡張子から判断されます．
     * </p>
     * @param file 入力データとなるファイル．
     * @see TargetType#getType
     */
    public FileProcessTarget(TargetSource source, File file){
        this(source, file.getName(), file);
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public InputStream openStream() throws IOException{
        return new FileInputStream(file);
    }
}
