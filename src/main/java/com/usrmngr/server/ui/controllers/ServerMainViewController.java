package com.usrmngr.server.ui.controllers;

import com.usrmngr.server.core.model.QuickServer.UMServer.UMServer;
import com.usrmngr.server.core.model.Logging.TextAreaAppender;
import com.usrmngr.util.Alert.AlertMaker;
import com.usrmngr.util.WindowHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.quickserver.net.AppException;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class ServerMainViewController implements Initializable {

    @FXML
    private BorderPane parentNode;

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
    private MenuItem editProperties, about;

    private UMServer umServer;
    private Properties properties;
    private boolean serverRunning;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProperties();
        initServer();
        initGUI();
        initLogging();
    }

    private void initProperties() {
        properties = PropertiesViewController.loadProperties();
        if (properties == null || properties.isEmpty()) {
            openPropertiesWindow();
            properties = PropertiesViewController.loadProperties();
        }
    }


    private void initLogging() {
        TextAreaAppender.setTextArea(logArea);
    }


    private void initGUI() {
        setStatusMessage("Stopped.");
        stopServer.setDisable(true);
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
                e -> openAboutWindow()
        );
        editProperties.setOnAction(
                e -> openPropertiesWindow()
        );
    }

    private void clearLogs() {
        logArea.clear();
    }

    private void openPropertiesWindow() {
        String windowName = "Properties";
        String fxmPath = "/server/fxml/PropertiesView.fxml";
        try {
            WindowHelper.showChildWindow(null, windowName, fxmPath);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorMessage(e, windowName, "Failed to load ".concat(windowName));

        }
    }

    private void openAboutWindow() {
        String windowName = "About";
        String fxmPath = "/server/fxml/AboutView.fxml";
        try {
            WindowHelper.showChildWindow(parentNode.getScene().getWindow(), windowName, fxmPath);
        } catch (Exception e) {
            AlertMaker.showErrorMessage(e, windowName, "Failed to load ".concat(windowName));
        }


    }

    private void initServer() {
        serverRunning = false;
        this.umServer = new UMServer();
        umServer.setName(properties.getProperty("serverName"));
        umServer.setPort(Integer.parseInt(properties.getProperty("port")));
    }

    private void startServerButtonClicked() {
        setStatusMessage("Starting...");
        try {
            umServer.startServer();
            serverRunning = true;
            startup();

        } catch (AppException e) {
            e.printStackTrace();
            setStatusMessage("Failed to start ... ");
        }
    }

    private void stopServerButtonClicked() {
        setStatusMessage("Stopping...");
        try {
            umServer.stopServer();
            serverRunning = false;
            shutdown();
        } catch (AppException e) {
            e.printStackTrace();
            setStatusMessage("Failed to stop Server...");
        }
    }

    public void startup() {
        setStatusMessage("Started.");
        startServer.setDisable(true);
        stopServer.setDisable(false);
    }

    public void shutdown() {
        if (serverRunning) {
            stopServerButtonClicked();
        }
        setStatusMessage("Stopped.");
        stopServer.setDisable(true);
        startServer.setDisable(false);

    }

    private void setStatusMessage(String message) {
        this.statusLabel.setText(message);
    }

}

