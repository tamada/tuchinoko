package com.github.tuchinoko;

/**
 * 1つの引数を表すインターフェースです．
 * 引数は名前と値と説明を含みます．
 * 引数の名前にnullは許容しません．
 * 
 * @author Haruaki Tamada
 */
public interface Argument{
    /**
     * 引数の名前を返します．必ずnull以外の値を返さなければいけません．
     */
    public String getName();

    /**
     * 引数の値を返します．nullが返される場合もあります．
     */
    public String getValue();

    /**
     * 引数の解説を返します．nullが返される場合もあります．
     */
    public String getDescription();
}
