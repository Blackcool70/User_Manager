package com.usrmngr.client.controllers;

import com.usrmngr.client.Main;
import com.usrmngr.client.models.FXDialogs.DialogManager;
import com.usrmngr.client.models.FXNodeContainer;
import com.usrmngr.client.models.User;
import com.usrmngr.client.util.DataManager;
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
    private TitledPane[] panes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allNodes = new FXNodeContainer();
        allNodes.addItem(centerPane);
        panes = new TitledPane[]{basicInfoDropdown, contactInfoDropdown, passwordDropdown};
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
        disableMenu(false);
        disableUserSection(false);
        disableSave(true);
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
            data = getDataFromSource();
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

    private JSONArray getDataFromSource() throws JSONException {
        return new JSONArray(DataManager.readFile((DATA_PATH)));
    }

    private boolean requestConfirmation(String message) {
        return DialogManager.requestConfirmation(message);
    }

    private void loadSampleData() {
        loadUserList();
    }

    @FXML
    public void editButtonClicked() {
        setAllFieldsDisabled(false);
        setAllDropdownExpanded(true);
        disableUserSection(false);
        passwordDropdown.setExpanded(false);
        passwordDropdown.setDisable(true);
        disableSave(false);
        disableMenu(true);
    }

    @FXML
    public void addButtonClicked() {
        clearAllTextFields();
        setAllFieldsDisabled(false);
        setAllDropdownExpanded(true);
        disableSave(false);
        disableMenu(true);
    }

    private void disableSave(boolean b) {
        this.bottomPane.setDisable(b);
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
        disableMenu(false);
        //do the save
        DialogManager.showInfo("Saved!");
        loadDefaultView();
        loadUser(selectedUser);
    }

    @FXML
    public void passwordResetButtonClicked() {
        disableMenu(true);
        disableUserSection(false);
        passwordDropdown.setExpanded(true);
        passwordDropdown.setDisable(false);
        disableSave(false);
    }

    @FXML
    public void deleteButtonClicked() {
        disableMenu(true);
        disableUserSection(false);
        disableMenu(true);
        disableSave(false);


    }

    private void disableUserSection(boolean disabled) {
        basicInfoDropdown.setMouseTransparent(disabled);
        basicInfoDropdown.setExpanded(true);
    }

    private void disableMenu(boolean disabled) {
        setAllDropdownExpanded(false);
        setAllFieldsDisabled(true);
        leftPane.setDisable(disabled);
    }

    private void setAllDropdownExpanded(boolean expanded) {
        for (TitledPane pane : panes)
            pane.setExpanded(expanded);
    }

    private void setAllFieldsDisabled(boolean disabled) {
        for (TitledPane pane : panes)
            pane.setDisable(disabled);
    }

    public void configMenuSelected() {
        String configViewFXML = "/fxml/ConfigWindow/ConfigMainView.fxml";
        Main.screenLoader(configViewFXML, "Configurations");
    }


}
