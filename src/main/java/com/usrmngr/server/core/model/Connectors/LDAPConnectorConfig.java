package com.usrmngr.server.core.model.Connectors;
 public class LDAPConnectorConfig{
     private Configuration config;
    LDAPConnectorConfig(){
        config = new Configuration();
        config.put("hostName","localhost");
        config.put("port","389");
        config.put("baseDN","");
    }
    public void setHostName(String hostName) {
        config.put("hostName",hostName);
    }

    public void setPort(int port) {
        config.put("port",String.valueOf(port));
    }

    public int getPort() {
        return Integer.parseInt(config.get("port"));
    }

    public String getHostName() {
        return config.get("hostName");
    }

    public void setBaseDN(String dn) {
        config.put("baseDN",dn);
    }

    public String getBaseDN() {
        return config.get("baseDN");
    }

    public static void main(String[] args) {
        LDAPConnectorConfig config = new LDAPConnectorConfig();
    }
}
