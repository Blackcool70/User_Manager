package com.usrmngr.client.core;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

class UMClientThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private UUID id = UUID.randomUUID();

    public UMClientThread(InetAddress addr, int port) {
        try {
            socket = new Socket(addr, port);
        } catch (IOException e) {
            System.err.println("Socket failed : " + e);
            return;
        }

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Enable auto-flush:
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
            start();
        } catch (IOException e) {
            System.out.println("Socket Error [id#" + id + "]: " + e);
            try {
                socket.close();
            } catch (IOException e2) {
                System.err.println("Socket not closed [id#" + id + "]");
            }
        }
    }


    public void run() {
        System.out.println("Start Client " + id + " ");
        try {
            String msg;
            while( (msg = in.readLine() )!= null){
                System.out.println(msg);
            }
            System.out.println(msg);
            Thread.yield();
        } catch (IOException e){
            System.err.println("IO Exception [id#" + id + "]: " + e);
            System.err.println("Interrupted [id#" + id + "]: " + e);
        } catch (RuntimeException e) {
            System.err.println("RuntimeException [id#" + id + "]: " + e);
        } finally {
            // Always close it:
            if (out != null) {
                out.close();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    System.err.println("Error closing: " + ex);
                }
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Socket not closed [id#" + id + "]");
            }
            System.out.println("End Client " + id + " ");
        }
    }

    public static class UMClient {

        public static void main(String[] args)
                throws IOException, InterruptedException {
            int port = 8011;
            InetAddress addr = InetAddress.getLocalHost();
            System.out.println("=======================");
            System.out.println("Connecting to Server: " + addr);
            System.out.println("On Port: " + port);
            System.out.println("=======================");

            new UMClientThread(addr, port);
            Thread.yield();
        }
    }
}
