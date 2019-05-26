package com.usrmngr.server.core.model;

// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import com.usrmngr.server.Main;
import org.apache.logging.log4j.Level;

// Server class
public class Server implements Runnable {
    private Preferences preferences;
    private boolean run;

    public Server(Preferences preferences) {
        run = true;
        assert preferences != null;
        this.preferences = preferences;
    }
    public  void stop() {
        run = false;
    }
    @Override
    public void run() {
        Main.LOGGER.log(Level.INFO,"Server thread started");
        int i = 0;
        while(run){
            try {
                Thread.sleep(100);
                System.out.println(i++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Main.LOGGER.log(Level.INFO,"Server thread ended");
    }
}
