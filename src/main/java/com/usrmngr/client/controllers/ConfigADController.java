package com.usrmngr.client.controllers;

import com.usrmngr.client.models.ADConnector;
import com.usrmngr.client.models.FXDialogs.CredentialsDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.zip.Adler32;

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

    private Properties properties;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testButton.setOnMouseClicked(
                event -> {
                    testButtonClicked();
                }
        );

    }

    private void testButtonClicked() {
        String server = properties.getProperty("serverName");
        int port = Integer.parseInt(properties.getProperty("port"));
        String baseDN = properties.getProperty("ldapPath");
        String un = properties.getProperty("userName");
        String bindDN = un.concat(",").concat(baseDN);
        CredentialsDialog credentialsDialog = new CredentialsDialog(un, "");
        Optional<Pair<String, String>> result = credentialsDialog.showAndWait();
        Pair<String, String> creds;
        if (result.isPresent()) {
            creds = result.get();
            adConnector = new ADConnector(server, port, baseDN, bindDN,creds.getValue());
            adConnector.connect();
        }
    }

    public void setConfiguraitons(Properties properties) {
        this.properties = properties;
    }
}
