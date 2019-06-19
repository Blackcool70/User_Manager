package com.usrmngr.server.core.model.RMI;

import com.usrmngr.server.core.model.RMI.REQUEST.HandleRequest;
import com.usrmngr.server.core.model.RMI.REQUEST.RESULT;
import com.usrmngr.server.core.model.RMI.REQUEST.Request;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements HandleRequest {
    public static final String DEFAULT_HOST_NAME = "LOCALHOST";
    public static final String DEFAULT_SERVER_REG_NAME = "USRMGR_SERV";
    private static final int DEFAULT_LISTEN_PORT = 8085; // 0 random
    private static final int DEFAULT_REGISTRY_PORT = 1099;
    public static String DEFAULT_SERVER_POLICY_PATH = "C:\\Users\\jecsa\\IdeaProjects\\user_manager\\src\\main\\resources\\server\\server.policy";

    private HandleRequest stub;
    private Registry registry;
    private static final Logger logger = LogManager.getLogger(Server.class.getName());
    private final String serverPolicyPath;
    private final int port;
    private final String serverRegName;
    private final String hostName;

    public Server() {
        super();
        serverRegName = DEFAULT_SERVER_REG_NAME;
        port = DEFAULT_LISTEN_PORT;
        serverPolicyPath = DEFAULT_SERVER_POLICY_PATH;
        hostName = DEFAULT_HOST_NAME;
        logger.log(Level.INFO, "Server created.");
    }

    private void setServerHostname(String hostName) {
        System.setProperty("java.rmi.server.hostname", hostName);
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

    public void startUp() {
        init();
    }

    private void setRegistry(String serverName, int serverPort) throws RemoteException {
        registry = LocateRegistry.createRegistry(serverPort);
        setStub(DEFAULT_LISTEN_PORT);
        registry.rebind(serverName, stub);
    }

    private void init() {
        logger.log(Level.INFO,"Server starting...");
        try {
            setServerHostname(hostName);
            setSecurityPolicy(serverPolicyPath);
            setSecurityManager();
            setRegistry(serverRegName, DEFAULT_REGISTRY_PORT);
            logger.log(Level.INFO, "Server Initialized and running.");
            logger.log(Level.DEBUG, this.toString());
        } catch (RemoteException e) {
            //failure with connection ?
            logger.log(Level.ERROR, "Remote failure... I know ... vague!");
            logger.log(Level.TRACE, e);
        } catch (Exception e) {
            //something went wrong init the server
            logger.log(Level.ERROR, "Something else happened...");
            logger.log(Level.TRACE, e);
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServerName=").append(getServerRegName());
        sb.append("\nPort=").append(getServerPort());
        sb.append("\nServerPolicyFile=").append(getServerPolicyPath());
        return sb.toString();
    }

    private String getServerPolicyPath() {
        return this.serverPolicyPath;
    }

    private String getServerPort() {
        return String.valueOf(this.port);
    }

    private String getServerRegName() {
        return this.serverRegName;
    }

    @Override
    public Request handle(Request request) throws RemoteException {
        logger.log(Level.INFO,"Server handling request:\n".concat(request.toString()));
        // place holders
        request.setResult(RESULT.SUCCESS);
        return request;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startUp();

    }

    public void shutDown() {
        logger.log(Level.INFO,"Server shutting down...");
        //dont know how to shut it down...
        logger.log(Level.INFO,"Server still running... not sure how to stop.");
    }

    public boolean isRunning() {
        try {
            // this only works for the registry lookup the actual RMI server is still running on its thread...
            // not sure how to properly stop it.
            this.registry.lookup(DEFAULT_SERVER_REG_NAME);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}
