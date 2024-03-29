package com.usrmngr.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.usrmngr.Main.APP_NAME;
public class ClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/client/fxml/ClientMainView.fxml"));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
        window.setTitle(APP_NAME);

    }
}
