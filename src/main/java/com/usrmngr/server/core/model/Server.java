package com.usrmngr.server.core.model;

// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import org.apache.logging.log4j.Level;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static com.usrmngr.Main.LOGGER;
import static com.usrmngr.server.core.model.Constants.DEFAULT_LISTEN_PORT;

// Server class
public class Server implements  Runnable{ //is this the best way to create the thread?
    private boolean run;
    private boolean stopRequestReceived;
    private ServerSocket serverSocket;

    public Server() {
        run = false;
    }
    // Interrupts thread to stop server using the run flag
    public void stop() {
        run = false;
        stopRequestReceived = true;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "Error attempting to close server socket.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Server has started.");
        run = true;
        // running infinite loop for getting client requests
        try {
            serverSocket = new ServerSocket(Integer.parseInt(DEFAULT_LISTEN_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (run) {
            // server is listening on default port
            Socket clientSocket = null;
            LOGGER.log(Level.INFO, "Server started listening on port {}.", DEFAULT_LISTEN_PORT);
            try {
                // socket object to receive incoming client requests
                clientSocket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Client Connected {}", clientSocket.toString());
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                LOGGER.log(Level.INFO, "Thread started for client {}.", clientSocket.toString());

                // create a new thread object
                Thread t = new ClientHandler(clientSocket, dis, dos);
                t.setName("ClientHandler: ".concat(clientSocket.toString()));
                // Invoking the start() method
                t.start();
            }catch (SocketException e){
                if(stopRequestReceived)
                    break;
                LOGGER.log(Level.INFO,"Error communicating with client, retrying...");
            }catch (Exception e){
                LOGGER.log(Level.FATAL, "Fatal Error communicating with client.");
                LOGGER.log(Level.TRACE, e.getStackTrace());
                break;
            }
        }
        LOGGER.log(Level.INFO, "Server has stopped.");
    }

    public boolean isRunning() {
        return run;
    }
}

// ClientHandler class
class ClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                LOGGER.log(Level.INFO, "Communication with client started.");

                // Ask user what he wants
                dos.writeUTF("Hello, ready to serve.\n");

                // receive the answer from client
                received = dis.readUTF();

                if (received.equals("Exit")) {
                    this.s.close();
                    break;
                }

            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "Error encountered when talking to client {}.", s.toString());
                e.printStackTrace();
            }
            LOGGER.log(Level.INFO, "Communication with client stopped.");
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
