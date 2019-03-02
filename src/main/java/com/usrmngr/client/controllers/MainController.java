package com.usrmngr.client.controllers;

import com.usrmngr.client.util.ControllerHelper;
import com.usrmngr.client.util.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public GridPane usrEditableGPane;

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

    private JSONArray DEMO_DATA;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usrEditAreaNodes = ControllerHelper.getAllNodes(usrEditableGPane);
        loadUserList();

    }

    private void loadUserList(){
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

    private  void toggleEnableUserEditArea(){
        for (Node node : usrEditAreaNodes) {
            node.setDisable(!node.isDisabled());
        }
    }

    public void onEditUserClicked() {
        usrEditBtn.setDisable(true);
        toggleEnableUserEditArea();

    }
    public void onSaveUserClicked(){
        //save changed data
        toggleEnableUserEditArea();
        usrEditBtn.setDisable(false);
    }



}
