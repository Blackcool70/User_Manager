package com.usrmngr.client.core.model.Connectors;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ConfigurationTest {

    @Test
    public void put() {
        Configuration configuration = new Configuration();
        configuration.put("hello","world");
        assertEquals(configuration.size(),1);
    }

    @Test
    public void get() {
        Configuration configuration = new Configuration();
        configuration.put("hello","world");
        assertEquals(configuration.get("hello"),"world");
    }

    @Test
    public void load() {
        Configuration loadedConf = new Configuration();
        try {
            String loadPath = "src/test/com/usrmngr/client/core/model/Connectors/Config.test";
            loadedConf.load(loadPath);
            assertEquals(loadedConf.get("hello"),"world");

        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void save() {
        Configuration tosave = new Configuration();
        tosave.put("hello","world");
        try {
            String savePath = System.getProperty("user.dir") + File.separator + "Config.save.test";
            tosave.save(savePath);
            File file = new File(savePath);
            assertTrue(file.exists());
            file.delete();
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void equals1() {
        Configuration configuration = new Configuration();
        Configuration configuration1 = new Configuration();
        assertEquals(configuration, configuration1);
        configuration.put("hello","world");
        assertNotEquals(configuration, configuration1);
        configuration1.put("hello","world");
        assertEquals(configuration1,configuration);
    }
}