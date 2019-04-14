package com.usrmngr.client.controllers;

import com.usrmngr.client.Main;
import com.usrmngr.client.models.FXNodeContainer;
import com.usrmngr.client.models.User;
import com.usrmngr.client.util.AlertManager;
import com.usrmngr.client.util.DataManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // private final String DATA_PATH = "C:\\Users\\jecsa\\IdeaProjects\\User_Manager\\src\\main\\resources\\com\\usrmngr\\client\\samples\\MOCK_DATA.json";
    private final String DATA_PATH = "src/main/resources/samples/MOCK_DATA.json";
    private JSONArray data;

    private User selectedUser;

    @FXML
    public VBox controlAreaPane;
    @FXML
    public GridPane userAreaPane, infoAreaPane, passwordAreaPane, licenseAreaPane, saveAreaPane;
    @FXML
    public TitledPane infoDropDown, passwordDropDown, licenseDropDown;

    @FXML
    public Label userCount;
    @FXML
    public ListView<User> userList;
    @FXML
    public Label id;
    @FXML
    public TextField display_name, first_name, last_name, email_address, user_phone, middle_initial;
    @FXML
    public TextField job_title, department_name, office_name, manager_name, office_number, user_number;
    @FXML
    public PasswordField password_entry, password_confirm_entry;
    @FXML
    MenuItem preferencesMenu, configurationsMenu;
    private FXNodeContainer allNodes;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allNodes = new FXNodeContainer();
        allNodes.addNodesFromParent(userAreaPane);
        allNodes.addNodesFromParent(infoAreaPane);
        allNodes.addNodesFromParent(passwordAreaPane);
        allNodes.addNodesFromParent(licenseAreaPane);
        allNodes.addNodesFromParent(saveAreaPane);

        disableAllAreas(true);
        allAreasExpanded(false);

        controlAreaPane.setDisable(false);

        //action for when a user gets double clicked on the list
        userList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
            ) {
                selectedUser = userList.getSelectionModel().getSelectedItem();
                loadUser(selectedUser);
            }
        });

        loadSampleData();
    }

    private void loadUser(User selectedUser) {
        String[] userAttributes = selectedUser.getAttributes();
        for (String attribute : userAttributes) {
            allNodes.setTextOnTextField(attribute, selectedUser.getAttribute(attribute));
        }
    }

    private void loadUserList() {
        ObservableList<User> displayableUsers = FXCollections.observableArrayList();
        try {
            data = new JSONArray(DataManager.readFile(DATA_PATH));
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                User user = new User(jsonObject);
                displayableUsers.add(user);
            }
            userCount.setText(String.format("Users: %d", data.length()));
            userList.setItems(displayableUsers);
        } catch (JSONException e) {
            AlertManager.showError("Unable to load user list!");
            Platform.exit();
            System.exit(0);
        }

    }
    private boolean requestConfirmation(String message) {
        return AlertManager.requestConfirmation(message);
    }

    private void disableAllAreas(boolean disable) {
        controlAreaPane.setDisable(disable);
        userAreaPane.setDisable(disable);
        infoAreaPane.setDisable(disable);
        licenseAreaPane.setDisable(disable);
        passwordAreaPane.setDisable(disable);
        saveAreaPane.setDisable(disable);
    }

    private void loadSampleData() {
        loadUserList();
    }

    private void allAreasExpanded(boolean expanded) {

        infoDropDown.setExpanded(expanded);
        passwordDropDown.setExpanded(expanded);
        licenseDropDown.setExpanded(expanded);
    }

    @FXML
    public void editButtonClicked() {
        if (selectedUser != null) {
            controlAreaPane.setDisable(true);
            userAreaPane.setDisable(false);
            infoAreaPane.setDisable(false);
            licenseAreaPane.setDisable(false);
            saveAreaPane.setDisable(false);
        }
    }

    @FXML
    public void addButtonClicked() {
        disableAllAreas(false);
        allAreasExpanded(true);
        clearAllTextFields();
        selectedUser = null;
        controlAreaPane.setDisable(true);
    }

    private void clearAllTextFields() {
        allNodes.clearTextFields();
    }

    @FXML
    public void cancelButtonClicked() {
        disableAllAreas(true);
        controlAreaPane.setDisable(false);
        allAreasExpanded(false);
//            clearFields();
    }

    @FXML
    public void saveButtonClicked() {
        disableAllAreas(true);
        allAreasExpanded(false);
        controlAreaPane.setDisable(false);
    }

    @FXML
    public void passwordResetButtonClicked() {
        if(selectedUser != null) {
            passwordDropDown.setExpanded(true);
            controlAreaPane.setDisable(true);
            passwordAreaPane.setDisable(false);
            saveAreaPane.setDisable(false);
        }
    }

    @FXML
    public void deleteButtonClicked() {
        boolean failed;
        if (selectedUser != null) {
            if (requestConfirmation("User will be deleted.")) {
               failed = deleteUser(selectedUser.getAttribute("id"));
               if(failed){
                   AlertManager.showError("Unable to complete request!");
               }else {
                   System.out.printf("User: %s deleted\n",selectedUser.getAttribute("id"));
               }
            }
        }
    }
    @FXML
    public void configMenuSelected() {
        Main.showConfigSetup();
    }
    private boolean deleteUser(String id) {
        return  false;
    }


}
