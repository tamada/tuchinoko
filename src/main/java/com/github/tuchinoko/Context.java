package com.github.tuchinoko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.tuchinoko.spi.ProcessorService;

/**
 * <p>
 * Tuchinokoの1セッションの実行に関する設定を保持するクラスです． 具体的には，以下の情報を保持します．
 * </p>
 * <ul>
 * <li>出力先</li>
 * <li>処理対象となるファイル一覧</li>
 * <li>処理を行う処理器の名前一覧</li>
 * <li>処理器に渡すパラメータ</li>
 * </ul>
 * <p>
 * このオブジェクトは1セッションの情報を保持します． セッションでは，処理器の名前一覧で指定された順で処理器が実行されます．
 * その際，各処理器は処理器名とそのセッション中に現れる順番を3桁の数字で表した数字をハイフン(-) で繋げた文字列をidとして設定されます．
 * すなわち，以下の3つの処理器が4つ指定された場合，次のようなidが設定されます．
 * </p>
 * <blockquote>processorA, processorB, processorC, processorA</blockquote>
 * <blockquote>processorA-001, processorB-002, processorC-003, processorA-004</blockquote>
 * <p>
 * 処理器に渡すパラメータは実行時オプションで指定できます． パラメータは処理器名とその処理器のパラメータをドット(.)で区切った文字列，もしくは，
 * 処理器のidとその処理器のパラメータをドット(.)で区切った文字列を， パラメータとして認識します．
 * すなわち，上記の4つの処理器が指定された場合，processorBのparamB1を指定するには
 * processorB.paramB1，もしくは，processorB-002.paramB1という名称を用います．
 * </p>
 * <p>
 * また，processorAのparamA1を指定する場合，processorA.paramA1という名称を用いると， processorA-001,
 * processorA-004の両方のparamA1を同時に指定することになります．
 * 一方，processorA-001.paramA1という名称を用いれば，processorA-001のparamA1のみを設定できます．
 * なお，簡略化のため，001のように，処理器名を省略することができます．すなわち，001はprocessorA-001と同一に扱われます．
 * </p>
 * <p>
 * {@link #getProcessors <code>getProcessors</code>}メソッドを呼び出すと，
 * {@link #getProcessorNames <code>getProcessorNames</code>}が返す全ての処理器を構築し，
 * 同じ順序の配列で返します．返された処理器はid, パラメータが設定されています． ただし，指定された名前の処理器が見つからない場合は
 * {@link UnknownProcessorException <code>UnknownProcessorException</code>}
 * が，処理器のパラメータの値が衝突した場合は {@link ArgumentsConflictException
 * <code>ArgumentsConflictException</code>}が投げられます．
 * </p>
 * <p>
 * パラメータの衝突とは，同一の処理器の同一のパラメータに異なる値が指定されることを表します． すなわち，processorA-001.paramA1 =
 * value1, processorA.paramA1 = value2の2つの方法で
 * パラメータが指定された場合，processorA-001のparamA1はvalue1か，value2
 * のどちらの値を採用すれば良いのか不明です．そのため，ArgumentsConflictException が投げられます．
 * </p>
 * 
 * @author Haruaki Tamada
 * @see Environment
 */
public class Context{
    private Environment env;
    private List<String> targets = new ArrayList<String>();
    private String destination;
    private List<String> processorNames = new ArrayList<String>();
    private Map<String, String> arguments = new HashMap<String, String>();

    /**
     * 引数に与えられたEnvironment上で動く設定オブジェクトを構築します．
     */
    public Context(Environment env){
        this.env = env;
    }

    /**
     * 引数に与えられたEnvironment上で動き，引数で与えられた contextの項目をデフォルト値として持つオブジェクトを構築します．
     */
    public Context(Context context){
        this(context.getEnvironment());
        destination = context.getDestination();
        arguments.putAll(context.arguments);
        processorNames.addAll(context.processorNames);
        targets.addAll(context.targets);
    }

    /**
     * このオブジェクトが動いているEnvironmentを返します．
     */
    public Environment getEnvironment(){
        return env;
    }

    /**
     * <p>
     * 実行する処理器を構築して配列で返します．
     * 作成された各処理器はパラメータが設定済みです．
     * </p><p>
     * 処理機は{@link #addProcessorName <code>addProcessorName</code>}
     * 追加された順（{@link #getProcessorNames <code>getProcessorNames</code>}が返す順番）
     * で構築されます．
     * </p>
     * 
     * @see #addProcessorName(String)
     * @see #getProcessorNames()
     * @throws UnknownProcessorException 指定された名前のProcessorが存在しなかった場合．
     * @throws ArgumentsConflictException 同じ名前で違う値の引数が指定されていた場合．
     */
    public Processor[] getProcessors() throws ProcessorException{
        String[] processorNames = getProcessorNames();
        Processor[] processors = new Processor[processorNames.length];
        Environment env = getEnvironment();
        for(int i = 0; i < processors.length; i++){
            ProcessorService service = env.getService(processorNames[i]);
            if(service == null){
                throw new UnknownProcessorException(processorNames[i] + " is not found");
            }
            processors[i] = service.getProcessor();
            processors[i].setId(String.format("%s-%03d", processorNames[i], i + 1));
        }
        updateArguments(processors);
        return processors;
    }

    /**
     * 処理対象となるファイルを追加します．
     * 
     * @see #getTargets
     */
    public void addTarget(String arg){
        targets.add(arg);
    }

    /**
     * 処理を行う処理器を処理機名で指定して追加します．
     * 同じ処理機名を複数回追加した場合，その処理機が追加された回数だけ実行されることになります．
     * 
     * @see #getProcessorNames
     */
    public void addProcessorName(String processorName){
        processorNames.add(processorName);
    }

    /**
     * 処理対象のファイル一覧を返します．
     * 
     * @see #addTarget
     */
    public String[] getTargets(){
        return targets.toArray(new String[targets.size()]);
    }

    /**
     * 処理結果の出力先を返します． 設定されていない場合は，現在のディレクトリを表す"."を返します．
     * 
     * @see #setDestination
     */
    public String getDestination(){
        if(destination == null){
            destination = ".";
        }
        return destination;
    }

    /**
     * 現在設定されている処理器の数を返します．
     */
    public int getProcessorCount(){
        return processorNames.size();
    }

    /**
     * 現在設定されている処理器の名前一覧を配列で返します．
     * 返されるString配列には同じ文字列が含まれている場合があります．
     * 
     * @see #addProcessorName
     * @see #getProcessors
     */
    public String[] getProcessorNames(){
        return processorNames.toArray(new String[processorNames.size()]);
    }

    /**
     * <p>
     * 引数に指定された名前で始まるArgumentの一覧を返します．
     * 返されるArgumentはTuchinokoの実行時オプションで指定されたものです．
     * </p>
     * <p>
     * 返されたArgumentに対して操作を行っても何も影響を与えません．
     * 設定された値を変更したい場合は{@link #putArgument <code>putArgument</code>}
     * メソッドを呼び出します． また，返されるArgumentオブジェクトの
     * {@link Argument#getDescription <code>getDescription</code>}
     * メソッドは常にnullを返します．
     * </p>
     * <p>
     * 引数がnullの場合，もしくは，引数に指定された名前で始まる
     * Argumentが存在しない場合は長さ0の配列を返します．
     * </p>
     */
    public Argument[] findArguments(String keyPrefix){
        List<Argument> list = new ArrayList<Argument>();
        for(Map.Entry<String, String> entry : arguments.entrySet()){
            if(entry.getKey().startsWith(keyPrefix)){
                list.add(new ArgumentImpl(entry.getKey(), entry.getValue()));
            }
        }
        return list.toArray(new Argument[list.size()]);
    }

    /**
     * 指定された名前を持つArgumentの値を返します．
     * 指定された名前のArgumentが存在しない場合はnullを返します．
     */
    public String getArgumentValue(String key){
        if(key == null){
            throw new NullPointerException();
        }
        return arguments.get(key);
    }

    /**
     * 指定された名前を持つArgumentが存在すればtrueを返し， 存在しなければfalseを返します．
     * 引数がnullの場合，NullPointerExceptionが投げられます．
     */
    public boolean hasArgument(String key){
        if(key == null){
            throw new NullPointerException();
        }
        return arguments.containsKey(key);
    }

    /**
     * 指定された名前のArgumentの値を更新します．
     * keyがnullの場合はNullPointerExceptionが投げられます．
     * valueがnullの場合は値がnullに設定されます．
     */
    public void putArgument(String key, String value){
        if(key == null){
            throw new NullPointerException();
        }
        else{
            arguments.put(key, value);
        }
    }

    /**
     * 処理結果の出力先を設定します．
     * 
     * @see #getDestination
     */
    public void setDestination(String dest){
        this.destination = dest;
    }

    private void updateArguments(Processor[] processors) throws ArgumentsConflictException{
        Map<String, Map<String, Argument>> argmap = new HashMap<String, Map<String, Argument>>();
        List<String> conflictList = new ArrayList<String>();
        // idから引数を更新．
        for(Processor processor: processors){
            String id = processor.getId();
            String number = id.substring(processor.getProcessorName().length() + 1);

            updateArguments(id, id, argmap, findArguments(id + "."), conflictList);
            updateArguments(number, id, argmap, findArguments(number + "."), conflictList);
        }

        // Processor名から引数を更新．
        for(Processor processor : processors){
            String name = processor.getProcessorName();
            updateArguments(name, processor.getId(), argmap,
                    findArguments(name + "."), conflictList);
        }
        if(conflictList.size() == 0){
            for(Processor processor : processors){
                Map<String, Argument> submap = argmap.get(processor.getId());
                Arguments args = processor.getArguments();
                if(submap != null){
                    for(Argument arg : submap.values()){
                        args.putValue(arg);
                    }
                }
            }
        }
        else{
            throw new ArgumentsConflictException(
                    conflictList.toArray(new String[conflictList.size()]));
        }
    }

    private void updateArguments(String prefix, String id,
            Map<String, Map<String, Argument>> argmap, Argument[] args,
            List<String> conflictList){
        for(Argument arg : args){
            Map<String, Argument> submap = argmap.get(id);
            String key = arg.getName().substring(prefix.length() + 1);
            if(submap == null){
                submap = new HashMap<String, Argument>();
                argmap.put(id, submap);
            }
            if(submap.get(key) != null){
                Argument argument = submap.get(key);
                String value = argument.getValue();
                if((value != null && !value.equals(arg.getValue())) ||
                        (value == null && arg.getValue() != null)){
                    conflictList.add(id + "." + key);
                }
            }
            else{
                submap.put(key, new ArgumentImpl(key, arg.getValue()));
            }
        }
    }
}
