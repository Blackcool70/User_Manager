package com.usrmngr.client;

public class User {
    private String display;
    private final String id;

    public User(String display, String id){
        this.display = display;
        this.id = id;
    }

    @Override
    public String toString() {
        return display;
    }
    public static void main(String[] args) {

    }

    public String getId() {
        return id;
    }
}
