package com.usrmngr.client.models;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Treats a group of FX Nodes as a single node for manipulation.
 */
public class FXNodeContainer{
    private boolean disabled;
    private HashMap<String, Node> children;
    public FXNodeContainer() {
        children = new HashMap<>();
        disabled = false;
    }

    public FXNodeContainer(Parent node, boolean disabled) {
        children = new HashMap<>();
        addNodesFromParent(node);
        this.disabled = disabled;
        setDisable(disabled);
    }


    /**
     * Disables all nodes;
     */
    public void setDisable(boolean disabled) {
        Iterator it = children.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ((Node) pair.getValue()).setDisable(disabled);
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /**
     * Gets all the children inside a parent node.
     */
    private static HashMap<String, Node> getAllNodes(Parent root) {
        HashMap<String, Node> nodes = new HashMap<>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    public void addNodesFromParent(Parent node) {
        children.putAll(getAllNodes(node));
    }

    public void addNodes(ArrayList<Node> nodes) {
        for (Node node : nodes)
            addNode(node);
    }

    private void addNode(Node node) {
        children.putIfAbsent(node.getId(), node);
    }

    /**
     * Gets all the descendents of each parent node.
     */
    private static void addAllDescendents(Parent parent, HashMap<String, Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.put(node.getId(), node);
            if (node instanceof Parent)
                addAllDescendents((Parent) node, nodes);
        }
    }

    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Returns the child with id if it exists.
     *
     * @param id
     * @return
     */
    public Node getChild(String id) {
        return children.getOrDefault(id, null);
    }

    private ArrayList<Node> getChildren() {
        Iterator it = children.entrySet().iterator();
        ArrayList<Node> nodes = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            nodes.add((Node) pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return nodes;
    }


    /**
     * Returns the id and the node of class c as a key pair
     *
     * @param c the wanted class
     * @return the found values
     */
    private ArrayList<Node> getChildrenOfClass(Class c) {
        ArrayList<Node> cNodes = new ArrayList<>();
        Node value;
        for (Map.Entry<String, Node> stringNodeEntry : children.entrySet()) {
            value = stringNodeEntry.getValue();
            if (value.getClass() == c)
                cNodes.add(value);
        }
        return cNodes;
    }
    public void  clearTextFields(){
        ArrayList<Node> textFields = getChildrenOfClass(TextField.class);
        for(Node node: textFields){
            ((TextField) node).clear();
        }
    }

    /**
     * Sets the text on the provided name.If there is no such field nothing is set.
     *
     * @param fieldName id of the field
     * @param text      the text to set on the field
     */
    public void setTextOnTextField(String fieldName, String text) {
        Node node = getNode(fieldName);
        if (node instanceof TextField)
            ((TextField) node).setText(text);
    }

    private Node getNode(String fieldName) {
        return children.get(fieldName);
    }

    public static void main(String[] args) {

    }

}
