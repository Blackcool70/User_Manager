package com.usrmngr.common;

import java.io.Serializable;
import java.util.UUID;


/**
 * A request represent an operation that can sent by a client and
 * understood by the server.
 */
public class Request<T> implements Serializable {
    private final RequestType requestType;
    private final UUID ID;
    private T data;

    Request(RequestType requestType, T data) {
        this.requestType = requestType;
        this.ID = UUID.randomUUID();
        this.data = data;
    }

    RequestType getType() {
        return requestType;
    }

    UUID getID() {
        return this.ID;
    }

    @Override
    public String toString() {
        return "Type: " + this.requestType.toString() +
                "\nUUID: " + this.getID().toString() + "\n" +
                "Data:\n" + data.toString();
    }

    public enum RequestType {
        CREATE,
        DELETE,
        UPDATE,
        RESTORE;

        RequestType() {
        }

    }

    public static void main(String[] args) {
        Request request = new Request<>(RequestType.DELETE, "Taco");
        System.out.println(request);
    }
}