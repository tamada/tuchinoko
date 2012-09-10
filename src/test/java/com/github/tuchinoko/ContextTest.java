package com.github.tuchinoko;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContextTest{
    private Context context;

    @Before
    public void setUp() throws Exception{
        context = new Context(new Environment());
        context.setDestination("hoge");
        context.addProcessorName("nop");
        context.addTarget("target/test-classes/resources/test.jar");
        context.putArgument("nop.key1", "value1");
        context.putArgument("nop-001.key2", "value2");
        context.putArgument("001.key3", "value3");
    }

    @Test
    public void testBasic() throws Exception{
        Assert.assertEquals("hoge", context.getDestination());

        String[] targets = context.getTargets();
        Assert.assertEquals(1, targets.length);
        Assert.assertEquals("target/test-classes/resources/test.jar", targets[0]);

        Assert.assertTrue(context.hasArgument("nop.key1"));
        Assert.assertTrue(context.hasArgument("nop-001.key2"));
        Assert.assertTrue(context.hasArgument("001.key3"));
        Assert.assertFalse(context.hasArgument("nop-001.key3"));

        Assert.assertEquals("value1", context.getArgumentValue("nop.key1"));
        Assert.assertEquals("value2", context.getArgumentValue("nop-001.key2"));
        Assert.assertEquals("value3", context.getArgumentValue("001.key3"));

        Assert.assertNull(context.getArgumentValue("002.key4"));

        context.setDestination(null);
        Assert.assertEquals(".", context.getDestination());
    }

    @Test
    public void testSelfConstructor() throws Exception{
        Context context = new Context(this.context);
        Assert.assertEquals("hoge", context.getDestination());

        String[] targets = context.getTargets();
        Assert.assertEquals(1, targets.length);
        Assert.assertEquals("target/test-classes/resources/test.jar", targets[0]);

        Assert.assertTrue(context.hasArgument("nop.key1"));
        Assert.assertTrue(context.hasArgument("nop-001.key2"));
        Assert.assertTrue(context.hasArgument("001.key3"));
        Assert.assertFalse(context.hasArgument("nop-001.key3"));

        Assert.assertEquals("value1", context.getArgumentValue("nop.key1"));
        Assert.assertEquals("value2", context.getArgumentValue("nop-001.key2"));
        Assert.assertEquals("value3", context.getArgumentValue("001.key3"));

        Assert.assertNull(context.getArgumentValue("002.key4"));

        context.setDestination(null);
        Assert.assertEquals(".", context.getDestination());
    }

    @Test(expected=NullPointerException.class)
    public void testNullKeyInGetArgumentValue(){
        context.getArgumentValue(null);
    }

    @Test(expected=NullPointerException.class)
    public void testNullKeyInHasArgument(){
        context.hasArgument(null);
    }

    @Test(expected=NullPointerException.class)
    public void testNullArgumentKey(){
        context.putArgument(null, "this method will throw NullPointerException");
    }

    @Test
    public void testProcessor() throws Exception{
        Assert.assertEquals(1, context.getProcessorCount());

        Processor[] processors = context.getProcessors();

        Assert.assertEquals(1, processors.length);
        Assert.assertEquals("nop-001", processors[0].getId());
        Assert.assertEquals("nop", processors[0].getProcessorName());
        Arguments args = processors[0].getArguments();

        Assert.assertEquals(3, args.getArgumentCount());
        Assert.assertEquals("value1", args.getValue("key1"));
        Assert.assertEquals("value2", args.getValue("key2"));
        Assert.assertEquals("value3", args.getValue("key3"));
    }

    @Test
    public void testMultipleProcessor() throws Exception{
        context.addProcessorName("nop");
        context.putArgument("002.key4", "value4");

        Assert.assertEquals(2, context.getProcessorCount());

        Processor[] processors = context.getProcessors();
        Assert.assertEquals(2, processors.length);
        Assert.assertEquals("nop-001", processors[0].getId());
        Assert.assertEquals("nop", processors[0].getProcessorName());
        Assert.assertEquals("nop-002", processors[1].getId());
        Assert.assertEquals("nop", processors[1].getProcessorName());
        Arguments args1 = processors[0].getArguments();

        Assert.assertEquals(3, args1.getArgumentCount());
        Assert.assertEquals("value1", args1.getValue("key1"));
        Assert.assertEquals("value2", args1.getValue("key2"));
        Assert.assertEquals("value3", args1.getValue("key3"));

        Arguments args2 = processors[1].getArguments();
        Assert.assertEquals(2, args2.getArgumentCount());
        Assert.assertEquals("value1", args2.getValue("key1"));
        Assert.assertEquals("value4", args2.getValue("key4"));
    }

    @Test
    public void testNotConflictArguments() throws Exception{
        context.putArgument("nop-001.key1", "value1");
        context.getProcessors();
    }

    @Test(expected=ArgumentsConflictException.class)
    public void testConflictArguments() throws Exception{
        context.putArgument("nop-001.key1", "value1-conflict");
        context.getProcessors();
    }

    @Test(expected=ArgumentsConflictException.class)
    public void testConflictArguments2() throws Exception{
        context.putArgument("nop-001.key4", null);
        context.putArgument("001.key4", "value4");
        context.getProcessors();
    }

    @Test(expected=UnknownProcessorException.class)
    public void testUnknownProcessor() throws Exception{
        context.addProcessorName("unknown-processor");
        context.getProcessors();
    }
}
