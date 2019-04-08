package com.usrmngr.client.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ConfigController implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private GridPane companySettings;
    @FXML
    private TextField companyName, serverAddress, username;
    @FXML
    private PasswordField password, passwordConfirmation;
    @FXML
    private Button save, cancel;
    @FXML
    private TreeView<String> configOptions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> companySettingsRoot = new TreeItem<>("Company Settings");
        TreeItem<String> serverSettingsRoot = new TreeItem<>("Server Settings");
        configOptions.getRoot().getChildren().add(companySettingsRoot);
        configOptions.getRoot().getChildren().add(serverSettingsRoot);

    }
}

