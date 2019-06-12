package com.usrmngr.server.ui.controllers;

import com.usrmngr.server.core.model.TextAreaAppender;
import com.usrmngr.util.Alert.AlertMaker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initServer();
        initGUI();
        initLogging();

    }

    private void initLogging() {
        TextAreaAppender.setTextArea(logArea);
    }


    private void initGUI() {
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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/server/fxml/About.fxml"));
            Scene aboutScene = new Scene(root);
            Stage configWindow = new Stage();
            // prevents the parent window from being modified before configs are closed.
            configWindow.initModality(Modality.WINDOW_MODAL);
            configWindow.initOwner(Stage.getWindows().filtered(Window::isShowing).get(0));
            configWindow.setTitle("About");
            configWindow.setScene(aboutScene);
            configWindow.setMaxHeight(426);
            configWindow.setWidth(305);
            configWindow.setResizable(false);
            configWindow.showAndWait();
        } catch (Exception e) {
            AlertMaker.showErrorMessage(e, "Unable to load About", "Failed to load about.");
        }


    }

    private void initServer() {
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
    }

    public void shutdown() {
    }
}

