package com.usrmngr.client.GUI;

import com.usrmngr.client.GUI.models.FXDialogs.ExceptionDialog;
import com.usrmngr.client.GUI.util.DataManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static com.usrmngr.client.GUI.Constants.APP_NAME;

public class FXUserManagerMain extends Application {
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
        if(DataManager.getProperties().isEmpty()){
            loadConfigView();
        }
    }

    private void loadConfigView() {
        String configViewFXML = "/fxml/ConfigWindow/ConfigMainView.fxml";
        loadAsChildWindow(configViewFXML, "Configurations");
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

    public static void loadAsChildWindow(String fxmlPath, String title) {
        try {
            Stage configWindow = new Stage();
            // prevents the parent window from being modified before configs are closed.
            configWindow.initModality(Modality.WINDOW_MODAL);
            configWindow.initOwner(primaryStage);
            configWindow.setTitle(title);
            configWindow.setScene( new Scene(FXMLLoader.load(FXUserManagerMain.class.getResource(fxmlPath))));
            configWindow.showAndWait();
        } catch (IOException e) {
            new ExceptionDialog("Problem loading Configurations window.", e).showAndWait();
            e.printStackTrace();
            Platform.exit();
        }

    }
}
