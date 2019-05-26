package com.usrmngr.server.util.Alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
// credit: https://github.com/afsalashyana/Library-Assistant/blob/master/src/library/assistant/alert/AlertMaker.java
public class AlertMaker {

    public static void showSimpleAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
//        styleAlert(alert);
        alert.showAndWait();
    }

    public static void showErrorMessage(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
//        styleAlert(alert);
        alert.showAndWait();
    }

    public static void showErrorMessage(Exception ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error occurred");
        alert.setHeaderText("Error Occurred");
        alert.setContentText(ex.getLocalizedMessage());

        addExceptionToAlert(ex, alert);

        //styleAlert(alert);
        alert.showAndWait();
    }

    public static void showErrorMessage(Exception ex, String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error occurred");
        alert.setHeaderText(title);
        alert.setContentText(content);

        addExceptionToAlert(ex, alert);
        alert.showAndWait();
    }

    private static void addExceptionToAlert(Exception ex, Alert alert) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
    }

//    private static void styleAlert(Alert alert) {
//        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        DialogPane dialogPane = alert.getDialogPane();
//        dialogPane.getStylesheets().add(AlertMaker.class.getResource("/resources/dark-theme.css").toExternalForm());
//        dialogPane.getStyleClass().add("custom-alert");
//    }
}
