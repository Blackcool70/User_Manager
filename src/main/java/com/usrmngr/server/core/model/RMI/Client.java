package com.usrmngr.server.core.model.RMI;
import com.google.gson.Gson;
import org.json.JSONObject;

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
            StringBuilder stringBuilder = new StringBuilder("I am god!;");
            String name = Server.DEFAULT_SERVER_REG_NAME;
            Registry registry = LocateRegistry.getRegistry();
            HandleRequest comp = (HandleRequest) registry.lookup(name);
            Request request = new Request(RTYPE.CREATE,stringBuilder);
            Request reply = comp.handle(request);
            System.out.println(reply);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
