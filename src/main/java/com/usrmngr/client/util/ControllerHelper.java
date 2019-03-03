package com.usrmngr.client.util;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.ArrayList;

public class ControllerHelper {
    /**
     * Gets all the nodes inside a parent node.
     */
    public static ArrayList<Node> getAllNodes(Parent root) {
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

    public static void main(String[] args) {

    }
}
