package com.usrmngr.client.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ClientMainViewController {

    @FXML
    private BorderPane root;

    @FXML
    private VBox leftPane;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Button passwordResetButton;

    @FXML
    private Label userCount;

    @FXML
    private ListView<?> userList;

    @FXML
    private VBox centerPane;

    @FXML
    private TitledPane basicInfoDropdown;

    @FXML
    private GridPane userAreaPane;

    @FXML
    private TextField givenName;

    @FXML
    private TextField initials;

    @FXML
    private TextField sn;

    @FXML
    private TextField displayName;

    @FXML
    private TextField mail;

    @FXML
    private Label id;

    @FXML
    private Label DN;

    @FXML
    private TitledPane contactInfoDropdown;

    @FXML
    private GridPane infoAreaPane;

    @FXML
    private TextField title;

    @FXML
    private TextField department;

    @FXML
    private TextField physicalDeliveryOfficeName;

    @FXML
    private TextField manager;

    @FXML
    private TextField otherTelephone;

    @FXML
    private TextField mobile;

    @FXML
    private TitledPane passwordDropdown;

    @FXML
    private GridPane passwordAreaPane;

    @FXML
    private CheckBox usrRandPwCBox;

    @FXML
    private PasswordField password_entry;

    @FXML
    private PasswordField password_confirm_entry;

    @FXML
    private MenuItem configurationsMenu;

    @FXML
    private MenuItem preferencesMenu;

    @FXML
    private GridPane bottomPane;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    void addButtonClicked(ActionEvent event) {

    }

    @FXML
    void cancelButtonClicked(ActionEvent event) {

    }

    @FXML
    void configMenuSelected(ActionEvent event) {

    }

    @FXML
    void deleteButtonClicked(ActionEvent event) {

    }

    @FXML
    void editButtonClicked(ActionEvent event) {

    }

    @FXML
    void passwordResetButtonClicked(ActionEvent event) {

    }

    @FXML
    void saveButtonClicked(ActionEvent event) {

    }

}

