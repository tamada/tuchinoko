package com.github.tuchinoko.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.tuchinoko.Destination;
import com.github.tuchinoko.ProcessTarget;

/**
 * 抽象Destinationクラスです．
 * {@link #output <code>output</code>}メソッドのデフォルト実装が行われています．
 *
 * @author Haruaki Tamada
 */
public abstract class AbstractDestination implements Destination{

    @Override
    public abstract OutputStream getOutput(String className) throws IOException;

    @Override
    public abstract OutputStream getOutput(ProcessTarget target) throws IOException;

    @Override
    public void output(ProcessTarget target) throws IOException{
        InputStream in = null;
        OutputStream out = null;
        try{
            in = target.getSource();
            out = getOutput(target);
            byte[] data = new byte[256];
            int read = 0;

            while((read = in.read(data)) != 0){
                out.write(data, 0, read);
            }
        } finally{
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
        }
    }

    @Override
    public abstract void close() throws IOException;

    /**
     * 常にfalseを返します．
     */
    @Override
    public boolean isClosed(){
        return false;
    }
}
