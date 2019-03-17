package com.usrmngr.client.models;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Treats a group of FX Nodes as a single node for manipulation.
 */
public class FXNodeContainer {
    private boolean disabled;
    private Node parentNode;
    private HashMap<String, Node> children;

    public FXNodeContainer(Parent node, boolean disabled) {
        this.parentNode = node;
        this.children = getAllNodes(node);
        this.disabled = disabled;
        setDisable(disabled);
    }


    /**
     * Disables parent container
     */
    public void setDisable(boolean disabled) {
        this.disabled = disabled;
        parentNode.setDisable(disabled);
    }

    /**
     * Gets all the children inside a parent node.
     */
    private static HashMap<String, Node> getAllNodes(Parent root) {
        HashMap<String, Node> nodes = new HashMap<>();
        addAllDescendents(root, nodes);
        return nodes;
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
    public HashMap<String, Node> getChildrenOfClass(Class c) {
        HashMap<String, Node> cNodes = new HashMap<>();
        Node value;
        String key;
        for (Map.Entry<String, Node> stringNodeEntry : children.entrySet()) {
            value = stringNodeEntry.getValue();
            key = stringNodeEntry.getKey();
            if (value.getClass() == c)
                cNodes.put(key, value);
        }
        return cNodes;
    }

    public static void main(String[] args) {

    }
}
