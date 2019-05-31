package com.usrmngr.server.ui.controllers;

import com.usrmngr.server.core.model.Preferences;
import com.usrmngr.server.core.model.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.apache.logging.log4j.Level;

import java.net.URL;
import java.util.ResourceBundle;

import static com.usrmngr.Main.LOGGER;
import static com.usrmngr.server.core.model.Constants.DEFAULT_FULL_CONFIG_FILE_PATH;

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
    private  TextArea logArea;
    private Server server;
    private  Preferences preferences;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preferences = Preferences.getPreferences(DEFAULT_FULL_CONFIG_FILE_PATH);
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
        server = new Server(Preferences.getPreferences(DEFAULT_FULL_CONFIG_FILE_PATH));
        new Thread(server).start();

    }
    private void stopServerButtonClicked() {
        LOGGER.log(Level.INFO,"Server Manually stopped");
        setServerStatusMessage("Stopped");
        server.stop();
    }
    private void setServerStatusMessage(String message){
        this.statusLabel.setText(message);
    }
}

