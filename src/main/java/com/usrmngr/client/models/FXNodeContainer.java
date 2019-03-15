package com.usrmngr.client.models;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Treats a group of FX Nodes as a single node for manipulation.
 */
public class FXNodeContainer {
    private boolean disabled;
    private ArrayList<Node> nodes;

    public FXNodeContainer(Parent node, boolean disabled) {
        this.nodes = getAllNodes(node);
        this.disabled = disabled;
        setDisable(disabled);
    }



    public void setDisable(boolean disabled) {
        this.disabled = disabled;
        toggleWidgets();
    }
    /**
     * Gets all the nodes inside a parent node.
     */
    private static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    /**
     * Gets all the descendents of each parent node.
     */
    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                addAllDescendents((Parent) node, nodes);
        }
    }
    private void toggleWidgets() {
        for (Node node : nodes) {
            if (!(node instanceof Label)) {
                node.setDisable(disabled);
            }
        }
    }

    public boolean isDisabled() {
        return disabled;
    }
    public ArrayList<Node> getNodes(){
        return  this.nodes;
    }

    private void clearTextFields(Node node) {
        ((TextField) node).clear();
    }
    public void clearTextFields() {
        for (Node node : nodes) {
            if (node instanceof TextField) {
                clearTextFields(node);
            }
        }

    }
    public static void main(String[] args) {

    }
}
