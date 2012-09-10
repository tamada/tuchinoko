package com.github.tuchinoko.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetType;

public class DirectoryTargetSourceTest{
    private DirectoryTargetSource source;

    @Before
    public void setUp() throws IOException{
        source = new DirectoryTargetSource(new File("target/test-classes/resources/"));
    }

    @Test
    public void testBasic() throws IOException{
        Assert.assertEquals(new File("target/test-classes/resources"), new File(source.getName()));

        Iterator<ProcessTarget> iterator = source.iterator();
        Assert.assertTrue(source.contains("aaa/bbb/ccc/Class1.class"));
        Assert.assertFalse(source.contains("aaa/bbb/ccc/Class100.class"));

        Assert.assertTrue(iterator.hasNext());

        ProcessTarget target1 = iterator.next();
        Assert.assertEquals("aaa/bbb/ccc/Class1.class", target1.getName());
        Assert.assertEquals(TargetType.CLASS_FILE, target1.getType());
        Assert.assertSame(source, target1.getTargetSource());

        ProcessTarget target2 = iterator.next();
        Assert.assertEquals("aaa/bbb/ccc/Class2.class", target2.getName());
        Assert.assertEquals(TargetType.CLASS_FILE, target2.getType());
        Assert.assertSame(source, target2.getTargetSource());

        ProcessTarget target3 = iterator.next();
        Assert.assertEquals("aaa/bbb/Class3.class", target3.getName());
        Assert.assertEquals(TargetType.CLASS_FILE, target3.getType());
        Assert.assertSame(source, target3.getTargetSource());

        ProcessTarget target4 = iterator.next();
        Assert.assertEquals("aaa/bbb/Class4.class", target4.getName());
        Assert.assertEquals(TargetType.CLASS_FILE, target4.getType());
        Assert.assertSame(source, target4.getTargetSource());

        ProcessTarget target5 = iterator.next();
        Assert.assertEquals("aaa/bbb/ddd/Class5.class", target5.getName());
        Assert.assertEquals(TargetType.CLASS_FILE, target5.getType());
        Assert.assertSame(source, target5.getTargetSource());

        ProcessTarget target6 = iterator.next();
        Assert.assertEquals("aaa/bbb/ddd/Class6.class", target6.getName());
        Assert.assertEquals(TargetType.CLASS_FILE, target6.getType());
        Assert.assertSame(source, target6.getTargetSource());

        ProcessTarget target7 = iterator.next();
        Assert.assertEquals("aaa/bbb/Service1.xml", target7.getName());
        Assert.assertEquals(TargetType.XML_FILE, target7.getType());
        Assert.assertSame(source, target7.getTargetSource());

        ProcessTarget target8 = iterator.next();
        Assert.assertEquals("aaa/Class7.class", target8.getName());
        Assert.assertEquals(TargetType.CLASS_FILE, target8.getType());
        Assert.assertSame(source, target8.getTargetSource());

        ProcessTarget target9 = iterator.next();
        Assert.assertEquals("aaa/hoge.jpg", target9.getName());
        Assert.assertEquals(TargetType.JPG_FILE, target9.getType());
        Assert.assertSame(source, target9.getTargetSource());

        ProcessTarget target0 = iterator.next();
        Assert.assertEquals("META-INF/services/tuchinoko.spi.ProcessorService", target0.getName());
        Assert.assertEquals(TargetType.SERVICE_DESCRIPTOR, target0.getType());
        Assert.assertSame(source, target0.getTargetSource());

        ProcessTarget target10 = iterator.next();
        Assert.assertEquals("test.jar", target10.getName());
        Assert.assertEquals(TargetType.JAR_FILE, target10.getType());
        Assert.assertSame(source, target10.getTargetSource());

        Assert.assertFalse(iterator.hasNext());
    }
}
