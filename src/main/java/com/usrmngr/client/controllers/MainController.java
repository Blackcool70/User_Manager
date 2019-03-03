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
    public VBox mainUserOptions;

    public TextField usrFNameFld;
    public TextField usrLNameFld;
    public TextField usrDisplayNameFld;
    public TextField usrEmailFld;
    public TextField usrPhoneFld;

    public ChoiceBox<String> usrOfficeCBox;
    public ChoiceBox<String> usrManagerCBox;
    public ChoiceBox<String> usrTitleCBox;

    public Button usrEditBtn;
    public Button usrSaveBtn;
    public Button usrPwChangeBtn;
    public Button usrDelBtn;
    public Button usrAddBtn;

    public Button usrAddLicBtn;
    public Button usrDelLicBtn;

    public ListView<String> userList;
    public Label usrID;

    private ArrayList<Node> usrEditAreaNodes;
    private ArrayList<Node> usrControlOptions;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usrEditAreaNodes = ControllerHelper.getAllNodes(usrEditableGPane);
        usrControlOptions = ControllerHelper.getAllNodes(mainUserOptions);
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
        toggleNodes(usrEditAreaNodes, true);
    }

    private void disableEditArea() {
        toggleNodes(usrEditAreaNodes, false);
    }

    private void toggleNodes(ArrayList<Node> nodes, boolean enabled) {
        for (Node node : nodes) {
            if (!(node instanceof Label)) {
                node.setDisable(!enabled);
            }
        }
    }

    private void disableMainButtons() {
        toggleNodes(usrControlOptions, false);
    }

    private void enableMainButtons() {
        toggleNodes(usrControlOptions, true);
    }

    public void onEditUserClicked() {
        disableMainButtons();
        enableUserEditArea();
        usrSaveBtn.requestFocus();
    }

    public void onSaveUserClicked() {
        //save changed data
        disableEditArea();
        enableMainButtons();
        usrEditBtn.requestFocus();
        //save data
    }

    public void onCancelEditClicked() {
        disableEditArea();
        enableMainButtons();
        usrEditBtn.requestFocus();
    }
}
