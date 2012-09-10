package com.github.tuchinoko;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProcessorServicePoolTest{
    private ProcessorServicePool pool;
    private Environment env;

    @Before
    public void setUp() throws Exception{
        env = new Environment();
        pool = new ProcessorServicePool();

        pool.addProvider(env.getService("nop"));
    }

    @Test
    public void testBasic() throws Exception{
        Assert.assertEquals(1, pool.getServiceCount());
        Assert.assertTrue(pool.contains("nop"));
        Assert.assertTrue(pool.contains(env.getService("nop")));

        Assert.assertTrue(pool.removeProvider(env.getService("nop")));

        Assert.assertEquals(0, pool.getServiceCount());
        Assert.assertFalse(pool.contains("strenc"));
        Assert.assertFalse(pool.contains("nop"));
    }

    @Test
    public void testBasic2() throws Exception{
        pool = new ProcessorServicePool(pool);

        Assert.assertEquals(1, pool.getServiceCount());
        Assert.assertTrue(pool.contains("nop"));
        Assert.assertTrue(pool.contains(env.getService("nop")));

        Assert.assertTrue(pool.removeProvider(env.getService("nop")));

        Assert.assertEquals(0, pool.getServiceCount());
        Assert.assertFalse(pool.contains("strenc"));
        Assert.assertFalse(pool.contains("nop"));
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck1() throws Exception{
        Assert.assertFalse(pool.removeProvider(null));
    }
}
