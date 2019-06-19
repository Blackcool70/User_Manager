package com.usrmngr.server.core.model.RMI;

import com.usrmngr.server.core.model.RMI.REQUEST.HandleRequest;
import com.usrmngr.server.core.model.RMI.REQUEST.Request;
import com.usrmngr.server.core.model.RMI.REQUEST.TYPE;

import java.net.URI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private HandleRequest requestHandler;

    Client(String serverURI, int port) {
        try {

            Registry registry = LocateRegistry.getRegistry(port);
            requestHandler = (HandleRequest) registry.lookup(serverURI);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public Client() {
        try {
            Registry registry = LocateRegistry.getRegistry(Server.SERVER_PORT);
            requestHandler = (HandleRequest) registry.lookup(Server.SERVER_URI);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }


    public Request sendRequest(Request request) {
        try {
            return requestHandler.handle(request);
        } catch (RemoteException ignored) {
        }
        return  request;

    }

    public static void main(String args[]) throws RemoteException {
        Server server = new Server();
        server.start();
        Client client = new Client();
        Request request = client.sendRequest(new Request<>("Hello", TYPE.CREATE));
        System.out.println(request);


    }
}
