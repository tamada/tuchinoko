package com.github.tuchinoko.io;

import java.text.DateFormat;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.tuchinoko.Summary;

public class PropertiesSummaryPrinterTest{
    private PropertiesSummaryPrinter printer;
    private Summary summary;

    @Before
    public void setUp(){
        summary = new Summary("sample");
        summary.putEntry("aaa.bbb", "valueAB");
        summary.putEntry("aaa.ccc", "valueAC");
        summary.putEntry("ddd.eee", "valueDE");
        summary.putEntry("fff",     "valueF");

        printer = new PropertiesSummaryPrinter();
    }

    @Test
    public void testBasic(){
        String ln = System.getProperty("line.separator");
        String summaryString = printer.getSummary(summary);
        String time = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Assert.assertEquals("# " + time + ln +
                            "sample.aaa.bbb=valueAB" + ln +
                            "sample.aaa.ccc=valueAC" + ln +
                            "sample.ddd.eee=valueDE" + ln +
                            "sample.fff=valueF" + ln, summaryString);
    }
}
