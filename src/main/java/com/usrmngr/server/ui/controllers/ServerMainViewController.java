package com.usrmngr.server.ui.controllers;

import com.usrmngr.server.core.model.RMI.*;
import com.usrmngr.server.core.model.TextAreaAppender;
import com.usrmngr.util.Alert.AlertMaker;
import com.usrmngr.util.WindowHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.Properties;
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
    private MenuItem editProperties, about;

    private Server server;
    private Properties properties;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initServer();
        initGUI();
        initLogging();
    }
    private  Properties getProperties(){
        return  this.properties;
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

    public void setProperties(Properties properties){
       this.properties = properties;
    }
    private void clearLogs() {
        logArea.clear();
    }

    private void openPropertiesWindow() {
        String windowName = "Properties";
        String fxmPath = "/server/fxml/PropertiesView.fxml";
        try {
            WindowHelper.showChildWindow(Stage.getWindows().filtered(Window::isShowing).get(0),windowName,fxmPath);
        } catch (Exception e) {
            AlertMaker.showErrorMessage(e, windowName, "Failed to load ".concat(windowName));
        }
    }

    private void openAboutWindow() {
        String windowName = "About";
        String fxmPath = "/server/fxml/AboutView.fxml";
        try {
            WindowHelper.showChildWindow(Stage.getWindows().filtered(Window::isShowing).get(0),windowName,fxmPath);
        } catch (Exception e) {
            AlertMaker.showErrorMessage(e, windowName, "Failed to load ".concat(windowName));
        }


    }

    private void initServer() {
        this.server = new Server();
    }

    private void startServerButtonClicked() {
        setStatusMessage("Starting...");
        this.startup();
    }

    private void stopServerButtonClicked() {
        setStatusMessage("Stopping...");
        this.shutdown();
    }

    private void setStatusMessage(String message) {
        this.statusLabel.setText(message);
    }

    private void startup() {
        server.startUp();
        if (server.isRunning()) {
            setStatusMessage("Running");
            startServer.setDisable(true);
            stopServer.setDisable(false);
        } else {
            setStatusMessage("Failed");
        }

    }

    public void shutdown() {
        server.shutDown();
        if (server.isRunning()) {
            setStatusMessage("Failed.");
        } else {
            setStatusMessage("Stopping...");
            startServer.setDisable(false);
            stopServer.setDisable(true);
        }
    }
}

