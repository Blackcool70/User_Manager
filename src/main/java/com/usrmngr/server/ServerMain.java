package com.usrmngr.server;

import com.usrmngr.server.ui.controllers.ServerMainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerMain extends Application {
    private static final Logger LOGGER = LogManager.getLogger(ServerMain.class.getName());

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
    private void initLogging() {
        //configure global app  logging
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        LOGGER.log(Level.INFO, "Application started on {}", dateFormat.format(new Date()));

        Thread thread = new Thread(() ->
                LOGGER.log(Level.INFO, "Application stopped on {}.", dateFormat.format(new Date())));
        thread.setName("Server");
        Runtime.getRuntime().addShutdownHook(thread);
    }
}
