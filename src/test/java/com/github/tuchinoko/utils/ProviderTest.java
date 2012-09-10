package com.github.tuchinoko.utils;

import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProviderTest{
    private Provider provider;

    @Before
    public void setUp() throws Exception{
        provider = new Provider(
            "example",
            new Author[] {
                new Author("Example User1", "user1@example.com"),
                new Author("Example User2", null),
            },
            new Organization("Example Organization", new URL("http://www.example.com/"))
        );
    }

    @Test
    public void testExample() throws Exception{
        Assert.assertEquals("example", provider.getName());

        Author[] authors = provider.getAuthors();
        Assert.assertEquals(2, authors.length);

        Assert.assertEquals("Example User1", authors[0].getName());
        Assert.assertEquals("user1@example.com", authors[0].getEmail());
        Assert.assertEquals("Example User1 <user1@example.com>", authors[0].toString());

        Assert.assertEquals("Example User2", authors[1].getName());
        Assert.assertNull(authors[1].getEmail());
        Assert.assertEquals("Example User2", authors[1].toString());

        Organization org = provider.getOrganization();
        Assert.assertEquals("Example Organization", org.getName());
        Assert.assertEquals(new URL("http://www.example.com/"), org.getUrl());
        Assert.assertEquals("Example Organization (http://www.example.com/)", org.toString());
    }

    @Test
    public void testTuchinokoProvider() throws Exception{
        Provider provider = Provider.TUCHINOKO_PROVIDER;
        Assert.assertEquals("Tuchinoko Provider", provider.getName());

        Author[] authors = provider.getAuthors();
        Assert.assertEquals(1, authors.length);

        Assert.assertEquals("Haruaki Tamada", authors[0].getName());
        Assert.assertEquals("----", authors[0].getEmail());
        Assert.assertEquals("Haruaki Tamada <---->", authors[0].toString());

        Organization org = provider.getOrganization();
        Assert.assertNull(org);
    }

    @Test
    public void testUnknownProvider() throws Exception{
        Provider provider = Provider.UNKNOWN;
        Assert.assertEquals("unknown", provider.getName());

        Author[] authors = provider.getAuthors();
        Assert.assertEquals(0, authors.length);

        Organization org = provider.getOrganization();
        Assert.assertNull(org);
    }

    @Test
    public void testBasic2() throws Exception{
        Provider provider = new Provider("provider", new Organization("My Org"));

        Assert.assertEquals("provider", provider.getName());

        Author[] authors = provider.getAuthors();
        Assert.assertEquals(0, authors.length);

        Organization org = provider.getOrganization();
        Assert.assertEquals("My Org", org.getName());
        Assert.assertNull(org.getUrl());
    }

    @Test(expected=NullPointerException.class)
    public void testNullCheck() throws Exception{
        new Provider(null);
    }
}
