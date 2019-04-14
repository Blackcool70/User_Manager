package com.usrmngr.client;

import com.usrmngr.client.controllers.ConfigController;
import com.usrmngr.client.models.ADConnector;
import com.usrmngr.client.util.DialogManager;
import com.usrmngr.client.util.Constants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;

public class Main extends Application {
    public static Stage primaryStage;
    public static Properties properties;
    private ADConnector adConnector;

    @Override
    public void start(Stage window) throws Exception {
        primaryStage = window;
        properties = new Properties();
        // make sure there are setting configured if not then configure them.
        checkForProperties();
        String userName = properties.getProperty("userName");
        String hostName = properties.getProperty("hostName");
        Integer port = Integer.parseInt(properties.getProperty("port"));
        String ldapPath = properties.getProperty("ldapPath");

        adConnector = new ADConnector(hostName, port, ldapPath, userName);


        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        window.setTitle("User Manager: DEMO");
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setResizable(false);

        window.show();
    }

    public static void showConfigSetup() {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/fxml/ConfigView.fxml"));
            Scene configMenuScene = new Scene(root);
            Stage configWindow = new Stage();
            // prevents the parent window from being modified before configs are closed.
            configWindow.initModality(Modality.WINDOW_MODAL);
            configWindow.initOwner(Main.primaryStage);
            configWindow.setTitle("Configurations");
            configWindow.setScene(configMenuScene);
            configWindow.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkForProperties() {
        String dataPath = ConfigController.getUserDataDirectory();
        File configFile = new File(dataPath.concat(Constants.CONFIG_FILE_NAME));
        if (!configFile.exists()) {
            showConfigSetup();
            if (!configFile.exists()) {
                DialogManager.showError("No Configurations found! Application Terminating");
                Platform.exit();
                System.exit(0);
            }
        }
        try {
            Main.properties.load(new FileInputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
