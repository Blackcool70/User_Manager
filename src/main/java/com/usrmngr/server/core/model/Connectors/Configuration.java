package com.usrmngr.server.core.model.Connectors;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static com.usrmngr.server.ServerMain.APP_NAME;

class Configuration {
    private Properties values;

    Configuration() {
        values = new Properties();
    }

    void put(String key, String value) {
        values.put(key, value);
    }

    String get(String key) {
        return values.getProperty(key);
    }

    private void load(String fileName) throws IOException {
        File f = new File(getUserDataDirectory().concat(fileName));
        if(f.exists()) {
            InputStream is = new FileInputStream(f);
            values.load(is);
        }

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

    private void saveToXML(String fileName) throws IOException {
        String dataPath = getUserDataDirectory().concat(fileName);
        File configFile = new File(dataPath);
        configFile.getParentFile().mkdir();
        configFile.createNewFile();
        values.storeToXML(new FileOutputStream(configFile), getDateTime());;

    }

    private static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator + ".".concat(APP_NAME.toLowerCase()) + File.separator;
    }

    public  void save(String fileName) throws IOException {
        String dataPath = getUserDataDirectory().concat(fileName);
        File configFile = new File(dataPath);
        configFile.getParentFile().mkdir();
        configFile.createNewFile();
        values.store(new FileOutputStream(configFile),"");

    }

        /**
         * Returns string representation of the config
         *
         * @return string representation
         */
        @Override
        public String toString () {
            return values.toString();
        }

        public static void main (String[]args){
            Configuration configuration = new Configuration();
            configuration.put("taco", "test");
            configuration.put("taco1", "test1");
            configuration.put("taco12", "test12");
            try {
                configuration.saveToXML("test.config.");
                configuration.clear();
                configuration.load("test.config");
                System.out.println(configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(configuration);

        }

    private void clear() {
            values.clear();
    }
}
