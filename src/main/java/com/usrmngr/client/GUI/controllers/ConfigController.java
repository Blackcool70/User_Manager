package com.usrmngr.client.GUI.controllers;

import com.usrmngr.client.GUI.models.FXDialogs.DialogManager;
import com.usrmngr.client.GUI.models.FXNodeContainer;
import com.usrmngr.client.GUI.util.DataManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ConfigController implements Initializable {
    @FXML
    public Button saveButton;
    @FXML
    public Button cancelButton;
    @FXML
    // The list of available configurations
    public TreeView<String> configSelectionTree;
    // The area where the configurations are loaded
    @FXML
    public AnchorPane mainDisplayPane;
    // Stores all the configuration windows.
    private LinkedHashMap<String, Node> availableConfigurations;
    private String defaultConfig;
    private static FXNodeContainer currentConfigNodes;
    private Properties properties;

    /**
     * Adds and entry to the configuration menu tree
     *
     * @param configId a unique id to display on the tree
     * @param fxmlPath the path to the fxml to load when said id is selected
     */
    private void addConfiguration(final String configId, String fxmlPath) {
        try {
            Node option = FXMLLoader.load(getClass().getResource(fxmlPath));
            configSelectionTree.getRoot().getChildren().add(new TreeItem<>(configId));
            availableConfigurations.put(configId, option);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the configuration associated with the provided id.
     * If the provided id is not found then the first entry will be used.
     *
     * @param configId a unique id to display on the tree
     */
    private void switchToConfiguration(String configId) {
        mainDisplayPane.getChildren().clear();
        Node parent = availableConfigurations.getOrDefault(configId, availableConfigurations.get(defaultConfig));
        mainDisplayPane.getChildren().add(parent);
        currentConfigNodes = new FXNodeContainer((Parent) parent, false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableConfigurations = new LinkedHashMap<>();
        String rootTreeItem = "Configurations";
        configSelectionTree.setRoot(new TreeItem<>(rootTreeItem));

        // todo find away to move logic related to to the adconfig controller to its own controller
        addConfiguration("Configure Active Directory", "/fxml/ConfigWindow/ConfigADView.fxml");
        defaultConfig = availableConfigurations.keySet().iterator().next();
        switchToConfiguration(defaultConfig);

        //try to load properties
        saveButton.setOnMouseClicked(event -> savedButtonClicked());
        cancelButton.setOnMouseClicked(event -> cancelButtonClicked());
    }

    private void cancelButtonClicked() {
        if (!propertiesNotEmpty(properties)) {
             boolean ans = DialogManager.requestConfirmation(
                      "Invalid settings detected. Application will terminate.","");
             if(ans){
                 Platform.exit();
                 System.exit(0);
             }
        }else{
            Window thisStage = this.cancelButton.getScene().getWindow();
            thisStage.fireEvent(new WindowEvent(thisStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }


    @FXML
    public void savedButtonClicked() {
        ArrayList<TextField> textFields = currentConfigNodes.getTextFields();
        Properties properties = new Properties();
        for (TextField field : textFields) {
            if (field.getText().equals("")) {
                DialogManager.showError("Fields cannot be left empty.","", false);
                return;
            }
            properties.put(field.getId(), field.getText());
        }
        DataManager.saveConfigFile(properties);
        DialogManager.showInfo("Saved!");
    }



    /**
     * Check to make sure there are no blank properties,
     *
     * @param properties
     * @return true if not empty false if empty
     */
    private boolean propertiesNotEmpty(Properties properties) {
        if (properties == null || properties.isEmpty()) return false;
        for (Map.Entry<Object, Object> b : properties.entrySet()) {
            System.out.println(b.toString());
            String value = (String) b.getValue();
            if (value.equals("")) return false;
        }
        return true;
    }

}

