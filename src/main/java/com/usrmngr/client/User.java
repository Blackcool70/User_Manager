package com.usrmngr.client;

public class User {
    private String display;
    private final int id;

    public User(String display, int id){
        this.display = display;
        this.id = id;
    }

    @Override
    public String toString() {
        return display;
    }
    public static void main(String[] args) {

    }
}
