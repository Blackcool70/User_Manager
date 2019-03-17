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
    //// TODO: 3/17/2019 Does not all ways work
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
    private HashMap<String, Node> getChildrenOfClass(Class c) {
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
