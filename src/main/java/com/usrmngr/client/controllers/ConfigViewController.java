package com.usrmngr.client.controllers;

import com.usrmngr.client.Main;
import com.usrmngr.client.util.AlertManager;
import com.usrmngr.client.util.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigViewController implements Initializable {
    @FXML
    public Button save;
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
        mainDisplayPane.getChildren().add(availableConfigurations.getOrDefault(configId, availableConfigurations.get(defaultConfig)));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableConfigurations = new LinkedHashMap<>();
        rootTreeItem = "Configurations";
        configSelectionTree.setRoot(new TreeItem<>(rootTreeItem));
        addConfiguration("Configure Active Directory", "/fxml/ConfigActiveDirectoryView.fxml");
        defaultConfig = availableConfigurations.keySet().iterator().next();
        switchToConfiguration(defaultConfig);

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

    @FXML
    public void savedButtonClicked() {
        System.out.println("Saved clicked");

    }
}
