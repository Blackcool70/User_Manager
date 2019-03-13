package com.usrmngr.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    //TODO: 2019-03-13  figure out the proper way to resolve paths
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("./com/usrmngr/client/fxml/MainView.fxml"));
        window.setTitle("User Manager: DEMO");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
