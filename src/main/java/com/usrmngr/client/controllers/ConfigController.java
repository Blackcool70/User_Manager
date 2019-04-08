package com.usrmngr.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

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
    private TreeView<String> configOptionsTree;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> configOptionsRoot = new TreeItem<>("Configurations");
        configOptionsRoot.getChildren().add(new TreeItem<>("Company"));
        configOptionsRoot.getChildren().add(new TreeItem<>("Server"));
        configOptionsRoot.setExpanded(true);
        configOptionsTree.setRoot(configOptionsRoot);
        configOptionsTree.setEditable(false);


    }

}

