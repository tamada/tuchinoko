package com.github.tuchinoko;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.tuchinoko.spi.DefaultProcessorServiceLoader;
import com.github.tuchinoko.spi.ProcessorService;
import com.github.tuchinoko.spi.ProcessorServiceLoader;

public class DefaultProcessorServiceLoaderTest{
    private ProcessorServiceLoader loader;

    @Before
    public void setUp() throws Exception{
        loader = new DefaultProcessorServiceLoader();
    }

    @Test
    public void testBasic() throws Exception{
        ProcessorServicePool pool = loader.loadServices();

        Assert.assertEquals(1, pool.getServiceCount());

        ProcessorService service1 = pool.getService("nop");
        Assert.assertEquals("nop", service1.getProcessorName());
    }

    @Test
    public void testBasic2() throws Exception{
        ProcessorServicePool pool = new ProcessorServicePool();
        loader.loadServices(pool);

        Assert.assertEquals(1, pool.getServiceCount());
        ProcessorService service1 = pool.getService("nop");
        Assert.assertEquals("nop", service1.getProcessorName());
    }
}
