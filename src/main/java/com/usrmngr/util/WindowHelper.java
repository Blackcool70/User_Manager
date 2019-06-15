package com.usrmngr.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class WindowHelper {

    public static void showChildWindow(Window parentWindow,String windowTitle, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(WindowHelper.class.getResource(fxmlPath));
        Scene aboutScene = new Scene(root);
        Stage configWindow = new Stage();
        // prevents the parent window from being modified before configs are closed.
        configWindow.initModality(Modality.WINDOW_MODAL);
        configWindow.initOwner(parentWindow);
        configWindow.setTitle(windowTitle);
        configWindow.setScene(aboutScene);
        configWindow.setResizable(false);
        configWindow.showAndWait();
    }
}
