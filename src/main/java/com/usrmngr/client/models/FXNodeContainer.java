package com.usrmngr.client.models;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Treats a group of FX Nodes as a single node for manipulation.
 */
public class FXNodeContainer {
    private boolean disabled;
    private ArrayList<Node> children;
    public FXNodeContainer(Parent node, boolean disabled) {
        this.children = getAllNodes(node);
        this.disabled = disabled;
        setDisable(disabled);
    }


    /**
     * Disables all children
     */
    public void setDisable(boolean disabled) {
        this.disabled = disabled;
        for (Node node : children) {
            node.setDisable(disabled);
        }
    }
    /**
     * Gets all the children inside a parent node.
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

    public boolean isDisabled() {
        return disabled;
    }
    public ArrayList<Node> getChildren(){
        return  this.children;
    }

    private void clearTextFields(Node node) {
        ((TextField) node).clear();
    }
    public void clearTextFields() {
        for (Node node : children) {
            if (node instanceof TextField) {
                clearTextFields(node);
            }
        }

    }
    public ArrayList<Node> getAllOfClass(Class c){
        ArrayList<Node> nodes = new ArrayList<>();
        for( Node n : children){
            if( n.getClass() == c ){
               nodes.add(n);
            }
        }
        return  nodes;
    }
    public static void main(String[] args) {

    }
}
