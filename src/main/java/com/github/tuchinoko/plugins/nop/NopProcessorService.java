package com.github.tuchinoko.plugins.nop;

import java.net.MalformedURLException;
import java.net.URL;

import com.github.tuchinoko.Arguments;
import com.github.tuchinoko.Processor;
import com.github.tuchinoko.spi.AbstractProcessorService;
import com.github.tuchinoko.utils.Author;
import com.github.tuchinoko.utils.Organization;
import com.github.tuchinoko.utils.Provider;

/**
 * NopProcessorのSPIクラスです．
 *
 * @author Haruaki Tamada
 */
public class NopProcessorService extends AbstractProcessorService{
    /**
     * NopProcessorの名前である「nop」を返します．
     */
    @Override
    public String getProcessorName(){
        return "nop";
    }

    /**
     * NopProcessorの説明を返します．
     */
    @Override
    public String getDescription(){
        return "何も処理を行わない処理器";
    }

    /**
     * NopProcessorを作成して返します．
     */
    @Override
    public Processor createProcessor(){
        return new NopProcessor(this);
    }

    /**
     * NopProcessorのProviderを作成して返します．
     */
    @Override
    public Provider createProvider(){
        try{
            return new Provider(
                "Example", 
                new Author[] { new Author("Example Author", "author@example.com"), },
                new Organization("Example Organization", new URL("http://www.example.com"))
            );
        } catch(MalformedURLException e){
        }
        return Provider.UNKNOWN;
    }

    /**
     * NopProcessorのArgumentsを作成して返します．
     * NopProcessorは何もパラメータを取りません．
     */
    @Override
    public Arguments createDefaultArguments(){
        return new Arguments();
    }
}
