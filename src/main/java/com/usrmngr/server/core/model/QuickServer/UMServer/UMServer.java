package com.usrmngr.server.core.model.QuickServer.UMServer;
import com.usrmngr.server.core.model.Connectors.LDAPConfig;
import org.quickserver.net.AppException;
import org.quickserver.net.server.QuickServer;
public class UMServer  {
    private  String cmdHandle = "com.usrmngr.server.core.model.QuickServer.UMServer.CommandHandler";
    private  String objHandle = "com.usrmngr.server.core.model.QuickServer.ObjectHandler";
    private  QuickServer server;

    private  final static int PORT = 8011;
    private  final static String SERVER_NAME = "UM Server";
    private boolean running;
    /**
     * Core server that is manipulated by the GUI
     */
    public UMServer() {
        server = new QuickServer(cmdHandle);
        server.setClientObjectHandler(objHandle);
        server.setPort(PORT);
        server.setName(SERVER_NAME);
        server.getBasicConfig().getServerMode().setBlocking(true);
        server.getBasicConfig().getAdvancedSettings().setDebugNonBlockingMode(true);
        server.getBasicConfig().setCommunicationLogging(true);
    }

    public void startServer(){
        try {
            server.startServer();
            this.running = true;
        } catch (AppException e) {
            // what is best to do in this case?
            e.printStackTrace();
        }
    }
    public void stopServer(){
        try {
            server.stopServer();
            running = false;
        } catch (AppException e) {
            // what is best to do in this case?
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UMServer umServer = new UMServer();
        umServer.startServer();
    }

    public boolean isRunning() {
        return running;
    }
}
