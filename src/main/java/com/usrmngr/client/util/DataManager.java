package com.usrmngr.client.util;


import com.usrmngr.client.models.FXDialogs.DialogManager;
import com.usrmngr.client.models.FXDialogs.ExceptionDialog;
import javafx.application.Platform;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static com.usrmngr.client.Constants.APP_NAME;
import static com.usrmngr.client.Constants.PROPERTIES_FILE_NAME;

public class DataManager {

    public static  String readFile(String filename){
        StringBuilder sb = new StringBuilder();
        try {
            Files.lines(Paths.get(filename), StandardCharsets.UTF_8).forEach(sb::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  sb.toString();
    }
    /**
     * Returns the programs properties file if they exists.
     *
     * @param path path to the properties file
     * @return loaded portieres if found or empty properties.
     */
    private static Properties getProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(path)));
        } catch (IOException ignored) {
        }
        return properties;
    }
    public static  Properties getProperties(){
        return getProperties(getUserDataDirectory().concat(PROPERTIES_FILE_NAME));
    }
    private static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator + ".".concat(APP_NAME.toLowerCase()) + File.separator;
    }
    public static void saveConfigFile(Properties properties) {
        String dataPath = getUserDataDirectory().concat(PROPERTIES_FILE_NAME);
        File configFile = new File(dataPath);
        try {
            configFile.getParentFile().mkdir();
            configFile.createNewFile();
            DialogManager.showInfo(String.format("Configurations created: \n%s.\n", dataPath));
            properties.store(new FileOutputStream(configFile), "configurations");
        } catch (IOException e) {
            new ExceptionDialog("Unable to save configurations", e).showAndWait();
            Platform.exit();
        }

    }
    public static void main(String[] args) {
        String DATA_PATH = "src/main/resources/samples/MOCK_DATA.json";
        String data = DataManager.readFile(DATA_PATH);
    }
}
