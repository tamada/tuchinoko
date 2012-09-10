package com.github.tuchinoko.io;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.tuchinoko.Summary;

/**
 * {@link Summary <code>Summary</code>}
 * を特定のフォーマットで出力するための抽象クラスです．
 *
 * @author Haruaki Tamada
 */
public abstract class SummaryPrinter{
    /**
     * 特定のフォーマットで指定されたSummaryを指定された場所に出力するメソッドです．
     * 具体的な実装はサブクラスで行われます．
     */
    public abstract void print(Summary summary, PrintWriter out);

    /**
     * Summaryを文字列で得るためのメソッドです．
     * フォーマットはサブクラスの実装に依存します．
     */
    public String getSummary(Summary summary){
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);

        print(summary, out);

        out.flush();
        out.close();
        String string = new String(sw.getBuffer());

        return string;
    }

}
