package com.usrmngr.client.controllers;

import com.usrmngr.client.Main;
import com.usrmngr.client.models.FXNodeContainer;
import com.usrmngr.client.models.User;
import com.usrmngr.client.util.DataManager;
import com.usrmngr.client.util.DialogManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // private final String DATA_PATH = "C:\\Users\\jecsa\\IdeaProjects\\User_Manager\\src\\main\\resources\\com\\usrmngr\\client\\samples\\MOCK_DATA.json";
    private final String DATA_PATH = "src/main/resources/samples/MOCK_DATA.json";
    private JSONArray data;

    private User selectedUser;

    @FXML
    public VBox leftPane, centerPane;
    public GridPane bottomPane;
    @FXML
    TitledPane basicInfoDropdown, contactInfoDropdown, passwordDropdown;
    @FXML
    public Label userCount;
    @FXML
    public ListView<User> userList;
    @FXML
    public PasswordField password_entry, password_confirm_entry;
    @FXML
    MenuItem preferencesMenu, configurationsMenu;
    private FXNodeContainer allNodes; //todo find better way to get a hold of all the textfields programmatically

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allNodes = new FXNodeContainer();
        allNodes.addItem(centerPane);
        loadDefaultView();
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

    private void loadDefaultView() {
        clearAllTextFields();
        leftPane.setDisable(false);

        basicInfoDropdown.setExpanded(true);
        basicInfoDropdown.setMouseTransparent(true);

        contactInfoDropdown.setExpanded(false);
        contactInfoDropdown.setDisable(true);
        passwordDropdown.setExpanded(false);
        passwordDropdown.setDisable(true);

        bottomPane.setDisable(true);
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
            DialogManager.showError("Unable to load user list!", true);
        }

    }

    private boolean requestConfirmation(String message) {
        return DialogManager.requestConfirmation(message);
    }

    private void loadSampleData() {
        loadUserList();
    }

    @FXML
    public void editButtonClicked() {
        leftPane.setDisable(true);

        passwordDropdown.setDisable(true);
        passwordDropdown.setExpanded(false);
        basicInfoDropdown.setExpanded(true);
        basicInfoDropdown.setDisable(false);
        contactInfoDropdown.setDisable(false);
        contactInfoDropdown.setExpanded(true);
        bottomPane.setDisable(false);
    }

    @FXML
    public void addButtonClicked() {
      clearAllTextFields();
        leftPane.setDisable(true);

        passwordDropdown.setDisable(false);
        passwordDropdown.setExpanded(true);
        basicInfoDropdown.setExpanded(true);
        basicInfoDropdown.setDisable(false);
        contactInfoDropdown.setExpanded(true);
        contactInfoDropdown.setDisable(false);

        bottomPane.setDisable(false);
    }

    private void clearAllTextFields() {
        allNodes.clearTextFields();
    }

    @FXML
    public void cancelButtonClicked() {
        if (!DialogManager.requestConfirmation("All changes will be lost.")) return;
        clearAllTextFields();
        loadDefaultView();
    }

    @FXML
    public void saveButtonClicked() {
        //do the save
        DialogManager.showInfo("Saved!");
        loadDefaultView();
    }

    @FXML
    public void passwordResetButtonClicked() {

        //disable other buttons
        leftPane.setDisable(true);

        //able to see user info
        basicInfoDropdown.setExpanded(true);
        basicInfoDropdown.setMouseTransparent(true);

        //allow password change
        passwordDropdown.setExpanded(true);
        passwordDropdown.setDisable(false);
        //all save
        bottomPane.setDisable(false);

    }

    @FXML
    public void deleteButtonClicked() {
        if (selectedUser == null) return;;
        if(!requestConfirmation("User will be deleted.")) return;
        leftPane.setDisable(true);

        basicInfoDropdown.setMouseTransparent(true);
        basicInfoDropdown.setExpanded(true);

        contactInfoDropdown.setExpanded(false);
        contactInfoDropdown.setDisable(true);
        passwordDropdown.setExpanded(false);
        passwordDropdown.setDisable(true);

        bottomPane.setDisable(false);
    }

    public void configMenuSelected() {
        Main.showConfigSetup();
    }


    private boolean deleteUser(String id) {
        return false;
    }


}
