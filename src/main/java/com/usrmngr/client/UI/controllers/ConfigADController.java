package com.usrmngr.client.UI.controllers;

import com.usrmngr.client.models.ADConnector;
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
    public TextField hostName;
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
        load();
        testButton.setOnMouseClicked(event -> testConnection());

    }
    private void load(){
        properties = DataManager.getProperties();
        textFields.forEach(textField ->
                textField.setText((String)properties.getOrDefault(textField.getId(),"")));

    }
    private   void tempSave(){
        textFields.forEach(textField ->
                properties.setProperty(textField.getId(),textField.getText()));

    }
    private void testConnection(){
        tempSave();
        ADConnector adConnector = new ADConnector(properties);
        message.setText(adConnector.connect() ? "Success." : adConnector.getResultCode());
        adConnector.disconnect();
    }
}
