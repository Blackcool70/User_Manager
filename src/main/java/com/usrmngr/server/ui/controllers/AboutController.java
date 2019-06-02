package com.usrmngr.server.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    public static void main(String[] args) {

    }
    @FXML
    private TextArea aboutText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aboutText.setText(getAboutText());


    }

    private String getAboutText() {
        return
                "User Manager Server v.0.0.1\n" +
                        "Copyright (c) 2019 Jecsan Blanco\n";
    }
}
