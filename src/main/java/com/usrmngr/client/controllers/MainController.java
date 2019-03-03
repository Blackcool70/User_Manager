package com.usrmngr.client.controllers;

import com.usrmngr.client.util.ControllerHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public GridPane usrEditableGPane;
    public VBox usrOptionsVBox;
    public GridPane usrPasswordGPane;
    public GridPane usrSaveGPane;

    public TextField usrFName;
    public TextField usrLName;
    public TextField usrDisplayName;
    public TextField usrEmail;
    public TextField usrPhone;

    public CheckBox usrRandPwCBox;
    public PasswordField usrPassword;
    public PasswordField usrPasswordConfirm;

    public ChoiceBox<String> usrOffice;
    public ChoiceBox<String> usrManager;
    public ChoiceBox<String> usrTitle;

    public Button usrEditBtn;
    public Button usrCancelBtn;
    public Button usrSaveBtn;
    public Button usrPwChangeBtn;
    public Button usrDelBtn;
    public Button usrAddBtn;

    public Button usrAddLicBtn;
    public Button usrDelLicBtn;

    public ListView<String> userList;
    public Label usrID;

    private ArrayList<Node> usrEditArea;
    private ArrayList<Node> usrControlArea;
    private ArrayList<Node> usrPasswordArea;
    private ArrayList<Node> usrSaveArea;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usrEditArea = ControllerHelper.getAllNodes(usrEditableGPane);
        usrControlArea = ControllerHelper.getAllNodes(usrOptionsVBox);
        usrPasswordArea = ControllerHelper.getAllNodes(usrPasswordGPane);
        usrSaveArea = ControllerHelper.getAllNodes(usrSaveGPane);
        loadUserList();

    }

    private void loadUserList() {
        ObservableList<String> usrStrings = FXCollections.observableArrayList("Babara Burkle",
                "Estela Tiedemann",
                "Sona Fein",
                "Riva Gipe",
                "Xiomara Mclaughin",
                "Andrew Steimle",
                "Jada Alday",
                "Alejandro Hickey",
                "Joycelyn Kennerly",
                "Gabriela Augustin",
                "Jonathon Schur",
                "Adah Poulos",
                "Henrietta Forrest",
                "Seymour Chia",
                "Audry Callaghan",
                "Kellie Zucco",
                "Latanya Pittenger",
                "Inocencia Wrinkle",
                "Geraldine Rusek",
                "Conception Fischetti");
        userList.setItems(usrStrings);
    }

    private void enableUserEditArea() {
        toggleNodes(usrEditArea, true);
    }

    private void disableEditArea() {
        toggleNodes(usrEditArea, false);
    }

    private void toggleNodes(ArrayList<Node> nodes, boolean enabled) {
        for (Node node : nodes) {
            if (!(node instanceof Label)) {
                node.setDisable(!enabled);
            }
        }
    }

    private void disableMainButtons() {
        toggleNodes(usrControlArea, false);
    }

    private void enableMainButtons() {
        toggleNodes(usrControlArea, true);

    }

    public void onEditUserClicked() {
        disableMainButtons();
        enableUserEditArea();
        enableSavedArea();
    }

    public void onSaveUserClicked() {
        // changed data
        disableEditArea();
        disablePasswordChangeArea();
        disableSavedArea();
        enableMainButtons();
        // data
    }
    private void enablePasswordChangeArea(){
        toggleNodes(usrPasswordArea,true);
    }
    private void disablePasswordChangeArea(){
        toggleNodes(usrPasswordArea,false);

    }
    private  void enableSavedArea(){
        toggleNodes(usrSaveArea,true);
        usrSaveBtn.requestFocus();
    }
    private  void disableSavedArea(){
        toggleNodes(usrSaveArea,false);
    }
    public void onChangePasswordClicked(){
        enablePasswordChangeArea();
        enableSavedArea();
    }

    public void onCancelEditClicked() {
        disableEditArea();
        disablePasswordChangeArea();
        disableSavedArea();
        enableMainButtons();
        usrEditBtn.requestFocus();
    }
}
