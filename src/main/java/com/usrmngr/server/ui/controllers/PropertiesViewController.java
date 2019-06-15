package com.usrmngr.server.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesViewController implements Initializable {
    @FXML
    private GridPane propertyGrid;
    @FXML
    private Button saveButton,cancelButton;

    private Properties properties;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOnMouseClicked( e -> onSaveClicked());
        cancelButton.setOnMouseClicked( e -> onCancelClicked());

    }
    private void onCancelClicked(){
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    private void onSaveClicked(){
        System.out.println("save clicked");
    }
}
