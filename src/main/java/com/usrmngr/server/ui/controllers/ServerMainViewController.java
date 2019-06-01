package com.usrmngr.server.ui.controllers;

import com.usrmngr.server.core.model.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class ServerMainViewController implements Initializable {

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
    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stopServer.setDisable(true);
        server = new Server();
        startServer.setOnMouseClicked(
                event -> startServerButtonClicked()
        );
        stopServer.setOnMouseClicked(
                event -> stopServerButtonClicked()
        );
    }

    private void startServerButtonClicked() {
        startServer.setDisable(true);
        stopServer.setDisable(false);
        setStatusMessage("Starting...");
        Thread serverThread = new Thread(server);
        serverThread.setName("Server");
        serverThread.start();
        setStatusMessage("Running");
    }

    private void stopServerButtonClicked() {
        startServer.setDisable(false);
        stopServer.setDisable(true);
        if (server == null || !server.isRunning()) return;
        setStatusMessage("Stopping...");
        server.stop();
        setStatusMessage("Stopped");
    }

    private void setStatusMessage(String message) {
        this.statusLabel.setText(message);
    }
}

