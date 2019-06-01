package com.usrmngr.client.core.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

import static com.usrmngr.server.core.model.Constants.DEFAULT_LISTEN_PORT;

// Client class
public class Client {
    private final UUID clientID = java.util.UUID.randomUUID();

    // to connect to server
    private Socket commSocket;
    private int serverPort;
    private String serverAddress;

    // communication
    private DataInputStream dis;
    private DataOutputStream dos;

    Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }


    public String getClientID() {
        return clientID.toString();
    }

    public boolean isConnected() {
        if (commSocket == null) return false;
        return commSocket.isConnected();

    }

    public  synchronized  void sendMessage(String message) {
        if (!isConnected()) return;
        try {
            dos.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized String getMessage() {
        if (!isConnected()) return null;
        String msg = null;
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public void connect() throws IOException {
        try {
            commSocket = new Socket(InetAddress.getByName(serverAddress), serverPort);
            dis = new DataInputStream(commSocket.getInputStream());
            dos = new DataOutputStream(commSocket.getOutputStream());
        }catch (Exception ignored){}
    }

    private void disconnect() {
        if (isConnected()) {
            try {
                dis.close();
                dos.close();
                commSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", Integer.parseInt(DEFAULT_LISTEN_PORT));
        System.out.println("Client is connected:" + client.isConnected());
        client.connect();
        System.out.println("Client is connected:" + client.isConnected());
        System.out.println(client.getMessage());
        client.sendMessage(client.getClientID());
        System.out.println(client.getMessage());
        client.disconnect();
        System.out.println("Client is connected:" + client.isConnected());
    }
}
