package com.usrmngr.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static  Stage primaryStage;


    @Override
    public void start(Stage window) throws Exception {
        primaryStage = window;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        window.setTitle("User Manager: DEMO");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
