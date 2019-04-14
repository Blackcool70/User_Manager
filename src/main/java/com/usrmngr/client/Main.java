package com.usrmngr.client;

import com.usrmngr.client.util.AlertManager;
import com.usrmngr.client.util.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;

import static com.usrmngr.client.util.Constants.APP_NAME;

public class Main extends Application {
    public static  Stage primaryStage;
    public static Properties properties;

    @Override
    public void start(Stage window) throws Exception {
        primaryStage = window;

        properties =  getProperties();

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

    public static Properties getProperties(){
        String dataPath= getUserDataDirectory();
        File configFile = new File(dataPath.concat(Constants.CONFIG_FILE_NAME));
        Properties properties = new Properties();
        try {
            if (!configFile.exists()) {
                showConfigSetup();
                if (!configFile.exists()) {
                    AlertManager.showError("No Configurations found, aborting!");
                    System.exit(0);
                }
            }
            properties.load(new FileInputStream(configFile));
        }catch ( IOException e) {
            e.printStackTrace();
        }
        return  properties;
    }
    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator + ".".concat(APP_NAME.toLowerCase()) + File.separator;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
