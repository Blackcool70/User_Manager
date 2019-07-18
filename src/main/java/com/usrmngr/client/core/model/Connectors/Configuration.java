package com.usrmngr.client.core.model.Connectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * A generic configuration class that can hold different types of values.
 * The class is able to open files and write files to the desired paths.
 */
public class Configuration {
    private Properties values;
    public Configuration() {
        values = new Properties();
    }

    /**
     * Deep Copy constructor
     * @param conf another configuration
     */
    Configuration(Configuration conf) {
        this.values = new Properties(conf.values);
    }

    /**
     * Deep clone
     * @param configuration
     * @return deep clone of provided config
     */
    public Configuration clone(Configuration configuration) {
        return new Configuration(configuration);
    }
    protected void put(String key, String value) {
        values.put(key, value);
    }

     String get(String key) {
        return values.getProperty(key);
    }

    /**
     * Attempts to load the configuration file at the path specified.
     * @throws IOException
     */
    public void load(File file) throws IOException {
        values.load(new FileInputStream(file));
    }

    /**
     *  Attempts to save the configuration file at the path specified.
     * @throws IOException
     */
    public void save(File file) throws IOException {
        File parent = file.getParentFile();
        if(parent != null) // create directory structure as needed
            parent.mkdir();
        file.createNewFile();
        values.store(new FileOutputStream(file), "config");
    }


    /**
     * Returns string representation of the config
     *
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        values.forEach((key, value) -> sb.append(key).append("=").append(value).append("\n"));
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

    public void clear() {
        values.clear();
    }
    public static void main(String[] args) {
    }
}
