package com.usrmngr.server.UI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerMainController implements Initializable {

    @FXML
    private BorderPane ServerMainWindow;

    @FXML
    private Text statusLabel;

    @FXML
    private Button startServer;

    @FXML
    private Button stopServer;

    @FXML
    private TextArea logArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

