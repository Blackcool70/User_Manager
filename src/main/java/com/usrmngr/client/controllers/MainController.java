package com.usrmngr.client.controllers;

import com.usrmngr.client.models.DisplayArea;
import com.usrmngr.client.models.User;
import com.usrmngr.client.util.DataManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {

   // private final String DATA_PATH = "C:\\Users\\jecsa\\IdeaProjects\\User_Manager\\src\\main\\resources\\com\\usrmngr\\client\\samples\\data.json";
    private  final String DATA_PATH = "src/main/resources/com/usrmngr/client/samples/data.json";
    private  JSONArray data;

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
    public ListView<User> userListView;
    public Label usrID;
    public Label usrCount;


    public VBox controlsVBox;
    public GridPane userAreaPane;
    public GridPane infoAreaPane;
    public GridPane passwordAreaPane;
    public GridPane licenseAreaPane;
    public GridPane saveAreaPane;


    private DisplayArea controlArea;
    private DisplayArea userArea;
    private DisplayArea infoArea;
    private DisplayArea licenseArea;
    private DisplayArea passwordArea;
    private DisplayArea saveArea;

    public TitledPane infoDropDown;
    public TitledPane passwordDropDown;
    public TitledPane licenseDropDown;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controlArea = new DisplayArea(controlsVBox,false);
        userArea = new DisplayArea(userAreaPane,true);
        infoArea = new DisplayArea(infoAreaPane,true);
        licenseArea = new DisplayArea(licenseAreaPane,true);
        passwordArea = new DisplayArea(passwordAreaPane,true);
        saveArea  = new DisplayArea(saveAreaPane,false);
        allAreasExpanded(false);

        userListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
            ) {
                User selectedUser = userListView.getSelectionModel().getSelectedItem();
                loadUser(selectedUser);
            }
        });

        editButton.setOnMouseClicked(event -> {
            controlArea.setDisable(true);
            userArea.setDisable(false);
            infoArea.setDisable(false);
            licenseArea.setDisable(false);
            saveArea.setDisable(false);
        });
        addButton.setOnMouseClicked(event -> {
            controlArea.setDisable(true);
//            clearFields();
            disableAllAreas(false);
            allAreasExpanded(true);
        });
        cancelButton.setOnMouseClicked(event -> {
            disableAllAreas(true);
            controlArea.setDisable(false);
            allAreasExpanded(false);
//            clearFields();
            addButton.requestFocus();
        });
        saveButton.setOnMouseClicked(event -> {
            disableAllAreas(true);
            allAreasExpanded(false);
            controlArea.setDisable(false);
        });
        passwordResetButton.setOnMouseClicked(event -> {
            passwordDropDown.setExpanded(true);
            controlArea.setDisable(true);
            passwordArea.setDisable(false);
            saveArea.setDisable(false);

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
            usrDisplayName.setText(selectedUser.toString());
            usrFName.setText(user.getString("first_name"));
            usrLName.setText(user.getString("last_name"));
            usrEmail.setText(user.getString("email"));
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

    private void loadUserList(){
        ObservableList<User> displayableUsers = FXCollections.observableArrayList();
        try {
            data = new JSONArray(DataManager.readFile(DATA_PATH));
            User user;
            for (int i = 0; i < data.length(); i++) {
                user = new User(data.getJSONObject(i));
                displayableUsers.add(user);
            }
            usrCount.setText(String.format("Users: %d",data.length()));
            userListView.setItems(displayableUsers);
        } catch (JSONException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to Load Users");
            alert.setContentText("An Error Occurred loading users.");
            alert.showAndWait();
            Platform.exit();
            System.exit(0);
        }

    }



    private boolean getConfirm(String message) {
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
    private  void allAreasExpanded(boolean expanded){

        infoDropDown.setExpanded(expanded);
        passwordDropDown.setExpanded(expanded);
        licenseDropDown.setExpanded(expanded);
    }

}
