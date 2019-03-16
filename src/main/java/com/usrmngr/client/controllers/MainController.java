package com.usrmngr.client.controllers;

import com.usrmngr.client.models.FXNodeContainer;
import com.usrmngr.client.models.User;
import com.usrmngr.client.util.DataManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // private final String DATA_PATH = "C:\\Users\\jecsa\\IdeaProjects\\User_Manager\\src\\main\\resources\\com\\usrmngr\\client\\samples\\data.json";
    private final String DATA_PATH = "src/main/resources/samples/data.json";
    private JSONArray data;

    @FXML
    public VBox controlsVBox;
    @FXML
    public GridPane userAreaPane, infoAreaPane, passwordAreaPane, licenseAreaPane, saveAreaPane;
    @FXML
    public TitledPane infoDropDown, passwordDropDown, licenseDropDown;

    private User currentUser;
    private FXNodeContainer controlArea, userArea, infoArea, licenseArea, passwordArea, saveArea;
    public ListView<User> userList;
    public TextField displayNameField, firstNameField, lastNameField, emailField, userPhoneField;
    public Label usrID;
    public Label usrCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controlArea = new FXNodeContainer(controlsVBox, false);
        userArea = new FXNodeContainer(userAreaPane, true);
        infoArea = new FXNodeContainer(infoAreaPane, true);
        licenseArea = new FXNodeContainer(licenseAreaPane, true);
        passwordArea = new FXNodeContainer(passwordAreaPane, true);
        saveArea = new FXNodeContainer(saveAreaPane, false);
        allAreasExpanded(false);

        //action for when a user gets double clicked on the list
        userList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
            ) {
                currentUser = userList.getSelectionModel().getSelectedItem();
                loadUser(currentUser);
            }
        });

        loadSampleData();
    }

    private void loadUser(@NotNull User selectedUser) {
        try {
            JSONObject user = data.getJSONObject(Integer.parseInt(selectedUser.getId()) - 1);
            displayNameField.setText(selectedUser.toString());
            firstNameField.setText(user.getString("first_name"));
            lastNameField.setText(user.getString("last_name"));
            emailField.setText(user.getString("email"));
            usrID.setText(selectedUser.getId());
            userPhoneField.setText(user.getString("phone"));
        } catch (JSONException e) {
            displayError("Unable to load user, try again.");
        }

    }

    private void loadUserList() {
        ObservableList<User> displayableUsers = FXCollections.observableArrayList();
        try {
            data = new JSONArray(DataManager.readFile(DATA_PATH));
            User user;
            for (int i = 0; i < data.length(); i++) {
                user = new User(data.getJSONObject(i));
                displayableUsers.add(user);
            }
            usrCount.setText(String.format("Users: %d", data.length()));
            userList.setItems(displayableUsers);
        } catch (JSONException e) {
            displayError("Unable to load user list!");
            Platform.exit();
            System.exit(0);
        }

    }

    private void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }


    private boolean requestConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(message);
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;

    }

    private void disableAllAreas(boolean disable) {
        controlArea.setDisable(disable);
        userArea.setDisable(disable);
        infoArea.setDisable(disable);
        licenseArea.setDisable(disable);
        passwordArea.setDisable(disable);
        saveArea.setDisable(disable);
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
        controlArea.setDisable(true);
        userArea.setDisable(false);
        infoArea.setDisable(false);
        licenseArea.setDisable(false);
        saveArea.setDisable(false);
    }

    @FXML
    public void addButtonClicked() {
        controlArea.setDisable(true);
//            clearFields();
        disableAllAreas(false);
        allAreasExpanded(true);
    }

    @FXML
    public void cancelButtonClicked() {
        disableAllAreas(true);
        controlArea.setDisable(false);
        allAreasExpanded(false);
//            clearFields();
    }

    @FXML
    public void saveButtonClicked() {
        disableAllAreas(true);
        allAreasExpanded(false);
        controlArea.setDisable(false);
    }

    @FXML
    public void passwordResetButtonClicked() {
        passwordDropDown.setExpanded(true);
        controlArea.setDisable(true);
        passwordArea.setDisable(false);
        saveArea.setDisable(false);
    }

    @FXML
    public void deleteButtonClicked() {
        if (currentUser != null) {
            if (requestConfirmation("User will be deleted.")) {
                System.out.println("User deleted!");
            }
        }
    }

    /**
     * Sets the next on the given field.If there is no such field nothing is set.
     *
     * @param fieldName id of the field
     * @param text      the text to set on the field
     */
    private void setTextOnField(String fieldName, String text) {

    }

}
