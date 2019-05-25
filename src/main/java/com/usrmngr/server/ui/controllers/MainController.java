package com.usrmngr.server.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.apache.logging.log4j.Level;

import java.net.URL;
import java.util.ResourceBundle;

import static com.usrmngr.server.Main.LOGGER;

public class MainController implements Initializable {

    @FXML
    private BorderPane ServerMainWindow;

    @FXML
    private Text statusLabel;

    @FXML
    private Button startServer;

    @FXML
    private Button stopServer;

    @FXML
    private  TextArea logArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setServerStatusMessage("Stopped");
        startServer.setOnMouseClicked(
                event -> startServerButtonClicked()
        );
        stopServer.setOnMouseClicked(
                event -> stopServerButtonClicked()
        );
    }
    private void startServerButtonClicked(){
        LOGGER.log(Level.INFO,"Server Manually started");
        setServerStatusMessage("Running");
    }
    private void stopServerButtonClicked() {
        LOGGER.log(Level.INFO,"Server Manually stopped");
        setServerStatusMessage("Stopped");
    }
    private void setServerStatusMessage(String message){
        this.statusLabel.setText(message);
    }
}

