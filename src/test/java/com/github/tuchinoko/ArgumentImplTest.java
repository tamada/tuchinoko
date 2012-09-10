package com.github.tuchinoko;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArgumentImplTest{
    private ArgumentImpl arg;

    @Before
    public void setUp(){
        arg = new ArgumentImpl("name", "value", "description");
    }

    @Test
    public void testBasic(){
        Assert.assertEquals("name", arg.getName());
        Assert.assertEquals("value", arg.getValue());
        Assert.assertEquals("description", arg.getDescription());

        arg.setDescription("newDesc");
        Assert.assertEquals("name", arg.getName());
        Assert.assertEquals("value", arg.getValue());
        Assert.assertEquals("newDesc", arg.getDescription());

        arg.setValue("newValue");
        Assert.assertEquals("name", arg.getName());
        Assert.assertEquals("newValue", arg.getValue());
        Assert.assertEquals("newDesc", arg.getDescription());
    }

    @Test
    public void testSelfConstructor(){
        ArgumentImpl arg2 = new ArgumentImpl(arg);
        Assert.assertEquals("name", arg2.getName());
        Assert.assertEquals("value", arg2.getValue());
        Assert.assertEquals("description", arg2.getDescription());

        arg2.setDescription("newDesc");
        Assert.assertEquals("name", arg.getName());
        Assert.assertEquals("value", arg.getValue());
        Assert.assertEquals("description", arg.getDescription());
        Assert.assertEquals("name", arg2.getName());
        Assert.assertEquals("value", arg2.getValue());
        Assert.assertEquals("newDesc", arg2.getDescription());

        arg.setValue("newValue");
        Assert.assertEquals("name", arg.getName());
        Assert.assertEquals("newValue", arg.getValue());
        Assert.assertEquals("description", arg.getDescription());
        Assert.assertEquals("name", arg2.getName());
        Assert.assertEquals("value", arg2.getValue());
        Assert.assertEquals("newDesc", arg2.getDescription());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullNameInConstructor() throws Exception{
        arg = new ArgumentImpl((String)null);
    }

    @Test
    public void testToString(){
        Assert.assertEquals("name=value(description)", arg.toString());

        ArgumentImpl arg2 = new ArgumentImpl("arg2");
        Assert.assertNull(arg2.getValue());
        Assert.assertEquals("arg2=<null>", arg2.toString());
    }
}
