package com.usrmngr.client.core.model.Connectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * A generic configuration class that can hold different types of values.
 * The class is able to open files and write files to the desired paths.
 */
public class Configuration {
    private Properties values;

    public Configuration(Configuration config) {
        values = new Properties();
        config.values.forEach(values::put);
    }
    public Configuration() {
        this.values = new Properties();
    }


    public void put(String key, String value) {
        values.put(key, value);
    }

    String get(String key) {
        return values.getProperty(key);
    }

    /**
     * Attempts to load the configuration file at the path specified.
     *
     * @throws IOException
     */
    public void load(String path) throws IOException {
        File file = new File(path);
        values.load(new FileInputStream(file));
    }

    @Override
    public boolean equals(Object obj) {

        // checking if both the object references are
        // referring to the same object.
        if (this == obj)
            return true;

        // it checks if the argument is of the
        // type  by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Conf...)) return false; ---> avoid.
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        // type casting of the argument.
        Configuration candidate = (Configuration) obj;

        // comparing the state of argument with
        // the state of 'this' Object.
        return candidate.values.equals(this.values);
    }

    /**
     * Attempts to save the configuration file at the path specified.
     *
     * @throws IOException
     */
    public void save(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdir();
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

    public int size() {
        return this.values.size();
    }

    public Set<String> getKeys() {
        return values.stringPropertyNames();
    }

    public String getValue(String key) {
        return values.getProperty(key);
    }
}
