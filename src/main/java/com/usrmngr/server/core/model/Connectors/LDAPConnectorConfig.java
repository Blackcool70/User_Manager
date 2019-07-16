package com.usrmngr.server.core.model.Connectors;

public class LDAPConnectorConfig extends  Configuration{
    public LDAPConnectorConfig(){
        put("hostName","localhost");
        put("port","389");
        put("baseDN","");
    }
    public void setHostName(String hostName) {
        put("hostName",hostName);
    }

    public void setPort(int port) {
        put("port",String.valueOf(port));
    }

    public int getPort() {
        return Integer.parseInt(get("port"));
    }

    public String getHostName() {
        return get("hostName");
    }

    public void setBaseDN(String dn) {
        put("baseDN",dn);
    }

    public String getBaseDN() {
        return get("baesDN");
    }

    public static void main(String[] args) {
        LDAPConnectorConfig config = new LDAPConnectorConfig();
    }
}
