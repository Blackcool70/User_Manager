package com.usrmngr.server.core.model.RMI;
import com.usrmngr.server.core.model.RMI.REQUEST.HandleRequest;
import com.usrmngr.server.core.model.RMI.REQUEST.Request;
import com.usrmngr.server.core.model.RMI.REQUEST.TYPE;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String args[]){
        System.setProperty("java.security.policy","file:C:\\Users\\jecsa\\IdeaProjects\\user_manager\\src\\main\\resources\\client\\client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            StringBuilder stringBuilder = new StringBuilder("Hello world!;");
            String name = Server.DEFAULT_SERVER_REG_NAME;
            Registry registry = LocateRegistry.getRegistry();
            HandleRequest comp = (HandleRequest) registry.lookup(name);
            Request request = new Request(stringBuilder,TYPE.CREATE);
            Request reply = comp.handle(request);
            System.out.println(reply);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
