package com.usrmngr.client.controllers;

import com.usrmngr.client.Main;
import com.usrmngr.client.models.FXNodeContainer;
import com.usrmngr.client.util.AlertManager;
import com.usrmngr.client.util.Constants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.usrmngr.client.util.Constants.APP_NAME;

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
    private String defaultConfig, rootTreeItem;
    private static FXNodeContainer currentConfigNodes;

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
        rootTreeItem = "Configurations";
        configSelectionTree.setRoot(new TreeItem<>(rootTreeItem));

        // todo find away to move logic related to to the adconfig controller to its own controller
        addConfiguration("Configure Active Directory", "/fxml/ConfigActiveDirectoryView.fxml");
        defaultConfig = availableConfigurations.keySet().iterator().next();
        switchToConfiguration(defaultConfig);

        //try to load properties
        loadProperties();

        configSelectionTree.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2
            ) {
                String selectedConfig = configSelectionTree.getSelectionModel().getSelectedItem().getValue();
                if (!selectedConfig.equals(rootTreeItem)) {
                    switchToConfiguration(selectedConfig);
                }
            }
        });
        cancelButton.setOnMouseClicked(event -> {
            if (AlertManager.requestConfirmation("Unsaved Changes will Be lost!")) {
                Node source = (Node) event.getSource();
                Window thisStage = source.getScene().getWindow();
                thisStage.fireEvent(
                        new WindowEvent(
                                thisStage,
                                WindowEvent.WINDOW_CLOSE_REQUEST
                        )
                );
            }
        });
    }

    private void saveConfigFile() {
        String dataPath = getUserDataDirectory().concat(Constants.CONFIG_FILE_NAME);
        File configFile = new File(dataPath);
        if (!configFile.exists()) {
            try {
                configFile.getParentFile().mkdir();
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<TextField> textFields = currentConfigNodes.getTextFields();
        for (TextField field : textFields) {
            Main.properties.put(field.getId(), field.getText().toLowerCase().trim());
        }
        try {
            Main.properties.store(new FileOutputStream(configFile), "test");
        } catch (IOException e) {
            catastrophicErrorOccurred(e, "Unable to Save Configurations. Try again.");
        }
        AlertManager.showInfo(String.format("Configurations created: \n%s.\n", dataPath));
    }

    private static void loadProperties() {
        File configFile = new File(getUserDataDirectory().concat(Constants.CONFIG_FILE_NAME));
        if (configFile.exists()) {
            try {
                Main.properties.load(new FileInputStream(configFile));
                for (Map.Entry<Object, Object> e : Main.properties.entrySet()) {
                    ((TextField) currentConfigNodes.getItem((String) e.getKey())).setText((String) e.getValue());
                }

            } catch (IOException e) {
                catastrophicErrorOccurred(e, "Unable to load Configurations. Try again.");
            }
        }
    }


    @FXML
    public void savedButtonClicked(){
        ArrayList<TextField> textFields = currentConfigNodes.getTextFields();
        for (TextField field : textFields) {
            if (field.getText().equals("")) {
                AlertManager.showError("Fields cannot be left empty.");
                return;
            }
        }
        saveConfigFile();
        AlertManager.showInfo("Saved!");
    }

    public static String getUserDataDirectory() {
        return System.getProperty("user.home") + File.separator + ".".concat(APP_NAME.toLowerCase()) + File.separator;
    }

    private static void catastrophicErrorOccurred(Exception e, String message) {
        AlertManager.showError(message);
        e.printStackTrace();
        Platform.exit();
        System.exit(1);

    }
}

