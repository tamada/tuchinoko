package com.github.tuchinoko.spi;

import com.github.tuchinoko.Arguments;
import com.github.tuchinoko.Processor;
import com.github.tuchinoko.ProcessorBuildException;
import com.github.tuchinoko.utils.Author;
import com.github.tuchinoko.utils.Organization;
import com.github.tuchinoko.utils.Provider;

/**
 * ProcessorServiceのデフォルト実装を含む抽象クラス．
 *
 * @author Haruaki Tamada
 */
public abstract class AbstractProcessorService implements ProcessorService{
    private Arguments args;
    private Provider provider;

    /**
     * サブクラスのためのコンストラクタ．
     */
    protected AbstractProcessorService(){
        args = createDefaultArguments();
        provider = createProvider();
    }

    /**
     * このSPIが提供する処理器のパラメータとその初期値を表すArgumentsを作成して返します．
     * このメソッドはインスタンス化されたときに一度だけ実行されます．
     * 
     * @see #getDefaultArguments
     */
    protected abstract Arguments createDefaultArguments();

    /**
     * このProcessorの提供者情報を作成して返します．
     * このメソッドはインスタンス化されたときに一度だけ実行されます．
     * 
     * @see #getProvider
     */
    protected abstract Provider createProvider();

    /**
     * {@link Processor <code>Processor</code>}を作成して返します．
     * このメソッドは必要に応じて何度も呼び出されます．
     */
    protected abstract Processor createProcessor() throws ProcessorBuildException;

    @Override
    public final Author[] getAuthors(){
        Provider currentProvider = getProvider();
        if(currentProvider != null){
            return currentProvider.getAuthors();
        }
        return new Author[0];
    }

    /**
     * <p>
     * このProviderが定義する{@link Processor <code>Processor</code>}のデフォルトの
     * {@link Arguments <code>Arguments</code>}を返します．
     * 返されたArgumentsに変更を加えても，デフォルト値に影響を与えません．
     * </p><p>
     * デフォルト値はどのような方法であっても変更不可能です．
     * 何らかの理由により，デフォルト値を変更したい場合は，新たな
     * Processorを定義し直さなければいけません．
     * </p><p>
     * 返されたArgumentsの値を変更したい場合，返されたオブジェクトに対して，
     * {@link Arguments#putValue(String, String) Arguments#putValue}
     * メソッドを呼び出して変更してください．
     * この変更は自由で，かつ，デフォルト値に全く影響を与えません．
     * </p>
     */
    @Override
    public final Arguments getDefaultArguments(){
        return new Arguments(args);
    }

    /**
     * このSPIが作成する処理器の説明を返します．
     */
    @Override
    public abstract String getDescription();

    /**
     * このSPIが作成する処理器を作成した団体情報を返します．
     * {@link #getProvider}がnullを返すとき，このメソッドはnullを返します．
     * @see Provider#getOrganization
     */
    @Override
    public final Organization getOrganization(){
        Provider currentProvider = getProvider();
        if(currentProvider != null){
            return currentProvider.getOrganization();
        }
        return null;
    }

    /**
     * このSPIに対応する処理器を作成して返します．
     * Processorの作成に失敗した場合，ProcessorBuildExceptionが投げられます．
     */
    @Override
    public final Processor getProcessor() throws ProcessorBuildException{
        Processor processor = createProcessor();
        if(processor == null){
            throw new ProcessorBuildException();
        }

        return processor;
    }

    /**
     * このSPIが作成する処理器の名前を返します．
     */
    @Override
    public abstract String getProcessorName();

    /**
     * このSPIが作成する処理器の提供者情報を返します．
     * @see #createProvider
     */
    @Override
    public final Provider getProvider(){
        return provider;
    }
}
