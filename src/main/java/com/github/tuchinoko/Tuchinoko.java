package com.github.tuchinoko;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.tuchinoko.io.DestinationBuilder;
import com.github.tuchinoko.io.GlueDestination;
import com.github.tuchinoko.io.TargetSourceBuilder;

/**
 * Tuchinokoの実行エンジンとなるクラスです．
 * Tuchinokoをプログラムから実行する場合は，このクラスを利用してください．
 *
 * @author Haruaki Tamada
 */
public class Tuchinoko{
    private Context context;

    /**
     * 指定されたContextをもとに実行エンジンを構築します．
     */
    public Tuchinoko(Context context){
        this.context = context;
    }

    /**
     * コンストラクタで与えられたContextに従って処理を行います．
     */
    public void execute() throws ProcessorException{
        try{
            Processor[] processors = context.getProcessors();

            TargetSource source = new TargetSourceBuilder().build(context.getTargets());
            List<Summary> summaries = new ArrayList<Summary>();
            for(int i = 0; i < processors.length; i++){
                Destination dest;
                if(i == (processors.length - 1)){
                    dest = new DestinationBuilder().build(context.getDestination());
                }
                else{
                    dest = new GlueDestination();
                }
                processors[i].init();
                processors[i].execute(source, dest);
                processors[i].finish();
                summaries.add(processors[i].getSummary());
                if(dest instanceof GlueDestination){
                    source = ((GlueDestination)dest).getTargetSource();
                }
                dest.close();
            }

        } catch(IOException e){
            throw new ProcessorException(e);
        }
    }
}
