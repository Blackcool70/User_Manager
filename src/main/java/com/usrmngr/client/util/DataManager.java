package com.usrmngr.client.util;


import com.usrmngr.client.controllers.ConfigController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import static com.usrmngr.client.Constants.PROPERTIES_FILE_NAME;

public class DataManager {

    public static String readFile(String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(filename);

            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();

            while (scanner.hasNextLine()) {
                sb.append(line);
                line = scanner.nextLine();
            }
            sb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    /**
     * Returns the programs properties file if they exists.
     *
     * @param path path to the properties file
     * @return loaded portieres if found or empty properties.
     */
    private static Properties getProperties(String path) {
        Properties properties = new Properties();
        File file = new File(path);
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException ignored) {
        }
        return properties;
    }
    public static  Properties getProperties(){
        return getProperties(ConfigController.getUserDataDirectory().concat(PROPERTIES_FILE_NAME));
    }
    public static void main(String[] args) {
        String DATA_PATH = "src/main/resources/samples/MOCK_DATA.json";
        String data = DataManager.readFile(DATA_PATH);
    }
}
