package com.usrmngr.server;

import com.usrmngr.server.ui.controllers.ServerMainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage window) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/server/fxml/ServerMainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.setTitle("Server Manager Client");
        ServerMainViewController controller = loader.getController();
        window.setOnCloseRequest(e -> controller.shutdown());
        window.show();

    }
}
