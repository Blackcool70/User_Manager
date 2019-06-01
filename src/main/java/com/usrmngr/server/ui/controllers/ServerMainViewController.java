package com.usrmngr.server.ui.controllers;

import com.usrmngr.server.core.model.Server;
import com.usrmngr.util.Alert.AlertMaker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private Button startServer, stopServer, clearLogs;
    @FXML
    private TextArea logArea;


    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu editMenu, helpMenu;
    @FXML
    private MenuItem editConfigs, about;


    private Server server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initServer();
        initGUI();


    }

    private void initGUI() {
        stopServer.setDisable(true);
        this.server = new Server();
        startServer.setOnMouseClicked(
                e -> startServerButtonClicked()
        );
        stopServer.setOnMouseClicked(
                e -> stopServerButtonClicked()
        );
        clearLogs.setOnMouseClicked(
                e -> clearLogs()
        );
        about.setOnAction(
                e -> showAboutWindow()
        );
        editConfigs.setOnAction(
                e -> openConfigWindow()
        );
    }

    private void clearLogs() {
        logArea.clear();
    }

    private void openConfigWindow() {
        System.out.println("Opening config window.");
    }

    private void showAboutWindow() {
        System.out.println("Opening about Window.");
    }

    private void initServer() {
        server = new Server();

    }

    private void startServerButtonClicked() {
        startServer.setDisable(true);
        stopServer.setDisable(false);
        setStatusMessage("Starting...");
        this.startup();
        setStatusMessage("Running");
    }

    private void stopServerButtonClicked() {
        startServer.setDisable(false);
        stopServer.setDisable(true);
        setStatusMessage("Stopping...");
        this.shutdown();
        setStatusMessage("Stopped");
    }

    private void setStatusMessage(String message) {
        this.statusLabel.setText(message);
    }

    public void startup() {
        Thread serverThread = new Thread(server);
        serverThread.setName("Server");
        serverThread.start();
    }

    public void shutdown() {
        if (server == null || !server.isRunning()) return;
        server.stop();
    }
}

