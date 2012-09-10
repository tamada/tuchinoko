package com.github.tuchinoko;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SummaryTest{
    private Summary summary;

    @Before
    public void setUp() throws Exception{
        summary = new Summary("nop-001");
        summary.putEntry("entry1", "value1");
        summary.putEntry("entry2", "value2");
    }

    @Test
    public void testBasic() throws Exception{
        Assert.assertEquals("nop-001", summary.getProcessorId());

        Assert.assertEquals(2, summary.getEntryCount());
        Assert.assertEquals("value1", summary.getEntry("entry1"));
        Assert.assertEquals("value2", summary.getEntry("entry2"));
        Assert.assertNull(summary.getEntry("entry3"));

        summary.removeEntry("entry3");
        summary.removeEntry("entry1");
        summary.removeEntry(null);
        Assert.assertEquals(1, summary.getEntryCount());
        Assert.assertEquals("value2", summary.getEntry("entry2"));
        Assert.assertNull(summary.getEntry("entry1"));

        summary.setProcessorId("new-id-001");
        Assert.assertEquals("new-id-001", summary.getProcessorId());
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck1() throws Exception{
        summary.setProcessorId(null);
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck2() throws Exception{
        summary.getEntry(null);
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck3() throws Exception{
        summary.putEntry(null, "this method will throw NullPointerException");
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck() throws Exception{
        new Summary.Entry("key1", null);
    }
}
