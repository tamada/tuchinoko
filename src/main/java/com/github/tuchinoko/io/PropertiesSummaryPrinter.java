package com.github.tuchinoko.io;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;

import com.github.tuchinoko.Summary;

/**
 * <p>
 * Summaryをプロパティファイル形式で出力するためのSummaryPrinterです．
 * </p><p>
 * 出力形式は，プロパティファイル形式で，Summaryの各エントリを1行に出力します．
 * 各行はエントリのキーとそれに対応する値をイコール記号(=)で区切った文字列で表されます．
 * エントリのキーはイコール記号の左辺に書かれ，キーに対応する値はイコールの右辺に書かれます．
 * エントリのキーはProcessor IdとSummaryのエントリのキーをドット「.」
 * で区切った文字列で表されます．
 * また，シャープ記号(#)から改行文字まではコメントとして扱われます．
 * </p><p>
 * すなわち，次のようなプロパティ形式の文字列が出力されます．
 * </p>
 * <blockquoxte><pre> # comment
 * processorId.summary-key1=summary-value1
 * processorId.summary-key2=summary-value2
 *            :</pre></blockquote>
 * <p>
 * 上記のcommentがコメントを表し，processorIdがプロセッサのId，
 * summary-key1, summary-key2がSummaryの各エントリ，
 * summary-value1, summary-value2がSummaryのエントリに対応する値を表しています．
 * </p>
 *
 * @author Haruaki Tamada
 */
public class PropertiesSummaryPrinter extends SummaryPrinter{
    /**
     * <p>
     * 指定されたSummaryを指定された出力先へ出力します．
     * 出力形式はプロパティファイル形式です．
     * </p>
     */
    @Override
    public void print(Summary summary, PrintWriter out){
        out.printf("# %s%n", DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
        for(Summary.Entry entry: summary){
            out.printf("%s.%s=%s%n", summary.getProcessorId(), entry.getKey(), entry.getValue());
        }
    }
}
