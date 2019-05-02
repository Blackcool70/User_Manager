package com.usrmngr.client;

import com.usrmngr.client.models.FXDialogs.ExceptionDialog;
import com.usrmngr.client.util.DataManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

import static com.usrmngr.client.Constants.APP_NAME;

public class Main extends Application {
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        primaryStage = window;
        loadProperties();
        loadMainView();
    }

    private void loadProperties() {
        Properties properties = DataManager.getProperties();
        if (properties.isEmpty()) {
            loadConfigView();
        }
    }

    private void loadConfigView() {
        String configViewFXML = "/fxml/ConfigWindow/ConfigMainView.fxml";
        screenLoader(configViewFXML, "Configurations");
    }


    // default properties
    public static Properties getProperties(){
        return DataManager.getProperties();
    }

    private void loadMainView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
            primaryStage.setTitle(APP_NAME);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            new ExceptionDialog("Problem loading program.", e).showAndWait();
            e.printStackTrace();
            Platform.exit();
        }
    }

    public static void screenLoader(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(fxmlPath));
            Scene configMenuScene = new Scene(root);
            Stage configWindow = new Stage();
            // prevents the parent window from being modified before configs are closed.
            configWindow.initModality(Modality.WINDOW_MODAL);
            configWindow.initOwner(primaryStage);
            configWindow.setTitle(title);
            configWindow.setScene(configMenuScene);
            configWindow.showAndWait();
        } catch (IOException e) {
            new ExceptionDialog("Problem loading Configurations window.", e).showAndWait();
            e.printStackTrace();
            Platform.exit();
        }

    }
}
