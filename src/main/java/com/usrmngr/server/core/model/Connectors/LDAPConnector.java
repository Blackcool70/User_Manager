package com.usrmngr.server.core.model.Connectors;

import com.unboundid.ldap.sdk.*;

public class LDAPConnector {
    private LDAPConnection connection;
    private  LDAPConnectorConfig config;

    public LDAPConnector() {
        config = new LDAPConnectorConfig();
        connection = new LDAPConnection();
    }
    public LDAPConnector(LDAPConnectorConfig config){
        this.config = config;
    }
    public  void setHost(String host){
        this.config.setHostName(host);
    }
    public  void setPort(int port){
        this.config.setPort(port);
    }
    public  int getPort(){
        return this.config.getPort();
    }
    public String getHost(){
        return this.config.getHostName();
    }
    public void setBaseDN(String dn){
        this.config.setBaseDN(dn);
    }
    public boolean autheticate(String userName,String password){
        boolean success = true;
        try {
            connection.bind(String.format("CN=%s,%s",userName,config.getBaseDN()),password);
        } catch (LDAPException e) {
            success = false;
            e.printStackTrace();
        }
        return  success;
    }
    public void connect(){
        try {
            connection = new LDAPConnection(getHost(),getPort());
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }
    public boolean isConnected(){
        return connection != null && connection.isConnected();
    }

    public static void main(String[] args) {
        LDAPConnectorConfig lc = new LDAPConnectorConfig();
        lc.setBaseDN("ou=Users,ou=Company,dc=lab,dc=net");
        lc.setHostName("192.168.1.2");
        lc.setPort(389);
        System.out.println("config\n"+lc.toString());
        LDAPConnector ld = new LDAPConnector(lc);
        ld.connect();
        System.out.println("Connected: " + ld.isConnected());
        System.out.println("Connected: " + ld.isConnected());
        System.out.println("Connected: " + ld.isConnected());
        ld.disconnect();
        System.out.println("Connected: " + ld.isConnected());
        ld.connect();
        System.out.println("Connected: " + ld.isConnected());


    }

    private void disconnect() {
         if(isConnected()) connection.close();
    }
}

