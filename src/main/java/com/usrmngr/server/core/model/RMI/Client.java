package com.usrmngr.server.core.model.RMI;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String args[]){
        System.setProperty("java.security.policy","file:C:\\Users\\jecsa\\IdeaProjects\\user_manager\\src\\main\\resources\\client\\client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Request";
            Registry registry = LocateRegistry.getRegistry();
            HandleRequest comp = (HandleRequest) registry.lookup(name);
            Request request = new Request();
            Request reply = comp.handle(request);
            System.out.println(reply);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
