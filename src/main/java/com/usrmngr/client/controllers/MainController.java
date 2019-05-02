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
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
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
    private ArrayList<TitledPane> panes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allNodes = new FXNodeContainer();
        allNodes.addItem((Parent)basicInfoDropdown.getContent());
        allNodes.addItem((Parent)contactInfoDropdown.getContent());
        panes = new ArrayList<>();
        panes.add(basicInfoDropdown);
        panes.add(basicInfoDropdown);
        panes.add(passwordDropdown);
        //action for when a user gets double clicked on the list
        userList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
            ) {
                selectedUser = userList.getSelectionModel().getSelectedItem();
                loadUser(selectedUser);
            }
        });
        loadSampleData();
        loadDefaultView();
    }

    private void loadDefaultView() {
        loadUser(selectedUser);
        setAllFieldsDisabled(true);
        setMenuDisabled(false);
        setUserSectionDisabled(false);
        setSaveDisabled(true);
    }

    private void disableEdit(boolean disabled) {
        setAllFieldsDisabled(disabled);
        setSaveDisabled(disabled);
        setMenuDisabled(!disabled);
    }



    private void loadUser(User selectedUser) {
        if(selectedUser == null) return;
        allNodes.getTextFields().forEach(textField ->
                textField.setText(selectedUser.getAttribute(textField.getId())));
    }

    private void loadUserList() {
        ObservableList<User> displayableUsers = FXCollections.observableArrayList();
        try {
            data = getDataFromSource();
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                displayableUsers.add(new User(jsonObject));
            }
            userCount.setText(String.format("Users: %d", data.length()));
            userList.setItems(displayableUsers);
            selectedUser = displayableUsers.get(0);
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
        if(selectedUser == null) return;
        disableEdit(false);
    }

    @FXML
    public void addButtonClicked() {
        disableEdit(false);
        setAllDropdownExpanded(true);
        clearAllTextFields();
        selectedUser = null;
    }

    private void setSaveDisabled(boolean disabled) {
        if(selectedUser == null) return;
        this.bottomPane.setDisable(disabled);
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
        setMenuDisabled(false);
        //do the save
        DialogManager.showInfo("Saved!");
        loadDefaultView();
        loadUser(selectedUser);
    }

    @FXML
    public void passwordResetButtonClicked() {
        if(selectedUser == null) return;
        setMenuDisabled(true);
        setUserSectionDisabled(false);
        passwordDropdown.setExpanded(true);
        passwordDropdown.setDisable(false);
        setSaveDisabled(false);
    }

    @FXML
    public void deleteButtonClicked() {
        if(selectedUser == null) return;
        setMenuDisabled(true);
        setUserSectionDisabled(false);
        setMenuDisabled(true);
        setSaveDisabled(false);


    }

    private void setUserSectionDisabled(boolean disabled) {
        basicInfoDropdown.setMouseTransparent(disabled);
        basicInfoDropdown.setExpanded(true);
    }

    private void setMenuDisabled(boolean disabled) {
        leftPane.setDisable(disabled);
    }

    private void setAllDropdownExpanded(boolean expanded) {
        panes.forEach(pane -> pane.setExpanded(expanded));
    }

    private void setAllFieldsDisabled(boolean disabled) {
        panes.forEach(pane -> pane.setMouseTransparent(disabled));
    }

    public void configMenuSelected() {
        String configViewFXML = "/fxml/ConfigWindow/ConfigMainView.fxml";
        Main.screenLoader(configViewFXML, "Configurations");
    }


}
