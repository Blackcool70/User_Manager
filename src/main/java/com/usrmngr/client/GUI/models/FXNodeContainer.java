package com.usrmngr.client.GUI.models;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.util.*;

/**
 * Treats a group of FX Nodes as a single node for manipulation.
 */
public class FXNodeContainer {
    private boolean disabled;
    private HashMap<String, Node> items;
    private String parent;

    public FXNodeContainer() {
        items = new HashMap<>();
        parent = null;
        this.disabled = false;
    }

    public FXNodeContainer(Parent item, boolean disabled) {
        if (item == null) throw new NullPointerException("Parent is null");
        items = new HashMap<>();
        parent = item.getId();
        addNodesFromParent(item);
        this.disabled = disabled;
        setDisable(disabled);
    }

    /**
     * Gets all the items inside a parent node.
     */
    private static HashMap<String, Node> getAllNodes(Parent root) {
        HashMap<String, Node> items = new HashMap<>();
        addAllDescendents(root, items);
        return items;
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

    public static void main(String[] args) {


    }

    public void addItem(Parent item) {
        addNodesFromParent(item);
    }

    /**
     * Disables all nodes;
     */
    private void setDisable(boolean disabled) {
        for (Map.Entry<String, Node> stringNodeEntry : items.entrySet()) {
            ((Node) ((Map.Entry) stringNodeEntry).getValue()).setDisable(disabled);
            //   it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private void addNodesFromParent(Parent node) {
        items.putAll(getAllNodes(node));
    }

    public void addItems(ArrayList<Node> items) {
        for (Node node : items)
            addItem(node);
    }

    private void addItem(Node node) {
        items.putIfAbsent(node.getId(), node);
    }

    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Returns the child with id if it exists.
     *
     * @param id the unique id for the item
     * @return the node or null if not found
     */
    public Node getItem(String id) {
        return items.getOrDefault(id, null);
    }

    private ArrayList<Node> getItems() {
        Iterator<Map.Entry<String, Node>> it = items.entrySet().iterator();
        ArrayList<Node> nodes = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry<String, Node> pair = it.next();
            nodes.add(pair.getValue());
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
    private ArrayList<Node> getChildrenOfClass(Class<TextField> c) {
        ArrayList<Node> cNodes = new ArrayList<>();
        Node value;
        for (Map.Entry<String, Node> stringNodeEntry : items.entrySet()) {
            value = stringNodeEntry.getValue();
            if (value.getClass() == c)
                cNodes.add(value);
        }
        return cNodes;
    }

    public void clearTextFields() {
        ArrayList<Node> textFields = getChildrenOfClass(TextField.class);
        for (Node node : textFields) {
            ((TextField) node).clear();
        }
    }

    public ArrayList<TextField> getTextFields() {
        ArrayList<Node> rawNodes = getChildrenOfClass(TextField.class);
        ArrayList<TextField> textFields = new ArrayList<>();
        for (Node node : rawNodes) {
            textFields.add(((TextField) node));
        }
        return textFields;
    }

    /**
     * Sets the text on the provided field name if it exists.
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
        return items.getOrDefault(fieldName, null);
    }

    /**
     * Returns the parent item if it exists.
     *
     * @return the parent item
     */
    public Node getParent() {
        return parent != null ? getItem(parent) : null;
    }
}
