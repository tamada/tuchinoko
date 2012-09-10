package com.github.tuchinoko;

import java.io.File;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocalClassLoaderBuilderTest{
    private LocalClassLoaderBuilder builder;

    @Before
    public void setUp(){
        builder = new LocalClassLoaderBuilder();
    }

    @Test
    public void testBasic() throws Exception{
        URL[] url = builder.collectUrls();

        Assert.assertEquals("local", builder.getName());
        Assert.assertEquals(1, url.length);
        Assert.assertEquals(new File("target/classes").toURI().toURL(), url[0]);
    }

    @Test
    public void testSystemEnvironment() throws Exception{
        System.setProperty("tuchinoko.lib", "target/commons-cli-1.1.jar" + File.pathSeparator + "target/asm-4.0.jar");
        URL[] url = builder.collectUrls();

        Assert.assertEquals(3, url.length);
        Assert.assertEquals(new File("target/classes").toURI().toURL(), url[0]);
        Assert.assertEquals(new File("target/commons-cli-1.1.jar").toURI().toURL(), url[1]);
        Assert.assertEquals(new File("target/asm-4.0.jar").toURI().toURL(), url[2]);
    }
}
