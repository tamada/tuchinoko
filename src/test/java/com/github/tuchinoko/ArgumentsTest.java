package com.github.tuchinoko;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArgumentsTest{
    private Arguments args;

    @Before
    public void setUp() throws Exception{
        args = new Arguments();
        args.putValue(new ArgumentImpl("arg1", "value1", "description1"));
        args.putValue("arg2", "value2", "description2");
        args.putValue("arg3", "value3");
        args.setDescription("arg3", "description3");
        args.putValue("nullValue", null);
    }

    @Test
    public void testBasic() throws Exception{
        Assert.assertEquals(4, args.getArgumentCount());

        Assert.assertTrue(args.hasArgument("arg1"));
        Assert.assertEquals("value1", args.getValue("arg1"));
        Assert.assertEquals("description1", args.getDescription("arg1"));

        Assert.assertTrue(args.hasArgument("arg2"));
        Assert.assertEquals("value2", args.getValue("arg2"));
        Assert.assertEquals("description2", args.getDescription("arg2"));

        Assert.assertTrue(args.hasArgument("arg3"));
        Assert.assertEquals("value3", args.getValue("arg3"));
        Assert.assertEquals("description3", args.getDescription("arg3"));

        Assert.assertTrue(args.hasArgument("nullValue"));
        Assert.assertNull(args.getValue("nullValue"));
        Assert.assertEquals("value4", args.getValue("nullValue", "value4"));
        Assert.assertNull(args.getDescription("nullValue"));
    }

    @Test
    public void testUpdateValue() throws Exception{
        args.putValue("arg1", "updateValue1");
        
        Assert.assertEquals(4, args.getArgumentCount());
        Assert.assertTrue(args.hasArgument("arg1"));
        Assert.assertEquals("updateValue1", args.getValue("arg1"));
        Assert.assertEquals("description1", args.getDescription("arg1"));

        args.putValue("arg2", "updateValue2", "updateDescription2");
        Assert.assertEquals(4, args.getArgumentCount());
        Assert.assertTrue(args.hasArgument("arg2"));
        Assert.assertEquals("updateValue2", args.getValue("arg2"));
        Assert.assertEquals("updateDescription2", args.getDescription("arg2"));

        args.putValue("arg4", "value4", "description4");
        Assert.assertEquals(5, args.getArgumentCount());
        Assert.assertTrue(args.hasArgument("arg4"));
        Assert.assertEquals("value4", args.getValue("arg4"));
        Assert.assertEquals("description4", args.getDescription("arg4"));

        args.removeArgument("arg4");
        Assert.assertEquals(4, args.getArgumentCount());
        Assert.assertFalse(args.hasArgument("arg4"));
        Assert.assertNull(args.getValue("arg4"));
        Assert.assertNull(args.getDescription("arg4"));
    }

    @Test
    public void testSelfConstructor() throws Exception{
        Arguments arg = new Arguments(args);
        
        Assert.assertEquals(4, arg.getArgumentCount());

        Assert.assertTrue(arg.hasArgument("arg1"));
        Assert.assertEquals("value1", arg.getValue("arg1"));
        Assert.assertEquals("description1", arg.getDescription("arg1"));

        Assert.assertTrue(arg.hasArgument("arg2"));
        Assert.assertEquals("value2", arg.getValue("arg2"));
        Assert.assertEquals("description2", arg.getDescription("arg2"));

        Assert.assertTrue(arg.hasArgument("arg3"));
        Assert.assertEquals("value3", arg.getValue("arg3"));
        Assert.assertEquals("description3", arg.getDescription("arg3"));

        Assert.assertTrue(arg.hasArgument("nullValue"));
        Assert.assertNull(arg.getValue("nullValue"));
        Assert.assertEquals("value4", arg.getValue("nullValue", "value4"));
        Assert.assertNull(arg.getDescription("nullValue"));
    }

    @Test
    public void testMerge() throws Exception{
        Arguments args2 = new Arguments();
        args2.putValue("arg2", "newValue2", "newDescription2");
        args2.putValue("arg3", "newValue3", "newDescription3");
        args2.putValue("arg4", "newValue4", "newDescription4");

        args.merge(args2);
        
        Assert.assertEquals(5, args.getArgumentCount());
        Assert.assertTrue(args.hasArgument("arg1"));
        Assert.assertEquals("value1", args.getValue("arg1"));
        Assert.assertEquals("description1", args.getDescription("arg1"));

        Assert.assertTrue(args.hasArgument("arg2"));
        Assert.assertEquals("value2", args.getValue("arg2"));
        Assert.assertEquals("description2", args.getDescription("arg2"));

        Assert.assertTrue(args.hasArgument("arg3"));
        Assert.assertEquals("value3", args.getValue("arg3"));
        Assert.assertEquals("description3", args.getDescription("arg3"));

        Assert.assertTrue(args.hasArgument("arg4"));
        Assert.assertEquals("newValue4", args.getValue("arg4"));
        Assert.assertEquals("newDescription4", args.getDescription("arg4"));
    }

    @Test
    public void testMergeOverwrite() throws Exception{
        Arguments args2 = new Arguments();
        args2.putValue("arg2", "newValue2", "newDescription2");
        args2.putValue("arg3", "newValue3", "newDescription3");
        args2.putValue("arg4", "newValue4", "newDescription4");

        args.mergeOverwrite(args2);
        
        Assert.assertEquals(5, args.getArgumentCount());
        Assert.assertTrue(args.hasArgument("arg1"));
        Assert.assertEquals("value1", args.getValue("arg1"));
        Assert.assertEquals("description1", args.getDescription("arg1"));

        Assert.assertTrue(args.hasArgument("arg2"));
        Assert.assertEquals("newValue2", args.getValue("arg2"));
        Assert.assertEquals("newDescription2", args.getDescription("arg2"));

        Assert.assertTrue(args.hasArgument("arg3"));
        Assert.assertEquals("newValue3", args.getValue("arg3"));
        Assert.assertEquals("newDescription3", args.getDescription("arg3"));

        Assert.assertTrue(args.hasArgument("arg4"));
        Assert.assertEquals("newValue4", args.getValue("arg4"));
        Assert.assertEquals("newDescription4", args.getDescription("arg4"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullNameInHasArguments() throws Exception{
        args.hasArgument(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullNameInGetValue() throws Exception{
        args.getValue(null);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testNullNameInGetDescription() throws Exception{
        args.getDescription(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullArgumentInSetDescription() throws Exception{
        args.setDescription("emptyKey", "description");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullNameInSetDescription() throws Exception{
        args.setDescription(null, "description");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullNameInPutValue() throws Exception{
        args.putValue(null, "valuenull");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullNameInPutValue2() throws Exception{
        args.putValue(null, "valuenull", "description");
    }
}
