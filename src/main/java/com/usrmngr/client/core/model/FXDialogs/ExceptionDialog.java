package com.usrmngr.client.core.model.FXDialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;


// thanks to https://code.makery.ch/blog/javafx-dialogs-official/
public class ExceptionDialog extends Alert {
    private final  String APP_NAME  = "USER_MANAGER";
    public ExceptionDialog(String contextText, Exception e) {
        super(AlertType.ERROR);
        this.setTitle(APP_NAME);
        this.setHeaderText("An Error occurred");
        this.setContentText(contextText);
        buildGUI(getFormattedExceptionText(e));
    }

    private void buildGUI(String exceptionText) {
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
        this.getDialogPane().setExpandableContent(expContent);
    }

    private String getFormattedExceptionText(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
