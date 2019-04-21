package com.usrmngr.client.models.FXDialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import static com.usrmngr.client.Constants.APP_NAME;

public class CredentialsDialog extends Dialog {
    private ButtonType loginButtonType;
    private TextField username;
    private PasswordField password;
    private GridPane grid;
    private String defaultUsername, defaultPassowrd;

    public CredentialsDialog() {
        this.defaultUsername = "";
        this.defaultPassowrd = "";
        this.setTitle(APP_NAME.concat(":Login"));
        this.setHeaderText("Please enter your credentials");
        buildGUI();
        setBehavior();
    }

    public CredentialsDialog(String defaultUsername, String defaultPassword) {
        this.defaultUsername = defaultUsername;
        this.defaultPassowrd = defaultPassword;
        this.setTitle(APP_NAME.concat(":Login"));
        this.setHeaderText("Please enter your credentials");
        buildGUI();
        setBehavior();
    }

    private void setBehavior() {
        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = this.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        this.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);
        username.setText(defaultUsername);

        // Convert the result to a username-password-pair when the login button is clicked.
        this.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
    }

    private void buildGUI() {
        loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        // Create the username and password labels and fields.
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        username = new TextField();
        username.setPromptText("Username");
        password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

    }


}

