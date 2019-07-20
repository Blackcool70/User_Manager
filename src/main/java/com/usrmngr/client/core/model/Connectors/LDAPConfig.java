package com.usrmngr.client.core.model.Connectors;

/**
*Encapsulates  all the necessary  configuration to create an LDAP connection.
*/
public class LDAPConfig extends Configuration {
    private final String  DEFAULT_PORT = "389";
    public LDAPConfig() {
        super();
    }

    public static void main(String[] args) {
    }

    public int getPort() {
        try {
            return Integer.parseInt(get("port"));
        }catch (NumberFormatException e){
            return Integer.parseInt(DEFAULT_PORT);
        }
    }

    public void setPort(int port) {
        put("port", String.valueOf(port));
    }

    public String getHostName() {
        return (String) getOrDefault("hostName","localhost");
    }

    public void setHostName(String hostName) {
        put("hostName", hostName);
    }

    public String getBaseDN() {
        return get("baseDN");
    }

    // location of where to start searching the directory tree.
    public void setBaseDN(String dn) {
        put("baseDN", dn);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
