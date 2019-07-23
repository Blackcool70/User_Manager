package com.usrmngr.client.ui.controllers;

import com.usrmngr.client.core.model.Connectors.ADConnector;
import com.usrmngr.client.core.model.Connectors.LDAPConfig;
import com.usrmngr.client.core.model.FXNodeContainer;
import com.usrmngr.client.core.model.User;
import com.usrmngr.util.Alert.AlertMaker;
import com.usrmngr.util.Dialog.DialogMaker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.usrmngr.Main.APP_CONFIG_PATH;

public class ClientMainViewController implements Initializable {

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
    private LDAPConfig config;
    private Pair<String, String> credentials;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  initAdConnection();
        initController();
        //loadUserList();
        loadDefaultView();
    }

    private void initAdConnection() {
        loadConfigs();
        connectToAD(); // will connect or quit
        authenticateToAD();
    }

    private void authenticateToAD() {
         credentials = new Pair<>("cn=Administrator,ou=users,ou=company,dc=lab,dc=net","J3cs4nb!");
        //credentials = getCredentials();
        adConnector.authenticate(credentials.getKey(), credentials.getValue());//will clean up
    }

    private void loadConfigs() {
        this.config = new LDAPConfig();
        try {
            this.config.load(APP_CONFIG_PATH);
        } catch (IOException e) {
            configMenuSelected();
            try {
                this.config.load(APP_CONFIG_PATH);
            } catch (IOException ex) {
                AlertMaker.showSimpleAlert("Config", "Failed to load config. Aborting.");
                Platform.exit();
                System.exit(1);
            }
        }
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
                selectedUser = new User(adConnector.getADUser(selectedUser.getAttribute("cn")));
                loadUser(selectedUser);
            }
        });
    }

    private void connectToAD() {
        adConnector = new ADConnector(this.config);
        adConnector.connect();
        while (!adConnector.isConnected()) {
            if (DialogMaker.showConfirmationDialog("Unable to connect, check configuration.")) {
                configMenuSelected();
                adConnector.connect();
            } else {
                Platform.exit();
                System.exit(0);
            }
        }
    }

    private Pair<String, String> getCredentials() {
        boolean quit;
        Optional<Pair<String, String>> input;
        while (true) {
            input = DialogMaker.showLoginDialog();
            if (input.isEmpty()) {
                quit = DialogMaker.showConfirmationDialog("Not providing credentials will terminate the application.");
                if (quit) {
                    Platform.exit();
                    System.exit(0);
                }
            } else {
                Pair<String, String> candaceCred = input.get();
                adConnector.connect();
                adConnector.authenticate(candaceCred.getKey(), candaceCred.getValue());
                if (adConnector.isAuthenticated()) {
                    break;
                } else {
                    AlertMaker.showSimpleAlert("Alert", "Invalid Credentials, try again.");
                }
            }
        }
        return input.get();
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
            AlertMaker.showErrorMessage("Fatal Error",e.getMessage());
            Platform.exit();
            System.exit(1);
        }
        userCount.setText(String.format("Users: %d", data.length()));
        userList.setItems(displayableUsers);

    }

    private JSONArray getDataFromSource() {
        adConnector.authenticate(credentials.getKey(), credentials.getValue());
        return adConnector.getAllADUsers("displayName","cn");
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
        DN.setText("");
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
        if (DialogMaker.showConfirmationDialog("Changes will be lost.")) {
            clearAllTextFields();
            loadDefaultView();
        }

    }

    @FXML
    public void saveButtonClicked() {
        setMenuDisabled(false);
        //do the save
        AlertMaker.showSimpleAlert("Save","Save successful.");
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
        String configViewFXML = "/client/fxml/ConfigView.fxml";
        String windowTitle = "Configurations";
        try {
            openChildWindow(windowTitle, configViewFXML);
        } catch (IOException e) {
            AlertMaker.showSimpleAlert(windowTitle, "Unable to open " + windowTitle + " window.");
            e.printStackTrace();
        }
    }

    public void openChildWindow(String title, String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene config = new Scene(root);
        Stage configWindow = new Stage();
        // prevents the parent window from being modified before configs are closed.
        configWindow.initModality(Modality.WINDOW_MODAL);
        configWindow.initOwner(root.getScene().getWindow());
        configWindow.setTitle(title);
        configWindow.setScene(config);
        configWindow.setResizable(false);
        configWindow.showAndWait();
    }


}
