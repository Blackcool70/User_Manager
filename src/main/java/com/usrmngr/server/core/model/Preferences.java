package com.usrmngr.server.core.model;

import com.usrmngr.Main;
import com.usrmngr.server.util.Alert.AlertMaker;
import javafx.application.Platform;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import static com.usrmngr.server.core.model.Constants.*;

public class Preferences {
    private Properties properties;

    public Preferences() {
        properties = new Properties();
    }

    public static void initConfig() {
        Preferences preferences = new Preferences();
        preferences.properties.put("port", DEFAULT_LISTEN_PORT);
        preferences.properties.put("server", DEFAULT_SERVER_NAME);
        preferences.properties.put("timeout", DEFAULT_SESSION_TIMEOUT);
        writePreferenceToFile(preferences, DEFAULT_APP_CONFIG_PATH, DEFAULT_CONFIG_FILE_NAME);
    }

    public static Preferences getPreferences(String path) {
        Preferences preferences = null;
        try {
            preferences = new Preferences();
            preferences.properties.load(new FileInputStream(new File(path)));
        } catch (IOException e) {
            Main.LOGGER.log(Level.ERROR, "Config file is missing. Creating new one with default config");
            initConfig();
        }
        Main.LOGGER.log(Level.INFO, "Config file is loaded.");
        return preferences;
    }

    public static void writePreferenceToFile(Preferences preferences, String path, String fileName) {
        File configFile = new File(path.concat(fileName));
        try {
            configFile.getParentFile().mkdir();
            configFile.createNewFile();
            preferences.properties.store(new FileOutputStream(configFile), "configs");
            AlertMaker.showSimpleAlert("Configurations", "Configs saved to ".concat(path.concat(fileName)));
            Logger.getLogger(Preferences.class.getName()).info("Config file saved: ".concat(path.concat(fileName)));
        } catch (IOException e) {
            AlertMaker.showErrorMessage(e, "Configurations", "Unable to save configurations");
            Main.LOGGER.log(Level.FATAL, "Failed to write config file.");
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        Preferences preferences = Preferences.getPreferences(DEFAULT_APP_CONFIG_PATH);
    }
}

