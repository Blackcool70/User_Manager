package com.usrmngr.client.core.model.Connectors;

/**
 * Encapsulates  all the necessary  configuration to create an LDAP connection.
 */
public class LDAPConfig extends Configuration {
    public LDAPConfig() {
        put("hostName", "localhost");
        put("port", "389");
        put("baseDN", "");
    }

    /**
     *  Copy constructor
     * @param config
     */
    public LDAPConfig(LDAPConfig config){
        super(config);
    }
    public static void main(String[] args) {
    }

    public int getPort() {
        return Integer.parseInt(get("port"));
    }

    public void setPort(int port) {
        put("port", String.valueOf(port));
    }

    public String getHostName() {
        return get("hostName");
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
