package com.usrmngr.server.ui.controllers;

import com.usrmngr.server.ServerMain;
import com.usrmngr.util.Alert.AlertMaker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;

public class PropertiesViewController implements Initializable {
    @FXML
    private GridPane propGrid;
    @FXML
    private Button saveButton, cancelButton;

    private  Properties properties;
    private static final String PROPERTIES_FILE = "config.properties";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOnMouseClicked(e -> onSaveClicked());
        cancelButton.setOnMouseClicked(e -> onCancelClicked());
        properties = loadProperties();
        if(!hasValidProperties()){
            createProperties();
        }
        displayProperties(properties);

    }

    private void onCancelClicked() {
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    private boolean hasValidProperties() {
        return !(properties == null || properties.isEmpty());

    }

    private void displayProperties(Properties prop) {
        if (!hasValidProperties()) return;
        int i = 0;
        propGrid.addRow(i);
        propGrid.addColumn(i);
        Enumeration e = prop.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            propGrid.add(new Label(key),0,i);
            propGrid.add(new TextField(prop.getProperty(key)),1,i);
            ++i;
        }
    }


    private void createProperties() {
        properties = new Properties();
        properties.put("serverID", UUID.randomUUID().toString());
        properties.put("propertiesPath", getPropertiesPath());
        properties.put("serverName", "User Manager");
        properties.put("port", "50511");

        AlertMaker.showSimpleAlert("Properties", "Default Properties created:\n".concat(getPropertiesPath()));
    }

    private void onSaveClicked() {
        saveProperties(properties);
    }

    private  void saveProperties(Properties properties) {
        saveProperties(properties, getPropertiesPath());
    }

    private static void saveProperties(Properties properties, String path) {
        File propFile = new File(path);
        try {
            propFile.getParentFile().mkdir();
            propFile.createNewFile();
            // save properties to project root folder
            properties.store(new FileOutputStream(propFile), null);
        } catch (IOException io) {
            AlertMaker.showSimpleAlert("Properties", "Failed to save properties.");
            io.printStackTrace();
        }
    }

    public static Properties loadProperties() {
        return loadProperties(getPropertiesPath());
    }

    public static Properties loadProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(path)));
        } catch (IOException ignored) {
            AlertMaker.showSimpleAlert("Properties", "Failed to load properties.");
        }
        return properties;
    }

    private static String getPropertiesPath() {
        return System.getProperty("user.home") + File.separator +
                ".".concat(ServerMain.APP_NAME.toLowerCase().replaceAll(" ", "_")) +
                File.separator +
                PROPERTIES_FILE;
    }
}
