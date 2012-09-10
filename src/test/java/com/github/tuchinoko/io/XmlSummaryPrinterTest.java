package com.github.tuchinoko.io;

import java.text.DateFormat;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.tuchinoko.Summary;

public class XmlSummaryPrinterTest{
    private XmlSummaryPrinter printer;
    private Summary summary;

    @Before
    public void setUp(){
        summary = new Summary("sample");
        summary.putEntry("aaa.bbb", "valueAB");
        summary.putEntry("aaa.ccc", "valueAC");
        summary.putEntry("ddd.eee", "valueDE");
        summary.putEntry("fff",     "valueF");

        printer = new XmlSummaryPrinter();
    }

    @Test
    public void testBasic(){
        String ln = System.getProperty("line.separator");
        String summaryString = printer.getSummary(summary);
        String time = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Assert.assertEquals("  <sample timestamp=\"" + time + "\">" + ln +
                            "    <aaa>" + ln +
                            "      <bbb>valueAB</bbb>" + ln +
                            "      <ccc>valueAC</ccc>" + ln +
                            "    </aaa>" + ln +
                            "    <ddd>" + ln +
                            "      <eee>valueDE</eee>" + ln +
                            "    </ddd>" + ln +
                            "    <fff>valueF</fff>" + ln +
                            "  </sample>" + ln, summaryString);
    }
}
