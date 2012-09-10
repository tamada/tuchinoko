package com.github.tuchinoko.io;

import java.io.File;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.tuchinoko.ProcessTarget;
import com.github.tuchinoko.TargetSource;

public class DirectoryDestinationTest{
    private DirectoryDestination dest;

    @Before
    public void setUp() throws Exception{
        File file = new File("test_dest");
        file.mkdirs();
        dest = new DirectoryDestination(file);
    }

    @After
    public void tearDown() throws Exception{
        removeRecursive(new File("test_dest"));
    }

    private void removeRecursive(File file){
        if(file.isDirectory()){
            for(File f: file.listFiles()){
                removeRecursive(f);
            }
            file.delete();
        }
        else{
            file.delete();
        }
    }

    @Test
    public void testBasic() throws Exception{
        TargetSource source = new DirectoryTargetSource(new File(
                "target/test-classes/resources/"));
        for(ProcessTarget target: source){
            OutputStream out = dest.getOutput(target);
            out.close();
        }

        File file01 = new File(
                "test_dest/META-INF/services/tuchinoko.spi.ProcessorService");
        File file02 = new File("test_dest/aaa/Class7.class");
        File file03 = new File("test_dest/aaa/hoge.jpg");
        File file04 = new File("test_dest/aaa/bbb/Class3.class");
        File file05 = new File("test_dest/aaa/bbb/Class4.class");
        File file06 = new File("test_dest/aaa/bbb/Service1.xml");
        File file07 = new File("test_dest/aaa/bbb/ccc/Class1.class");
        File file08 = new File("test_dest/aaa/bbb/ccc/Class2.class");
        File file09 = new File("test_dest/aaa/bbb/ddd/Class5.class");
        File file10 = new File("test_dest/aaa/bbb/ddd/Class6.class");
        File file11 = new File("test_dest/test.jar");

        Assert.assertTrue(file01.exists());
        Assert.assertTrue(file02.exists());
        Assert.assertTrue(file03.exists());
        Assert.assertTrue(file04.exists());
        Assert.assertTrue(file05.exists());
        Assert.assertTrue(file06.exists());
        Assert.assertTrue(file07.exists());
        Assert.assertTrue(file08.exists());
        Assert.assertTrue(file09.exists());
        Assert.assertTrue(file10.exists());
        Assert.assertTrue(file11.exists());
    }

    @Test
    public void testBasic2() throws Exception{
        TargetSource source = new JarFileTargetSource(new File(
                "target/test-classes/resources/test.jar"));
        for(ProcessTarget target: source){
            OutputStream out = dest.getOutput(target);
            out.close();
        }

        File file00 = new File("test_dest/META-INF/MANIFEST.MF");
        File file01 = new File(
                "test_dest/META-INF/services/tuchinoko.spi.ProcessorService");
        File file02 = new File("test_dest/aaa/Class7.class");
        File file03 = new File("test_dest/aaa/hoge.jpg");
        File file04 = new File("test_dest/aaa/bbb/Class3.class");
        File file05 = new File("test_dest/aaa/bbb/Class4.class");
        File file06 = new File("test_dest/aaa/bbb/Service1.xml");
        File file07 = new File("test_dest/aaa/bbb/ccc/Class1.class");
        File file08 = new File("test_dest/aaa/bbb/ccc/Class2.class");
        File file09 = new File("test_dest/aaa/bbb/ddd/Class5.class");
        File file10 = new File("test_dest/aaa/bbb/ddd/Class6.class");

        Assert.assertTrue(file00.exists());
        Assert.assertTrue(file01.exists());
        Assert.assertTrue(file02.exists());
        Assert.assertTrue(file03.exists());
        Assert.assertTrue(file04.exists());
        Assert.assertTrue(file05.exists());
        Assert.assertTrue(file06.exists());
        Assert.assertTrue(file07.exists());
        Assert.assertTrue(file08.exists());
        Assert.assertTrue(file09.exists());
        Assert.assertTrue(file10.exists());
    }
}
