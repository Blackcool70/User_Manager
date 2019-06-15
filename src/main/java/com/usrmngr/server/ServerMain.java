package com.usrmngr.server;

import com.usrmngr.server.ui.controllers.ServerMainViewController;
import com.usrmngr.util.Alert.AlertMaker;
import com.usrmngr.util.WindowHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerMain extends Application {
    private static final String APP_NAME = "USER MANAGER SERVER";
    private static final String PROPERTIES_FILE = "config.properties";
    private Stage rootWindow;

    public static void main(String[] args) {
        launch(args);
    }

    private String getPropertiesPath() {
        return System.getProperty("user.home") + File.separator +
                APP_NAME.toLowerCase().replaceAll(" ", "_") +
                File.separator +
                PROPERTIES_FILE;
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        String path = getPropertiesPath();
        try {
            properties.load(new FileInputStream(new File(path)));
        } catch (IOException ignored) {
            AlertMaker.showSimpleAlert("Properties", "Failed to load properties.");
        }
        return properties;
    }

    @Override
    public void start(Stage window) {
        try {
            this.rootWindow = window;
            Properties serverProperties = getProperties();
            if (serverProperties.isEmpty())
                openPropertiesWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/fxml/ServerMainView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.setTitle(APP_NAME);
            ServerMainViewController controller = loader.getController();
            controller.setProperties(serverProperties);
            window.setOnCloseRequest(e -> controller.shutdown());
            window.show();
        } catch (Exception e) {
            AlertMaker.showErrorMessage(e, APP_NAME, "Oops, you are not suppose to see this. Please report this to the developers.");
            e.printStackTrace();
            System.exit(1);
        }

    }

    private void openPropertiesWindow() {
        String windowName = "Properties";
        String fxmPath = "/server/fxml/PropertiesView.fxml";
        try {
            WindowHelper.showChildWindow(rootWindow, windowName, fxmPath);
            if (getProperties().isEmpty()) {
                AlertMaker.showSimpleAlert(windowName, "Invalid properties. Application closing.");
                quit(1);
            }
        } catch (Exception e) {
            AlertMaker.showErrorMessage(e, windowName, "Failed to load required properties. Application closing.");
            quit(1);
            e.printStackTrace();

        }
    }

    private void quit(int status) {
        Runtime.getRuntime().exit(status);
        System.exit(status);
    }
}
