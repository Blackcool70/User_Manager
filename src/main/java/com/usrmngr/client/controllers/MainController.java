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

    private final String DATA_PATH = "/Users/jecsan/IdeaProjects/User_Manager/src/main/resources/com/usrmngr/client/samples/data.json";
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
    public Button editButton;
    public Button cancelButton;
    public Button saveButton;
    public Button passwordResetButton;
    public Button deleteButton;
    public Button addButton;
    public Button usrAddLicBtn;
    public Button usrDelLicBtn;
    public ListView<User> stringListView;
    public Label usrID;
    private ArrayList<Node> viewArea;
    private ArrayList<Node> controlsButtons;
    private ArrayList<Node> passwordArea;
    private ArrayList<Node> saveArea;
    private JSONArray data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controlsButtons = ControllerHelper.getAllNodes(usrOptionsVBox);

        viewArea = ControllerHelper.getAllNodes(usrEditableGPane);
        passwordArea = ControllerHelper.getAllNodes(usrPasswordGPane);
        saveArea = ControllerHelper.getAllNodes(usrSaveGPane);

        stringListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
            ) {
                User selectedUser = stringListView.getSelectionModel().getSelectedItem();
                loadUser(selectedUser);
            }
        });

        editButton.setOnMouseClicked(event -> {
            disableControlButtons();
            enableEditArea();
            enableSavedArea();
        });
        addButton.setOnMouseClicked(event -> {
            disableControlButtons();
            clearFields();
            enableAllAreas();
        });
        cancelButton.setOnMouseClicked(event -> {
            disableAllAreas();
            enableControlButtons();
            addButton.requestFocus();
        });
        saveButton.setOnMouseClicked(event -> {
            disableAllAreas();
            disablePasswordArea();
//            disableSaveArea();
            enableControlButtons();
        });
        passwordResetButton.setOnMouseClicked(event -> {
            disableControlButtons();
            enablePasswordArea();
            enableSavedArea();
        });
        deleteButton.setOnMouseClicked(event -> {
            if (getConfirm("User will be deleted.")) {
                System.out.println("User deleted!");
            }
        });

        loadSampleData();
    }

    private void loadUser(User selectedUser) {

        try {
            JSONObject user = data.getJSONObject(Integer.parseInt(selectedUser.getId()) - 1);
            usrFName.setText(user.getString("first_name"));
            usrLName.setText(user.getString("last_name"));
            usrEmail.setText(user.getString("email"));
            usrDisplayName.setText(selectedUser.toString());
            usrID.setText(selectedUser.getId());
            usrPhone.setText(user.getString("phone"));
        } catch (JSONException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to Load User");
            alert.setContentText("An Error Occurred loading user.");
            alert.showAndWait();
        }

    }

    private void loadUserList() throws JSONException {
        ObservableList<User> displayableUsers = FXCollections.observableArrayList();
        data = new JSONArray(DataManager.readFile(DATA_PATH));
        JSONObject user;
        for (int i = 0; i < data.length(); i++) {
            user = data.getJSONObject(i);
            displayableUsers.add(new User(
                    user.getString("first_name") + user.getString("last_name"), user.getString("id")));
        }
        stringListView.setItems(displayableUsers);
    }

    private void enableEditArea() {
        toggleWidgets(viewArea, true);
    }

    private void disableEditArea() {
        toggleWidgets(viewArea, false);
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

    private void toggleWidgets(ArrayList<Node> nodes, boolean enabled) {
        for (Node node : nodes) {
            if (!(node instanceof Label)) {
                node.setDisable(!enabled);
            }
        }
    }

    private void disableControlButtons() {
        toggleWidgets(controlsButtons, false);
    }

    private void enableControlButtons() {
        toggleWidgets(controlsButtons, true);

    }


    private boolean getConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(message);
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;

    }

    private void clearFields() {
        clearTextFields(viewArea);
    }

    private void enablePasswordArea() {
        toggleWidgets(passwordArea, true);
    }

    private void disablePasswordArea() {
        toggleWidgets(passwordArea, false);

    }

    private void enableSavedArea() {
        toggleWidgets(saveArea, true);
        saveButton.requestFocus();
    }

    private void disableSaveArea() {
        toggleWidgets(saveArea, false);
    }

    private void enableAllAreas() {
        enableEditArea();
        enableSavedArea();
        enablePasswordArea();
    }

    private void disableAllAreas() {
        disableEditArea();
        disablePasswordArea();
        disableSaveArea();
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
