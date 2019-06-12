package com.usrmngr.server.core.model.RMI;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements HandleRequest {
    public static final int DEFAULT_LISTEN_PORT = 8085; // 0 random
    public static final int DEFAULT_REGISTRY_PORT = 1099;
    public static  String DEFAULT_SERVER_POLICY_PATH = "C:\\Users\\jecsa\\IdeaProjects\\user_manager\\src\\main\\resources\\server\\server.policy";
    private HandleRequest stub;
    private Registry  registry;
    Server() {
        super();
        init();
    }

    private void setServerHostname(String hostname) {
        System.setProperty("java.rmi.server.hostname", hostname);
    }

    private void setSecurityPolicy(String filePath) {
        System.setProperty("java.security.policy", "file:".concat(filePath));
    }

    private void setSecurityManager() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    private void setStub(int port) throws RemoteException {
        stub = (HandleRequest) UnicastRemoteObject.exportObject(this, port);
    }

    private void start(){
        System.out.println("Server started");
        try {
            registry.rebind("Request", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    private void setRegistry(int port) throws RemoteException {
        registry = LocateRegistry.createRegistry(port);
    }

    private void init() {
        try {
            setServerHostname("localhost");
            setSecurityPolicy(DEFAULT_SERVER_POLICY_PATH);
            setSecurityManager();
            setRegistry(DEFAULT_REGISTRY_PORT);
            setStub(DEFAULT_LISTEN_PORT);
        } catch (RemoteException e) {
            //failure
            e.printStackTrace();
        } catch (Exception e) {
            //something went wrong init the server
            e.printStackTrace();
        }

    }
    private static  String getResourceAbsulatePath(String name){
        URL res = Server.class.getClassLoader().getResource(name);
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return  file.getAbsolutePath();
    }

    @Override
    public Request handle(Request request) throws RemoteException {
        request.setName("Test");
        System.out.println("Handled request!");
        return request;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}
