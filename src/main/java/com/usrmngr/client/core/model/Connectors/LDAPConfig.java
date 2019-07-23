package com.usrmngr.client.core.model.Connectors;

/**
 * Encapsulates  all the necessary  configuration to create an LDAP connector.
 */
public class LDAPConfig extends Configuration {
    public LDAPConfig(LDAPConfig config) {

    }

    public LDAPConfig(String server, int port) {
        this.put("server", server);
        this.put("port", String.valueOf(port));
    }

    public LDAPConfig(String server, int port, String baseDN) {
        this.put("server", server);
        this.put("port", String.valueOf(port));
        this.put("baseDN", baseDN);
    }

    public LDAPConfig() {
        this.put("server", "");
        this.put(("port"), "");
        this.put(("baseDN"), "");
    }

    public static void main(String[] args) {
    }

    public int getPort() {
        try {
            return Integer.parseInt(get("port"));
        } catch (NumberFormatException e) {
            String DEFAULT_PORT = "389";
            return Integer.parseInt(DEFAULT_PORT);
        }
    }

    public void setPort(int port) {
        put("port", String.valueOf(port));
    }

    public String getServer() {
        return get("server");
    }

    public void setServer(String server) {
        put("server", server);
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
