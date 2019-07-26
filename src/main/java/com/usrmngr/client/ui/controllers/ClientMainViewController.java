package com.usrmngr.client.ui.controllers;

import com.usrmngr.client.core.model.ADUser;
import com.usrmngr.client.core.model.Connectors.ADConnector;
import com.usrmngr.client.core.model.Connectors.Configuration;
import com.usrmngr.client.core.model.FXNodeContainer;
import com.usrmngr.util.Alert.AlertMaker;
import com.usrmngr.util.Dialog.DialogMaker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
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


    //GUI items
    @FXML
    public VBox leftPane, centerPane;
    public GridPane bottomPane;
    @FXML
    TitledPane basicInfoDropdown, contactInfoDropdown, passwordDropdown;
    ContextMenu contextMenu = new ContextMenu();
    @FXML
    public Label userCount, DN;
    @FXML
    public ListView<ADUser> userList;
    @FXML
    public PasswordField passwordField, passwordConfirmationField;
    @FXML
    MenuItem preferencesMenu, configurationsMenu;

    // other data structures
    private FXNodeContainer allNodes; //todo find better way to get a hold of all the textfields programmatically
    private ArrayList<TitledPane> panes;
    private ADConnector adConnector;
    private Configuration config;

    private ADUser selectedUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // initAdConnection();
        initController();
       // loadUserList();
        loadDefaultView();
        contextMenu();
    }
    private void contextMenu(){
        MenuItem delete_button = new MenuItem("Delete User");
        delete_button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                deleteButtonClicked();
            }
        });
        MenuItem edit_button = new MenuItem("Edit User");
        edit_button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                editButtonClicked();
            }
        });
        MenuItem reset_pass_button = new MenuItem("Password Reset");
        reset_pass_button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                passwordResetButtonClicked();
            }
        });
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(delete_button, edit_button, reset_pass_button);

        // When user right-click on the left pane
        userList.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(userList, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private void initAdConnection() {
        loadConfigs();
        connectToAD(); // will connect or quit
        authenticateToAD();
    }

    private void authenticateToAD() {
        String un = config.getValue("authDN");
        String pw = config.getValue("pw");
        //credentials = getCredentials();
        adConnector.authenticate(un, pw);//will clean up
    }

    private void loadConfigs() {
        this.config = new Configuration();
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
                selectedUser = adConnector.getADUser(selectedUser.getCN());
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
        setDropdownExpanded(contactInfoDropdown, true);
    }

    private void disableEdit(boolean disabled) {
        setAllFieldsDisabled(disabled);
        setSaveDisabled(disabled);
        setMenuDisabled(!disabled);
    }


    //gets full details for the selected user from datasource
    private void loadUser(ADUser selectedADObject) {
        if (selectedADObject != null) {
            DN.setText(selectedADObject.getDN());
            allNodes.getTextFields().forEach(textField ->
                    textField.setText(selectedADObject.getAttribute(textField.getId())));
        }
    }

    // loads a list of users fetched from source with enough information to be able to query for details on select.
    private void loadUserList() {
        ObservableList<ADUser> displayableADObjects = FXCollections.observableArrayList();
        // data
        JSONArray adUserImportData = getDataFromSource();
        try {
            for (int i = 0; i < adUserImportData.length(); i++) {
                displayableADObjects.add(new ADUser(adUserImportData.getJSONObject(i)));
            }
        } catch (JSONException e) {
            AlertMaker.showErrorMessage("Fatal Error", e.getMessage());
            Platform.exit();
            System.exit(1);
        }
        userCount.setText(String.format("Users: %d", adUserImportData.length()));
        userList.setItems(displayableADObjects);

    }

    private JSONArray getDataFromSource() {
        adConnector.authenticate();
        return adConnector.getAllADUsers("displayName", "cn");
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
        //todo implement the save properly
        AlertMaker.showSimpleAlert("Save", "Save successful.");
        loadDefaultView();
        loadUser(selectedUser);
    }

    @FXML
    public void passwordResetButtonClicked() {
       if (selectedUser == null) return;
        setDropdownExpanded(contactInfoDropdown,false);
        setMenuDisabled(true);
        setUserSectionDisabled(false);
        setPasswordDisabled(false);
        setSaveDisabled(false);
    }

    @FXML
    public void deleteButtonClicked() {
        if (selectedUser == null) return;

        if (DialogMaker.showConfirmationDialog("You are about to DELETE a user!")){
                /* TODO: If the user wants to delete, I think it's better, or rather, makes sense to ask
                *        if they are okay with deleting right there and then, if they click "OK", then begin the
                *       deleting algorithm and treat it as a save button, then load the default view. Otherwise,
                *       do nothing.
                *  Additional note: If there is a way to pop up or something similar that a user was successfully
                *                   deleted, that would be also a good thing.*/
                loadDefaultView();

        }

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

    /*This sets the expansion of a specific pane rather than all.*/
    private void setDropdownExpanded(TitledPane pane, boolean expanded){
        pane.setExpanded((expanded));
    }

    private void setAllFieldsDisabled(boolean disabled) {
        panes.forEach(pane -> pane.getContent().setMouseTransparent(disabled));
    }

    public void configMenuSelected() {
        String configViewFXML = "/client/fxml/ADConfigView.fxml";
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
