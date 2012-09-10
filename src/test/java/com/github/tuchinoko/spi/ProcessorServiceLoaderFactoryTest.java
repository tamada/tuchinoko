package com.github.tuchinoko.spi;

import org.junit.Assert;
import org.junit.Test;

public class ProcessorServiceLoaderFactoryTest{
    @Test
    public void testBasic() throws Exception{
        ProcessorServiceLoaderFactory factory = ProcessorServiceLoaderFactory.getInstance();

        ProcessorServiceLoader loader = factory.getLoader();
        Assert.assertEquals("default", loader.getName());
        Assert.assertTrue(loader instanceof DefaultProcessorServiceLoader);
    }

    @Test
    public void testDefaultLoader() throws Exception{
        ProcessorServiceLoaderFactory factory = ProcessorServiceLoaderFactory.getInstance();

        ProcessorServiceLoader loader = factory.getLoader("default");
        Assert.assertEquals("default", loader.getName());
        Assert.assertTrue(loader instanceof DefaultProcessorServiceLoader);
    }
}
