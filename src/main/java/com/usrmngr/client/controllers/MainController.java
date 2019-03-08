package com.usrmngr.client.controllers;

import com.usrmngr.client.User;
import com.usrmngr.client.util.ControllerHelper;
import com.usrmngr.client.util.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public ListView<User> stringListView;
    public Label usrID;

    private ArrayList<Node> usrViewArea;
    private ArrayList<Node> usrOptionsArea;
    private ArrayList<Node> usrPasswordArea;
    private ArrayList<Node> usrSaveArea;
    private final String DATA_PATH = "/Users/jecsan/IdeaProjects/User_Manager/src/main/resources/com/usrmngr/client/samples/data.json";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usrViewArea = ControllerHelper.getAllNodes(usrEditableGPane);
        usrOptionsArea = ControllerHelper.getAllNodes(usrOptionsVBox);
        usrPasswordArea = ControllerHelper.getAllNodes(usrPasswordGPane);
        usrSaveArea = ControllerHelper.getAllNodes(usrSaveGPane);

        stringListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
            ) {
                String selected = stringListView.getSelectionModel().getSelectedItem().toString();

                System.out.printf("Double clicked on : %s\n", selected);
            }
        });
        loadSampleData();

    }

    private void loadUserList() throws JSONException {
        ObservableList<User> displayableUsers = FXCollections.observableArrayList();
        JSONArray userList = new JSONArray(DataManager.readFile(DATA_PATH));
        JSONObject user;
        for (int i = 0; i < userList.length(); i++) {
            user = userList.getJSONObject(i);
            displayableUsers.add(new User(
                    user.getString("first_name") + user.getString("last_name"), user.getInt("id")));
        }
        stringListView.setItems(displayableUsers);
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

    private void enableAllUserViewAreas() {
        enableUserEditArea();
        enableSavedArea();
        enablePasswordChangeArea();
    }

    public void onAddUserClicked() {
        clearUserViewArea();
        enableAllUserViewAreas();
    }

    private boolean gotConfirmation(String message) {
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

    public void onClearUsrAreaClicked() {
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
        if (gotConfirmation("User will be deleted.")) {
            System.out.println("User deleted!");
        }

    }

    private void disableAllUsrViewArea() {
        disableEditArea();
        disablePasswordChangeArea();
        disableSavedArea();
    }

    public void onCancelEditClicked() {
        if (gotConfirmation("Changes will be lost.")) {
            disableAllUsrViewArea();
            enableMainButtons();
            usrAddBtn.requestFocus();
        }
    }

    private void loadSampleData() {
        try {
            loadUserList();
        } catch (JSONException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to Load Users");
            alert.setContentText("An Error Occurred loading users.");
            alert.showAndWait();

        }


    }
}
