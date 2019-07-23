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
public class Configuration extends  Properties{
    public Configuration() {
        super();
    }
     void  put(String key,String value){
        super.put(key,value);
     }
     String get(String key) {
        return super.getProperty(key);
    }

    /**
     * Attempts to load the configuration file at the path specified.
     * @throws IOException
     */
    public void load(String  path) throws IOException {
        File file = new File(path);
        this.load(new FileInputStream(file));
    }

    /**
     *  Attempts to save the configuration file at the path specified.
     * @throws IOException
     */
    public void save(String path) throws IOException {
        File file = new File(path);
        File parent = file.getParentFile();
        if(parent != null && !parent.exists()) parent.mkdir();
        super.store(new FileOutputStream(file), "config");
    }
    /**
     * Returns string representation of the config
     *
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.forEach((key, value) -> sb.append(key).append("=").append(value).append("\n"));
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();
        configuration.put("Test","test");
        configuration.save("c:\\\\temp\\config.txt");
    }

}
