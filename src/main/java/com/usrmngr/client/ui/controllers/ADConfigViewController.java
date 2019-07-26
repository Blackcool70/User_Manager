package com.usrmngr.client.ui.controllers;

import com.usrmngr.Main;
import com.usrmngr.client.core.model.Connectors.Configuration;
import com.usrmngr.client.core.model.Connectors.LDAPConnector;
import com.usrmngr.client.core.model.FXNodeContainer;
import com.usrmngr.util.Alert.AlertMaker;
import com.usrmngr.util.Dialog.DialogMaker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.usrmngr.Main.APP_CONFIG_PATH;

public class ADConfigViewController implements Initializable {
    @FXML
    private GridPane connectionProperties;
    @FXML
    private Button saveButton, cancelButton, debuggingFill;
    @FXML
    private PasswordField password;

    private Configuration config;
    private ArrayList<TextField> textFields;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOnMouseClicked(e -> onSaveClicked());
        cancelButton.setOnMouseClicked(e -> onCancelClicked());
        debuggingFill.setOnMouseClicked(e -> loadDebugConfig());
        config = new Configuration();

        FXNodeContainer allNodes = new FXNodeContainer();
        allNodes.addItem(connectionProperties);
        textFields = allNodes.getTextFields();
        textFields.add(password);

        try {
            config.load(APP_CONFIG_PATH);
        } catch (IOException ignored) {
        }
        loadConfig(config);
    }

    private void loadDebugConfig() {
        config = new Configuration();
        try {
            config.load(Main.APP_DEBUG_CONFIG_PATH);
            loadConfig(config);
        } catch (IOException e) {
            //log
            System.err.println(e.getMessage());
        }
    }


    private void closeWindow() {
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void onCancelClicked() {
        if (DialogMaker.showConfirmationDialog("Invalid configuration will terminate the program.")) {
            if (hasValidConfigs(config)) {
                closeWindow();
            } else {
                Platform.exit();
                System.exit(1);
            }
        }
    }

    private boolean hasValidConfigs(Configuration config) {
        boolean isValid = (config != null);
        if(isValid) {
            LDAPConnector connector = new LDAPConnector(config);
            try {
                connector.connect();
                connector.authenticate();
                isValid =  connector.isConnected() && connector.isAuthenticated();
            } catch (Exception ignored) {
                //log
            }
        }
        return  isValid;
    }


    /**
     * Try to match and load the needed configs from the config file.
     *
     * @param config
     */
    private void loadConfig(Configuration config) {
        for (TextField textField : textFields) {
            textField.setText(config.getValue(textField.getId()));
        }
    }

    private boolean allRequiredFieldsAreFilled() {
        boolean isValid = true;
        for (TextField textField : textFields) {
            String input = textField.getText();
            if (input == null || input.equals("")) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    private Configuration getTextFieldValues() {
        Configuration newConfig = new Configuration();
        for (TextField textField : textFields) {
            newConfig.put(textField.getId(), textField.getText());
        }
        return newConfig;
    }

    private void onSaveClicked() {
        if (allRequiredFieldsAreFilled()) {
            try {
                config = getTextFieldValues();
                if (hasValidConfigs(config)) {
                    config.save(APP_CONFIG_PATH);
                    AlertMaker.showSimpleAlert("Configurations", "Saved");
                    closeWindow();
                } else {
                    AlertMaker.showSimpleAlert("Invalid Config", "Configurations are invalid.");
                }
            } catch (IOException e) {
                AlertMaker.showSimpleAlert("Configuration", "Failed to save config, try again.");
            }
        } else {
            AlertMaker.showSimpleAlert("Invalid Config", "All fields are required.");
        }
    }


    private static String getPropertiesPath() {
        return APP_CONFIG_PATH;
    }
}
