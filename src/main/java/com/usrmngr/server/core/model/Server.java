package com.usrmngr.server.core.model;

// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import com.usrmngr.Main;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static com.usrmngr.server.core.model.Constants.DEFAULT_LISTEN_PORT;

// Server class
public class Server implements Runnable { //is this the best way to create the thread?
    private boolean running;
    private boolean stopRequestReceived;
    private ServerSocket serverSocket;
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    public Server() {
        running = false;
    }

    // Interrupts thread to stop server using the running flag
    public void stop() {
        running = false;
        stopRequestReceived = true;
        if (serverSocket == null) return;
        try {
            serverSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Error attempting to close server socket.");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Server has started.");
        running = true;
        // running infinite loop for getting client requests
        try {
            serverSocket = new ServerSocket(Integer.parseInt(DEFAULT_LISTEN_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.log(Level.INFO, "Server started listening on port {}.", DEFAULT_LISTEN_PORT);
        while (running) {
            // server is listening on default port
            Socket clientSocket = null;
            try {
                // socket object to receive incoming client requests
                clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Client Connected {}", clientSocket.getLocalPort());
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                LOGGER.log(Level.INFO, "Thread started for client {}.", clientSocket.getLocalPort());

                // create a new thread object
                Thread t = new ClientHandler(clientSocket, dis, dos);
                t.setName("ClientHandler:".concat(String.valueOf(clientSocket.getLocalPort())));
                // Invoking the start() method
                t.start();
            } catch (SocketException e) {
                if (stopRequestReceived) {
                    stopRequestReceived = false;
                    break;
                }
                LOGGER.log(Level.INFO, "Error communicating with client, retrying...");
            } catch (Exception e) {
                LOGGER.log(Level.FATAL, "Fatal Error communicating with client.");
                LOGGER.log(Level.TRACE, e.getStackTrace());
                break;
            }
        }
        LOGGER.log(Level.INFO, "Server has stopped.");
    }

    public boolean isRunning() {
        return running;
    }
}

// ClientHandler class
class ClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    public static final Logger LOGGER = LogManager.getLogger(Main.class.getName());

    // Constructor
    public ClientHandler(Socket clientSocket, DataInputStream dis, DataOutputStream dos) {
        this.s = clientSocket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        LOGGER.log(Level.INFO, "Communication with client started.");
        while (true) {
            try {

                // Ask user what he wants
                dos.writeUTF("Hello, ready to serve.\n");

                // receive the answer from client
                received = dis.readUTF();

                dos.writeUTF(("How can I help you " + received));

            } catch (EOFException | SocketException e) {
                LOGGER.log(Level.INFO, "Client disconnected: {}", e.getMessage());
                break;
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "Error encountered when talking to client {}.", s.getLocalPort());
                e.printStackTrace();
                break;
            }
        }
        LOGGER.log(Level.INFO, "Communication with client stopped.");

        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        Thread thread = new Thread(server);
        thread.setName("Server");
        thread.start();

    }
}
