package com.usrmngr.server.core.model.RMI;

import com.usrmngr.server.core.model.RMI.REQUEST.RESULT;
import com.usrmngr.server.core.model.RMI.REQUEST.Request;
import com.usrmngr.server.core.model.RMI.REQUEST.TYPE;
import org.junit.Test;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static org.junit.Assert.*;

public class ServerTest {
    @Test
    public void start() {
        try {
            Server server = new Server();
            server.start();
            assertTrue(server.isRunning());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void isRunning() {
        try {
            Server server = new Server();
            server.start();
            assertTrue(server.isRunning());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void handle() {

    }


    @Test
    public void stop() {
    }
}