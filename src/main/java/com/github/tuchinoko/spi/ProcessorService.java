package com.github.tuchinoko.spi;

import com.github.tuchinoko.Arguments;
import com.github.tuchinoko.Processor;
import com.github.tuchinoko.ProcessorBuildException;
import com.github.tuchinoko.utils.Author;
import com.github.tuchinoko.utils.Organization;
import com.github.tuchinoko.utils.Provider;

/**
 * {@link Processor <code>Processor</code>}を提供するためのSPIです．
 *
 * @author Haruaki Tamada
 */
public interface ProcessorService{
    /**
     * このSPIが返す{@link Processor <code>Processor</code>}の初期設定値を返します．
     * 返された{@link Arguments <code>Arguments</code>}オブジェクトを変更しても他に影響を与えません．
     * @return 初期設定値
     */
    Arguments getDefaultArguments();

    /**
     * このSPIが返す{@link Processor <code>Processor</code>}の解説を返します．
     * @return このSPIが対応するProcessorの解説
     */
    String getDescription();

    /**
     * {@link Processor <code>Processor</code>}を返します．
     * 返されるProcessorのメソッドは並行して呼び出される可能性があります．
     * そのため，並行して呼び出されても影響を受けないよう，実装しておかなければなりません．
     * @return 作成されたProcessorオブジェクト
     */
    Processor getProcessor() throws ProcessorBuildException;

    /**
     * このSPIが対応する{@link Processor <code>Processor</code>}の名前を返します．
     * @return 対応するProcessorの名前
     */
    String getProcessorName();

    /**
     * このSPI並びに{@link Processor <code>Processor</code>}
     * の作成者のリストを返します．
     * @return 作成者のリスト
     */
    Author[] getAuthors();

    /**
     * このSPI並びに{@link Processor <code>Processor</code>}
     * の作成者の団体を返します．
     * @return 作成者の所属する団体
     */
    Organization getOrganization();

    /**
     * このSPI並びに{@link Processor <code>Processor</code>}
     * の作成者のリストとその団体のオブジェクトを返します．
     * @return 作成者とその団体を収めたオブジェクト
     */
    Provider getProvider();
}
