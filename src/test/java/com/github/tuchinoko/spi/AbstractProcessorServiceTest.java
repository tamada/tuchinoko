package com.github.tuchinoko.spi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.tuchinoko.Arguments;
import com.github.tuchinoko.Processor;
import com.github.tuchinoko.ProcessorBuildException;
import com.github.tuchinoko.utils.Author;
import com.github.tuchinoko.utils.Organization;
import com.github.tuchinoko.utils.Provider;

public class AbstractProcessorServiceTest{
    private ProcessorService service;
    private ProcessorService service2;
    private ProcessorService service3;

    @Before
    public void setUp(){
        service = new AbstractProcessorService(){
            @Override
            public Processor createProcessor(){
                return null;
            }

            @Override
            public Arguments createDefaultArguments(){
                return new Arguments();
            }

            @Override
            public String getDescription(){
                return null;
            }

            @Override
            public String getProcessorName(){
                return "test";
            }

            @Override
            public Provider createProvider(){
                return Provider.TUCHINOKO_PROVIDER;
            }
        };

        service2 = new AbstractProcessorService(){
            @Override
            public Processor createProcessor(){
                return null;
            }

            @Override
            public Arguments createDefaultArguments(){
                return new Arguments();
            }

            @Override
            public String getDescription(){
                return "description2";
            }

            @Override
            public String getProcessorName(){
                return "test2";
            }

            @Override
            public Provider createProvider(){
                return Provider.TUCHINOKO_PROVIDER;
            }
        };

        service3 = new AbstractProcessorService(){
            @Override
            public Processor createProcessor(){
                return null;
            }

            @Override
            public Arguments createDefaultArguments(){
                return new Arguments();
            }

            @Override
            public String getDescription(){
                return "description3";
            }

            @Override
            public String getProcessorName(){
                return "test3";
            }

            @Override
            public Provider createProvider(){
                return null;
            }
        };
    }

    @Test
    public void testBasic() throws Exception{
        Assert.assertEquals(Provider.TUCHINOKO_PROVIDER, service.getProvider());
        Assert.assertEquals("test", service.getProcessorName());
        Assert.assertNull(service.getDescription());

        Author[] authors = service.getAuthors();
        Organization org = service.getOrganization();

        Assert.assertEquals("Tuchinoko Provider", service.getProvider().getName());
        Assert.assertEquals(1, authors.length);
        Assert.assertEquals("Haruaki Tamada", authors[0].getName());
        Assert.assertNull(org);

        Assert.assertEquals("test2", service2.getProcessorName());
        Assert.assertEquals("description2", service2.getDescription());
        Assert.assertEquals(Provider.TUCHINOKO_PROVIDER, service2.getProvider());

        Arguments args = service.getDefaultArguments();
        Assert.assertEquals(0, args.getArgumentCount());
    }

    @Test(expected=ProcessorBuildException.class)
    public void testUnknownProcessor1() throws Exception{
        service.getProcessor();
    }

    @Test(expected=ProcessorBuildException.class)
    public void testUnknownProcessor2() throws Exception{
        service2.getProcessor();
    }

    @Test(expected=ProcessorBuildException.class)
    public void testUnknownProcessor3() throws Exception{
        service3.getProcessor();
    }

    @Test
    public void testNullProvider() throws Exception{
        Assert.assertEquals("test3", service3.getProcessorName());
        Assert.assertEquals("description3", service3.getDescription());

        Author[] authors = service3.getAuthors();
        Assert.assertEquals(0, authors.length);
        Assert.assertNull(service3.getOrganization());
    }
}
