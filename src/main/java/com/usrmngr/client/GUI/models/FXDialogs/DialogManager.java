package com.usrmngr.client.GUI.models.FXDialogs;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogManager {
    public static boolean requestConfirmation(String message,String details) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation requested");
        alert.setHeaderText(message);
        alert.setContentText(details);
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();

    }

    public static void showError(String message,String details, boolean abort){
        showError(message,details);
        if(abort) {
            Platform.exit();
            System.exit(1);
        }
    }
     private static void showError(String message,String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(details);
        alert.showAndWait();
    }

    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(message);
        alert.showAndWait();
    }


}
