package com.usrmngr.server.core.model.RMI;

import com.usrmngr.server.core.model.RMI.REQUEST.HandleRequest;
import com.usrmngr.server.core.model.RMI.REQUEST.RESULT;
import com.usrmngr.server.core.model.RMI.REQUEST.Request;
import com.usrmngr.server.core.model.RMI.REQUEST.TYPE;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements HandleRequest {
    public static final String SERVER_URI = "//localhost/UMServer";
    public static final int SERVER_PORT = 8081;
    private final String URI;
    private final int PORT;
    private Registry registry;

    public Server() throws RemoteException {
        super();
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        this.URI = SERVER_URI;
        this.PORT = SERVER_PORT;
    }

    Server(String serverURI, int port) throws RemoteException {
        super();
        this.URI = serverURI;
        this.PORT = port;
    }

    @Override
    public Request handle(Request request) {
//        logger.log(Level.INFO,"Server handling request:\n".concat(request.toString()));
        // place holders
        request.setResult(RESULT.SUCCESS);
        return request;
    }

    public void start() {
        try {
            this.registry = LocateRegistry.createRegistry(PORT);
            this.registry.rebind(URI, this);
            System.out.println("Server started");
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Failure");
        }
    }

    public boolean isRunning(){
        if(registry == null) return false;
        try {
            registry.lookup(this.URI);
            return  true;
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            return  false;
        }
    }
    public void stop() {
        try {
            registry.unbind(SERVER_URI);
            UnicastRemoteObject.unexportObject(this, true);
            UnicastRemoteObject.unexportObject(registry, true);

            System.out.println("Server stopped");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
            Client client = new Client();
            Request result = client.sendRequest(new Request<>("hello", TYPE.CREATE));
            System.out.println(result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
