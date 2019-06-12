package com.usrmngr.server.core.model.RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;

public class ComputePi {
    public static void main(String args[]) {
        System.setProperty("java.security.policy","file:C:\\Users\\jecsa\\IdeaProjects\\user_manager\\src\\main\\java\\com\\usrmngr\\server\\core\\model\\RMI\\client.policy");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry();
            Compute comp = (Compute) registry.lookup(name);
            Pi task = new Pi(Integer.parseInt("3"));
            BigDecimal pi = comp.executeTask(task);
            System.out.println(pi);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}
