package com.usrmngr.server.core.model.QuickServer.UMServer;

import org.quickserver.net.AppException;
import org.quickserver.net.server.QuickServer;

public class UMServer extends QuickServer {
    private static String VER = "1.0";
    private static String cmdHandle = "com.usrmngr.server.core.model.QuickServer.UMServer.RequestCommandHandler";

    public UMServer() {
        super(cmdHandle);
        this.setPort(8125);
        this.setName("UM Server Version " + VER);
        this.getBasicConfig().getServerMode().setBlocking(true);
        this.getBasicConfig().getAdvancedSettings().setDebugNonBlockingMode(true);
        this.getBasicConfig().setCommunicationLogging(true);
    }

    public static void main(String[] args) {
        UMServer umServer = new UMServer();
        try {
            umServer.startServer();
        } catch (AppException e) {
            System.out.println("Error in server : " + e);
            e.printStackTrace();
        }

    }
}
