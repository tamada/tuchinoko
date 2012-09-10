package com.github.tuchinoko.utils;

import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrganizationTest{
    private Organization org1;
    private Organization org2;
    private Organization org3;

    @Before
    public void setUp() throws Exception{
        org1 = new Organization("My Organization", null);
        org2 = new Organization("Example Organization", new URL("http://www.example.com/"));
        org3 = new Organization("New Org");
    }

    @Test
    public void testBasic() throws Exception{
        Assert.assertEquals("My Organization", org1.getName());
        Assert.assertNull(org1.getUrl());
        Assert.assertEquals("My Organization", org1.toString());

        Assert.assertEquals("Example Organization", org2.getName());
        Assert.assertEquals(new URL("http://www.example.com/"), org2.getUrl());
        Assert.assertEquals("Example Organization (http://www.example.com/)", org2.toString());

        Assert.assertEquals("New Org", org3.getName());
        Assert.assertNull(org3.getUrl());
        Assert.assertEquals("New Org", org3.toString());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testNullCheck() throws Exception{
        new Organization(null);
    }
}
