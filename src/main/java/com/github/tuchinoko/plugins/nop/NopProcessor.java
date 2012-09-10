package com.github.tuchinoko.plugins.nop;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.tuchinoko.AbstractProcessor;
import com.github.tuchinoko.Arguments;
import com.github.tuchinoko.Destination;
import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.ProcessorException;
import com.github.tuchinoko.TargetSource;

/**
 * 何も処理を行わない処理器．
 * 
 * @author Haruaki Tamada
 */
public class NopProcessor extends AbstractProcessor{
    /**
     * NopProcessorを作成します．
     */
    public NopProcessor(NopProcessorService service){
        super(service);
    }

    /**
     * 初期設定を行います．
     */
    @Override
    public void prepare(Arguments args){
        putEntry(args);
    }

    /**
     * 何も処理を行わず，TargetSourceのProcessTargetからデータを読み込み，
     * そのままDestinationに出力します．
     */
    @Override
    public void perform(TargetSource source, Destination dest) throws ProcessorException{
        for(ProcessTarget target: source){
            try{
                InputStream in = target.getSource();
                OutputStream out = dest.getOutput(target.getName());
                putEntry("target", target.getName());

                int data;
                while((data = in.read()) != -1){
                    out.write(data);
                }
                in.close();
                out.close();
            } catch(IOException e){
                throw new ProcessorException(e);
            }
        }
    }

    /**
     * 終了処理を行います．
     */
    @Override
    public void summarize(){
        // do nothing....
    }
}
