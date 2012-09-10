package com.github.tuchinoko;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.tuchinoko.spi.ProcessorService;

public class EnvironmentTest{
    private Environment env;

    @Before
    public void setUp() throws Exception{
        env = new Environment();
    }

    @Test
    public void testBasic() throws Exception{
        Assert.assertEquals(1, env.getServiceCount());

        ProcessorService service1 = env.getService("nop");
        Assert.assertNotNull(service1);
        Assert.assertEquals("nop", service1.getProcessorName());

        Iterator<ProcessorService> iterator = env.iterator();
        Assert.assertTrue(iterator.hasNext());

        ProcessorService service3 = iterator.next();
        Assert.assertNotNull(service3);
        Assert.assertEquals("nop", service3.getProcessorName());
    }

    @Test
    public void testBasic2() throws Exception{
        env = new Environment(ClassLoader.getSystemClassLoader());

        ProcessorService service1 = env.getService("nop");
        Assert.assertNotNull(service1);
        Assert.assertEquals("nop", service1.getProcessorName());

        Iterator<ProcessorService> iterator = env.iterator();
        Assert.assertTrue(iterator.hasNext());

        ProcessorService service3 = iterator.next();
        Assert.assertNotNull(service3);
        Assert.assertEquals("nop", service3.getProcessorName());
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck1() throws Exception{
        env.getService(null);
    }
}
