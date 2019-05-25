package com.usrmngr.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;

import java.util.Date;

public class FXUserManagerServerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/server/fxml/ServerMainView.fxml"));
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
        window.setTitle("User Manager Server");
        Main.LOGGER.log(Level.INFO, "GUI application started");
    }
}
