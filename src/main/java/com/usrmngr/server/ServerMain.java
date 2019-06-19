package com.usrmngr.server;

import com.usrmngr.server.ui.controllers.ServerMainViewController;
import com.usrmngr.util.Alert.AlertMaker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerMain extends Application {
    public static final String APP_NAME = "USER MANAGER SERVER";

    public static void main(String[] args) {
        launch(args);
    }





    @Override
    public void start(Stage window) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/fxml/ServerMainView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.setTitle(APP_NAME);
            ServerMainViewController controller = loader.getController();
            window.setOnCloseRequest(e -> controller.shutdown());
            window.show();
        } catch (Exception e) {
            AlertMaker.showErrorMessage(e, APP_NAME, "Oops, you are not suppose to see this. Please report this to the developers.");
            e.printStackTrace();
            quit(1);
        }

    }

    private void quit(int status) {
        Runtime.getRuntime().exit(status);
        System.exit(status);
    }
}
