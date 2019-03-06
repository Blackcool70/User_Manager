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
import java.util.Optional;
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

    private ArrayList<Node> usrViewArea;
    private ArrayList<Node> usrOptionsArea;
    private ArrayList<Node> usrPasswordArea;
    private ArrayList<Node> usrSaveArea;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usrViewArea = ControllerHelper.getAllNodes(usrEditableGPane);
        usrOptionsArea = ControllerHelper.getAllNodes(usrOptionsVBox);
        usrPasswordArea = ControllerHelper.getAllNodes(usrPasswordGPane);
        usrSaveArea = ControllerHelper.getAllNodes(usrSaveGPane);
        loadSampleData();

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
        toggleNodes(usrViewArea, true);
    }

    private void disableEditArea() {
        toggleNodes(usrViewArea, false);
    }

    private void clearTextFields(Node node) {
        ((TextField) node).clear();
    }

    private void clearTextFields(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            if (node instanceof TextField) {
                clearTextFields(node);
            }
        }

    }

    private void toggleNodes(ArrayList<Node> nodes, boolean enabled) {
        for (Node node : nodes) {
            if (!(node instanceof Label)) {
                node.setDisable(!enabled);
            }
        }
    }

    private void disableMainButtons() {
        toggleNodes(usrOptionsArea, false);
    }

    private void enableMainButtons() {
        toggleNodes(usrOptionsArea, true);

    }

    public void onEditUserClicked() {
        disableMainButtons();
        enableUserEditArea();
        enableSavedArea();
    }
    private  void enableAllUserViewAreas(){
        enableUserEditArea();
        enableSavedArea();
        enablePasswordChangeArea();
    }

    public void onAddUserClicked() {
        clearUserViewArea();
        enableAllUserViewAreas();
    }
    private  boolean gotConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(message);
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;

    }

    private void clearUserViewArea() {
        clearTextFields(usrViewArea);
    }
    public void onClearUsrAreaClicked(){
        clearUserViewArea();
    }

    public void onSaveUserClicked() {
        // changed data
        disableEditArea();
        disablePasswordChangeArea();
        disableSavedArea();
        enableMainButtons();
        // data
    }

    private void enablePasswordChangeArea() {
        toggleNodes(usrPasswordArea, true);
    }

    private void disablePasswordChangeArea() {
        toggleNodes(usrPasswordArea, false);

    }

    private void enableSavedArea() {
        toggleNodes(usrSaveArea, true);
        usrSaveBtn.requestFocus();
    }

    private void disableSavedArea() {
        toggleNodes(usrSaveArea, false);
    }

    public void onResetPasswordClicked() {
        enablePasswordChangeArea();
        enableSavedArea();
    }

    public void onDeleteUserClicked() {
        if(gotConfirmation("User will be deleted.")){
            System.out.println("User deleted!");
        }

    }

    private void disableAllUsrViewArea(){
        disableEditArea();
        disablePasswordChangeArea();
        disableSavedArea();
    }
    public void onCancelEditClicked() {
        if(gotConfirmation("Changes will be lost.")){
            disableAllUsrViewArea();
            enableMainButtons();
            usrAddBtn.requestFocus();
        }
    }


    public void loadSampleData() {
        loadUserList();


    }
}
