package com.usrmngr.server.core.model.RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements HandleRequest {
    public static final String DEFAULT_SERVER_NAME = "USRMGR_SERV";
    private static final int DEFAULT_LISTEN_PORT = 8085; // 0 random
    private static final int DEFAULT_REGISTRY_PORT = 1099;
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

    private void startUp(){
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
            startUp();
        } catch (RemoteException e) {
            //failure
            e.printStackTrace();
        } catch (Exception e) {
            //something went wrong init the server
            e.printStackTrace();
        }

    }


    @Override
    public Request handle(Request request) throws RemoteException {
        System.out.println("Got Request:");
        System.out.println(request);
        request.setResult(RRESULT.SUCESS);
        return request;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startUp();
    }

}
