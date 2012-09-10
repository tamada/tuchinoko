package com.github.tuchinoko.io;

import java.io.File;
import java.io.IOException;

import com.github.tuchinoko.Destination;

/**
 * 出力先を構築するためのBuilderクラス．
 *
 * @author Haruaki Tamada
 */
public class DestinationBuilder{
    /**
     * 出力先の名前に従って適切な{@link Destination}を構築して返します．
     * 出力先の拡張子に従い，jarファイルもしくはディレクトリを出力先であると判断して返します．
     * @see JarFileDestination
     * @see DirectoryDestination
     */
    public Destination build(String destination) throws IOException{
        File file = new File(destination);
        Destination dest;
        if(file.getName().endsWith(".jar")){
            dest = new JarFileDestination(file);
        }
        else{
            dest = new DirectoryDestination(file);
        }
        return dest;
    }
}
