package com.github.tuchinoko.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuthorTest{
    private Author author1;
    private Author author2;
    private Author author3;

    @Before
    public void setUp(){
        author1 = new Author("Haruaki Tamada", null);
        author2 = new Author("Example User", "user@example.com");
        author3 = new Author("Taro Tuchinoko");
    }

    @Test
    public void testBasic(){
        Assert.assertEquals("Haruaki Tamada", author1.getName());
        Assert.assertNull(author1.getEmail());
        Assert.assertEquals("Haruaki Tamada", author1.toString());

        Assert.assertEquals("Example User", author2.getName());
        Assert.assertEquals("user@example.com", author2.getEmail());
        Assert.assertEquals("Example User <user@example.com>", author2.toString());

        Assert.assertEquals("Taro Tuchinoko", author3.getName());
        Assert.assertNull(author3.getEmail());
        Assert.assertEquals("Taro Tuchinoko", author3.toString());
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck() throws Exception{
        new Author(null);
    }
}
