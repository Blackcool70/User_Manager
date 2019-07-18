package com.usrmngr.client.ui.controllers;

import com.usrmngr.client.core.model.Connectors.ADConnector;
import com.usrmngr.client.core.model.Connectors.LDAPConfig;
import com.usrmngr.client.core.model.Connectors.LDAPConnector;
import com.usrmngr.client.core.model.FXDialogs.DialogManager;
import com.usrmngr.client.core.model.FXNodeContainer;
import com.usrmngr.client.core.model.User;
import com.usrmngr.util.Alert.AlertMaker;
import javafx.application.Platform;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class ClientMainViewController implements Initializable {

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
    public Label userCount, DN;
    @FXML
    public ListView<User> userList;
    @FXML
    public PasswordField password_entry, password_confirm_entry;
    @FXML
    MenuItem preferencesMenu, configurationsMenu;
    private FXNodeContainer allNodes; //todo find better way to get a hold of all the textfields programmatically
    private ArrayList<TitledPane> panes;
    private ADConnector adConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectToAD(getProgramConfig()); // will connect or quit
        initController();
        loadUserList();
        loadDefaultView();
    }

    private void initController() {
        allNodes = new FXNodeContainer();
        allNodes.addItem((Parent) basicInfoDropdown.getContent());
        allNodes.addItem((Parent) contactInfoDropdown.getContent());
        panes = new ArrayList<>();
        panes.add(basicInfoDropdown);
        panes.add(contactInfoDropdown);
        panes.add(passwordDropdown);
        //action for when a user gets double clicked on the list
        userList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
            ) {
                selectedUser = userList.getSelectionModel().getSelectedItem();
                //selectedUser = new User(LDAPConnector.getADUser(selectedUser.getAttribute("CN")));
                loadUser(selectedUser);
            }
        });
    }

    private void connectToAD(LDAPConfig config) {
        adConnector =  new ADConnector(config);
        adConnector.connect();
        while (!adConnector.isConnected() && !adConnector.isAuthenticated()) {
            if (DialogManager.requestConfirmation("Unable to connect,Open configurations?","Error Occurred")) {
                configMenuSelected();
                adConnector.connect();
                adConnector.authenticate("cn=Administrator,ou=Users,ou=Company,dc=lab,dc=net","J3cs4nb!");//will clean up
            } else {
                Platform.exit();
                System.exit(0);
            }
        }
    }

    private LDAPConfig getProgramConfig() {
        File configFile = new File(System.getProperty("user.home")+File.separator+ ".USER_MANAGER_CLIENT");
        LDAPConfig ldapConfig = new LDAPConfig();
        if(configFile.exists()) {
            try {
                ldapConfig.load(configFile);
            } catch (IOException e) {
                AlertMaker.showSimpleAlert("Failure","Failed to load configurations. Aborting.");
                Platform.exit();
                System.exit(1);
            }
        }else {
            ldapConfig.setPort(389);
            ldapConfig.setHostName("192.168.1.2");
            ldapConfig.setBaseDN("dc=lab,dc=net");
            try {
                ldapConfig.save(configFile);
            } catch (IOException e) {
                AlertMaker.showSimpleAlert("Failure","Failed to save configurations. Aborting.");
                Platform.exit();
                System.exit(1);
            }
        }
        return  ldapConfig;

    }

    private void loadDefaultView() {
        loadUser(selectedUser);
        setAllFieldsDisabled(true);
        setMenuDisabled(false);
        setUserSectionDisabled(false);
        setSaveDisabled(true);
        setPasswordDisabled(true);
    }

    private void disableEdit(boolean disabled) {
        setAllFieldsDisabled(disabled);
        setSaveDisabled(disabled);
        setMenuDisabled(!disabled);
    }


    //gets full details for the selected user from datasource
    private void loadUser(User selectedUser) {
        if (selectedUser == null) return;
        DN.setText(selectedUser.getAttribute("DN"));
        allNodes.getTextFields().forEach(textField ->
                textField.setText(selectedUser.getAttribute(textField.getId())));
    }

    // loads a list of users fetched from source with enough information to be able to query for details on select.
    private void loadUserList() {
        ObservableList<User> displayableUsers = FXCollections.observableArrayList();
        data = getDataFromSource();
        try {
            for (int i = 0; i < data.length(); i++) {
                displayableUsers.add(new User(data.getJSONObject(i)));
            }
        } catch (JSONException e) {
            DialogManager.showError("Unable to load user list.", e.getMessage(), true);
        }
        userCount.setText(String.format("Users: %d", data.length()));
        userList.setItems(displayableUsers);

    }

    private JSONArray getDataFromSource() {
        return adConnector.getAllADUsers();
    }

    private void loadSampleData() {
        loadUserList();
    }

    @FXML
    public void editButtonClicked() {
        if (selectedUser == null) return;
        disableEdit(false);
        setPasswordDisabled(true);
    }

    private void setPasswordDisabled(boolean disabled) {
        passwordDropdown.getContent().setMouseTransparent(disabled);
        passwordDropdown.setExpanded(!disabled);
    }

    @FXML
    public void addButtonClicked() {
        disableEdit(false);
        setAllDropdownExpanded(true);
        clearAllTextFields();
        selectedUser = null;
    }

    private void setSaveDisabled(boolean disabled) {
        this.bottomPane.setDisable(disabled);
    }

    private void clearAllTextFields() {
        allNodes.clearTextFields();
    }

    @FXML
    public void cancelButtonClicked() {
        if (!DialogManager.requestConfirmation("All changes will be lost.", "Are you sure?")) return;
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
        if (selectedUser == null) return;
        setMenuDisabled(true);
        setUserSectionDisabled(false);
        setPasswordDisabled(false);
        setSaveDisabled(false);
    }

    @FXML
    public void deleteButtonClicked() {
        if (selectedUser == null) return;
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
        panes.forEach(pane -> pane.getContent().setMouseTransparent(disabled));
    }

    public void configMenuSelected() {
        String configViewFXML = "/fxml/ConfigWindow/ConfigMainView.fxml";
        // Main.loadAsChildWindow(configViewFXML, "Configurations");
    }

}
