package com.usrmngr.server.core.model.QuickServer.UMServer;

import org.quickserver.net.AppException;
import org.quickserver.net.server.QuickServer;

import static java.lang.Thread.sleep;

public class UMServer extends QuickServer {
    public static String VER = "1.0";
    public static String objHandle = "com.usrmngr.server.core.model.QuickServer.UMServer.ObjectHandler";
    public static String cmdHandle = "com.usrmngr.server.core.model.QuickServer.UMServer.CommandHandler";
    public UMServer(){
        super(cmdHandle);
        this.setPort(8125);
        this.setName("UM Server Version " + VER);
        this.setClientObjectHandler(objHandle);
        this.getBasicConfig().getServerMode().setBlocking(true);
        this.getBasicConfig().getAdvancedSettings().setDebugNonBlockingMode(true);
        this.getBasicConfig().setCommunicationLogging(true);
    }


    public static void main(String[] args) {
        UMServer umServer = new UMServer();
        try	{
            umServer.startServer();
            sleep(2000);
            umServer.stopServer();
        } catch(AppException e){
            System.out.println("Error in server : "+e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
