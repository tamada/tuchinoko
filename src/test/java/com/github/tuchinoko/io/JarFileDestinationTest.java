package com.github.tuchinoko.io;

import java.io.File;
import java.io.OutputStream;
import java.util.jar.JarFile;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;

public class JarFileDestinationTest{
    private JarFileDestination dest;

    @Before
    public void setUp() throws Exception{
        dest = new JarFileDestination(new File("test_dest.jar"));
    }

    @After
    public void tearDown() throws Exception{
        new File("test_dest.jar").delete();
    }

    @Test
    public void testBasic() throws Exception{
        TargetSource source = new JarFileTargetSource(new File("target/test-classes/resources/test.jar"));
        for(ProcessTarget target: source){
            OutputStream out = dest.getOutput(target);
            out.close();
        }
        dest.close();

        JarFile file = new JarFile("test_dest.jar");
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/ccc/Class1.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/ccc/Class2.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/Class3.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/Class4.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/ddd/Class5.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/ddd/Class6.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/Class7.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/Service1.xml"));
        Assert.assertNotNull(file.getJarEntry("aaa/hoge.jpg"));
        Assert.assertNotNull(file.getJarEntry("META-INF/services/tuchinoko.spi.ProcessorService"));
        file.close();
    }

    @Test
    public void testBasic2() throws Exception{
        TargetSource source = new JarFileTargetSource(new File("target/test-classes/resources/test.jar"));
        for(ProcessTarget target: source){
            OutputStream out = dest.getOutput(target);
            out.close();
        }
        dest.close();

        JarFile file = new JarFile("test_dest.jar");
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/ccc/Class1.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/ccc/Class2.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/Class3.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/Class4.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/ddd/Class5.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/ddd/Class6.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/Class7.class"));
        Assert.assertNotNull(file.getJarEntry("aaa/bbb/Service1.xml"));
        Assert.assertNotNull(file.getJarEntry("aaa/hoge.jpg"));
        Assert.assertNotNull(file.getJarEntry("META-INF/services/tuchinoko.spi.ProcessorService"));
        file.close();
    }
}
