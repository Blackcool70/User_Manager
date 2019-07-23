package com.usrmngr.client.ui.controllers;

import com.usrmngr.client.core.model.Connectors.LDAPConfig;
import com.usrmngr.util.Alert.AlertMaker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import static com.usrmngr.Main.APP_CONFIG_PATH;

public class ConfigViewController implements Initializable {
    @FXML
    private GridPane propGrid;
    @FXML
    private Button saveButton, cancelButton;
    private LDAPConfig config;
    private TextField[] textFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOnMouseClicked(e -> onSaveClicked());
        cancelButton.setOnMouseClicked(e -> onCancelClicked());
        config = new LDAPConfig();
        try {
            config.load(APP_CONFIG_PATH);
        } catch (IOException ignored) {
        }
        if(config.size() == 0)
            loadDefault();
        displayConfig(config);
    }

    private void loadDefault() {
        config.setServer("localhost");
        config.setPort(389);
        config.setBaseDN("dc=company,dc=com");
    }

    private void onCancelClicked() {
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    /**
     * For each entry int he config file create a label and a text box with the value.
     * @param config
     */
    private void displayConfig(LDAPConfig config) {
        if (config == null) config = new LDAPConfig();
        int i = 0;
        propGrid.addRow(i);
        propGrid.addColumn(i);
        Set<String> keys =  config.getKeys();
        textFields = new TextField[keys.size()];
        for (String key : keys) {
            textFields[i] = new TextField(config.getValue(key));
            textFields[i].setId(key);
            propGrid.add(new Label(key), 0, i);
            propGrid.add(textFields[i],1,i);
            ++i;
        }
    }
    private void onSaveClicked() {
        for (TextField textField : textFields) {
            config.put(textField.getId(), textField.getText());
        }
        try {
            config.save(APP_CONFIG_PATH);
            AlertMaker.showSimpleAlert("Configuration","Saved");

        } catch (IOException e) {
            AlertMaker.showSimpleAlert("Configuration","Failed to save config, try again.");
        }
    }
    private static String getPropertiesPath() {
        return APP_CONFIG_PATH;
    }
}
