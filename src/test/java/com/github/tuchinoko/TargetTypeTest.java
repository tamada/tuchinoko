package com.github.tuchinoko;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

public class TargetTypeTest{
    @Test(expected=NullPointerException.class)
    public void testGetTypeNullCheck(){
        TargetType.getType(null);
    }

    @Test
    public void testGetType(){
        TargetType type01 = TargetType.getType("hoge.apt");
        Assert.assertEquals("APT_FILE", type01.toString());

        TargetType type02 = TargetType.getType("hoge.html");
        Assert.assertEquals("HTML_FILE", type02.toString());

        TargetType type03 = TargetType.getType("hoge.htm");
        Assert.assertEquals("HTML_FILE", type03.toString());

        TargetType type04 = TargetType.getType("hoge.xml");
        Assert.assertEquals("XML_FILE", type04.toString());

        TargetType type05 = TargetType.getType("hoge.class");
        Assert.assertEquals("CLASS_FILE", type05.toString());

        TargetType type06 = TargetType.getType("hoge.java");
        Assert.assertEquals("JAVA_FILE", type06.toString());

        TargetType type07 = TargetType.getType("hoge.jnlp");
        Assert.assertEquals("JNLP_FILE", type07.toString());

        TargetType type08 = TargetType.getType("hoge.properties");
        Assert.assertEquals("PROPERTIES_FILE", type08.toString());

        TargetType type09 = TargetType.getType("META-INF/MANIFEST.MF");
        Assert.assertEquals("MANIFEST", type09.toString());

        TargetType type10 = TargetType.getType("META-INF/services/hoge");
        Assert.assertEquals("SERVICE_DESCRIPTOR", type10.toString());

        TargetType type11 = TargetType.getType("hoge.bmp");
        Assert.assertEquals("BMP_FILE", type11.toString());

        TargetType type12 = TargetType.getType("hoge.jpg");
        Assert.assertEquals("JPG_FILE", type12.toString());

        TargetType type13 = TargetType.getType("hoge.jpeg");
        Assert.assertEquals("JPG_FILE", type13.toString());

        TargetType type14 = TargetType.getType("hoge.gif");
        Assert.assertEquals("GIF_FILE", type14.toString());

        TargetType type15 = TargetType.getType("hoge.png");
        Assert.assertEquals("PNG_FILE", type15.toString());

        TargetType type16 = TargetType.getType("hoge.jar");
        Assert.assertEquals("JAR_FILE", type16.toString());

        TargetType type17 = TargetType.getType("hoge.zip");
        Assert.assertEquals("ZIP_FILE", type17.toString());

        TargetType type18 = TargetType.getType("hoge.war");
        Assert.assertEquals("WAR_FILE", type18.toString());

        TargetType type19 = TargetType.getType("hoge.ear");
        Assert.assertEquals("EAR_FILE", type19.toString());

        TargetType type20 = TargetType.getType("hoge.bz2");
        Assert.assertEquals("BZIP2_FILE", type20.toString());

        TargetType type21 = TargetType.getType("hoge.gz");
        Assert.assertEquals("GZIP_FILE", type21.toString());

        TargetType type22 = TargetType.getType("hoge.tar");
        Assert.assertEquals("TAR_FILE", type22.toString());

        TargetType type23 = TargetType.getType("hoge");
        Assert.assertEquals("FILE", type23.toString());
    }

    @Test
    public void testTargetType() throws Exception{
        Iterator<TargetType> iterator = TargetType.values();

        Assert.assertTrue(iterator.hasNext());
        TargetType type01 = iterator.next();
        Assert.assertEquals("APT_FILE", type01.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type06 = iterator.next();
        Assert.assertEquals("HTML_FILE", type06.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type14 = iterator.next();
        Assert.assertEquals("XML_FILE", type14.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type03 = iterator.next();
        Assert.assertEquals("CLASS_FILE", type03.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type04 = iterator.next();
        Assert.assertEquals("JAVA_FILE", type04.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type10 = iterator.next();
        Assert.assertEquals("MANIFEST", type10.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type08 = iterator.next();
        Assert.assertEquals("JNLP_FILE", type08.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type12 = iterator.next();
        Assert.assertEquals("PROPERTIES_FILE", type12.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type13 = iterator.next();
        Assert.assertEquals("SERVICE_DESCRIPTOR", type13.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type02 = iterator.next();
        Assert.assertEquals("BMP_FILE", type02.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type05 = iterator.next();
        Assert.assertEquals("GIF_FILE", type05.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type09 = iterator.next();
        Assert.assertEquals("JPG_FILE", type09.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type11 = iterator.next();
        Assert.assertEquals("PNG_FILE", type11.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type07 = iterator.next();
        Assert.assertEquals("JAR_FILE", type07.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type15 = iterator.next();
        Assert.assertEquals("ZIP_FILE", type15.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type20 = iterator.next();
        Assert.assertEquals("WAR_FILE", type20.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type21 = iterator.next();
        Assert.assertEquals("EAR_FILE", type21.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type16 = iterator.next();
        Assert.assertEquals("TAR_FILE", type16.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type17 = iterator.next();
        Assert.assertEquals("BZIP2_FILE", type17.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type18 = iterator.next();
        Assert.assertEquals("GZIP_FILE", type18.toString());

        Assert.assertTrue(iterator.hasNext());
        TargetType type19 = iterator.next();
        Assert.assertEquals("FILE", type19.toString());

        Assert.assertFalse(iterator.hasNext());
    }
}
