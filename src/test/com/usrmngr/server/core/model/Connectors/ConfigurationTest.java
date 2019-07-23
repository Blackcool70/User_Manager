package com.usrmngr.server.core.model.Connectors;

import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;

public class ConfigurationTest {

    @Test
    public void getUserDataDirectory() {
        //windows
        Assert.assertEquals(Configuration.getUserDataDirectory(),"C:\\Users\\jecsa\\.user_manager_server\\");
    }

    @Test
    public void clone1() {
        Configuration conf = new Configuration();
        conf.put("hello","word");
        conf.put("key","value");

        Configuration conf1 =  conf.clone(conf);
        // not the same address
        Assert.assertNotEquals(conf1,conf);
        // has same values
        Assert.assertEquals(conf1.get("hello"),conf.get("hello"));
        Assert.assertEquals(conf1.get("key"),conf.get("key"));
        // one does not change the other
        conf.clear();
        Assert.assertNotEquals(conf,conf1);
    }

    @Test
    public void load() throws IOException {
        Configuration confa = new Configuration();
        confa.put("hello","world");
        confa.put("this","test");
        confa.put("will","pass");

        Configuration confb = new Configuration();
        confb.load("C:\\Users\\jecsa\\IdeaProjects\\User_Manager\\src\\test\\com\\usrmngr\\server\\core\\model\\Connectors\\test.config");

        Assert.assertEquals(confa.get("hello"),confb.get("hello"));
        Assert.assertEquals(confa.get("this"),confb.get("this"));
        Assert.assertEquals(confa.get("will"),confb.get("will"));
    }

    @Test
    public void save() throws IOException {
        Configuration confa = new Configuration();
        confa.put("hi","how");
        confa.put("are","you");
        confa.put("this","day");
        confa.save("test.save.config");
        File file =  new File("test.save.config");
        Assert.assertTrue(file.exists());
        Configuration confb = new Configuration();
        confb.load("test.save.config");
        file.delete(); // not sure why this wont delete

    }

    @Test
    public void toString1() {
        Configuration conf = new Configuration();
        conf.put("hello","world");
        conf.put("Hi","jack");
        Assert.assertEquals(conf.toString(),"Hi=jack\nhello=world");
    }

}