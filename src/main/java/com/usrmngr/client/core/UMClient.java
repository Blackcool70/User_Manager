package com.usrmngr.client.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles communication with the UM Server;
 */
public class UMClient {
    // client info
    private final UUID ID;
    // connection info
    private int port;
    private String server;
    private Socket socket;

    // msg transmission
    private BufferedReader br;
    private PrintWriter bos;
    private Logger logger;

    UMClient() {
        this.ID = java.util.UUID.randomUUID();
        this.port = 0;
        setupLogger();
    }

    private void setupLogger() {
        logger = Logger.getLogger("UMClient");
    }

    public String getID() {
        return ID.toString();
    }

    public void configure(String server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Attempts to connect to server
     */
    public void connect() {
        String log = "%s to %s on %s.";
        String result = "Successfully connected";
        try {
            socket = new Socket(server, port);
        } catch (IOException e) {
            result = "Failed to connect.";
        }
        logger.log(Level.INFO, String.format(log, result, server, port));
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    private String getMsg() {
        String msg = "";
        try {
            msg = new Scanner(socket.getInputStream()).nextLine();
            logger.info("Message response received: " + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public String send(String data) {
        String response = null;
        if (!isConnected()) return null;
        try {
            logger.info(String.format("Sending message to %s on %s.", server, port));
            bos = new PrintWriter(socket.getOutputStream(), true);
            bos.println(data);
            logger.info("Message sent.");
            // get a response
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to send message.");
        }
        return response;
    }

    public void disconnect() {
        if (socket == null) return;
        try {
            socket.close();
            logger.info("Disconnected.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to disconnect.");
        }
    }

    private boolean isConfigured() {
        return this.server != null && port != 0;
    }

    @Override
    public String toString() {
        return "ID=" + getID() + "\n" + String.format("configuration={(server=%s),(port=%s)}\n", server, port);

    }

    public static void main(String[] args) {
        UMClient umClient = new UMClient();
        umClient.configure("localhost", 8125);
        umClient.connect();
        umClient.send("Hello world.");
        umClient.getMsg();
        umClient.disconnect();

    }
}
