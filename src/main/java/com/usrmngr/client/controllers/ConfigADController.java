package com.usrmngr.client.controllers;

import com.usrmngr.client.models.FXNodeContainer;
import com.usrmngr.client.util.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigADController implements Initializable {
    @FXML
    public Pane root;
    @FXML
    public Button testButton;
    @FXML
    public Label message;
    public TextField authDomain;
    public TextField server;
    public TextField baseDN;
    public TextField bindDN;
    public TextField port;
    public TextField bindPW;

    private Properties properties;
    private ArrayList<TextField> textFields;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXNodeContainer allNodes = new FXNodeContainer();
        allNodes.addItem(root);
        textFields = allNodes.getTextFields();

    }
    private void load(){
        properties = DataManager.getProperties();
        textFields.forEach(textField ->
                textField.setText((String)properties.getOrDefault(textField.getId(),"")));

    }
    private   void save(){
        textFields.forEach(textField ->
                properties.setProperty(textField.getId(),textField.getText()));

    }
}
