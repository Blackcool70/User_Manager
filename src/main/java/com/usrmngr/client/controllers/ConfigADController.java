package com.usrmngr.client.controllers;

import com.usrmngr.client.Main;
import com.usrmngr.client.models.ADConnector;
import com.usrmngr.client.models.FXNodeContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigADController implements Initializable {
    @FXML
    public Pane root;
    @FXML
    public TextField authDomain;
    @FXML
    public TextField ldapPath;
    @FXML
    public TextField hostName;
    @FXML
    public TextField userName;
    @FXML
    public TextField port;
    @FXML
    public Button testButton;
    @FXML
    public Label message;

    private Properties properties;
    private FXNodeContainer fxNodeContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fxNodeContainer = new FXNodeContainer();
        fxNodeContainer.addItem(root);
        properties = Main.getProperties();
        testButton.setOnMouseClicked(
                event -> testButtonClicked()
        );

    }

    private void testButtonClicked() {

        ADConnector adConnector = new ADConnector();
        if(!properties.isEmpty()){
            String server = properties.getProperty("hostName");
            int port = Integer.parseInt(properties.getProperty("port"));
            String baseDN = properties.getProperty("ldapPath");
            String un = properties.getProperty("userName");
            String bindDN = un.concat(",").concat(baseDN);
            String password = properties.getProperty("password");
            adConnector.setConfigs(server, port, baseDN, bindDN, password);
            adConnector.connect();
        }

        String msg;
        if(adConnector.isConnected()){
            msg = "Connection Successful";
        }else{
            msg = "Connection Failed: ".concat(adConnector.getResultCode());
        }
        message.setText(msg.concat(adConnector.getErrorMessageFromServer()));
        adConnector.disconnect();
    }

}
