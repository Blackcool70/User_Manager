package com.usrmngr.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ConfigADController {
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
}
