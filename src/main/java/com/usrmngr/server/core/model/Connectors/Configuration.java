package com.usrmngr.server.core.model.Connectors;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

// Holds configurations needed for an ldap connection.
class Configuration {
    private Properties values;
    private String savePath;

    Configuration() {
        values = new Properties();
        savePath = "./";
    }

    void put(String key, String value) {
        values.put(key, value);
    }

    String get(String key) {
        return values.getProperty(key);
    }

    private void load(String path) throws IOException {
        // First try loading from the current directory
        File f = new File(path);
        InputStream is = new FileInputStream(f);
        values.load(is);
    }

    private String getDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.now().toString();
    }

    private void loadFromXML(String path) throws IOException {
        // First try loading from the current directory
        File f = new File(path);
        InputStream is = new FileInputStream(f);
        values.loadFromXML(is);

    }
    private void saveToXML(String path) throws IOException {
        File f = new File(path);
        f.mkdir();
        OutputStream out = new FileOutputStream(f);
        values.storeToXML(out, getDateTime());

    }
    private void save(String path) throws IOException {
        File f = new File(path);
        f.mkdir();
        OutputStream out = new FileOutputStream(f);
        values.store(out, getDateTime());
    }

    /**
     * Returns string representation of the config
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return values.toString();
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.put("taco", "test");
        configuration.put("taco1", "test1");
        configuration.put("taco12", "test12");
        System.out.println(configuration);

    }
}
