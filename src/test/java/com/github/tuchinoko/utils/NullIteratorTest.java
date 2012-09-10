package com.github.tuchinoko.utils;

import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NullIteratorTest{
    private NullIterator<Object> iterator;

    @Before
    public void setUp(){
        iterator = new NullIterator<Object>();
    }

    @Test
    public void testBasic(){
        Assert.assertFalse(iterator.hasNext());
        
        iterator.remove();
    }

    @Test(expected=NoSuchElementException.class)
    public void testNoSuchElementException() throws Exception{
        iterator.next();
    }
}
