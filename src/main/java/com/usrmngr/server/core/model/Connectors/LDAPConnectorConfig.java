package com.usrmngr.server.core.model.Connectors;

import java.util.HashMap;

// Holds configurations needed for an ldap connection.
class LDAPConnectorConfig {
    private HashMap<String, String> values;

    LDAPConnectorConfig() {
        this.values = new HashMap<>();
        this.values.put("hostName", "localhost");
        this.values.put("port", "398");
       this.values.put("baseDN", "");
    }

    /**
     * Returns the port used for connection or the default port of 389.
     *
     * @return ldap port
     */
    int getPort() {
        return Integer.parseInt(this.values.get("port"));
    }

    void setPort(int port) {
        this.values.put("port", String.valueOf(port));
    }

    /**
     * Returns the hostname of the connecting server.
     *
     * @return hostname
     */
    String getHostName() {
        return values.get("hostName");
    }

    void setHostName(String hostName) {
        this.values.put("hostName", hostName);
    }

    /**
     * The point from where a server will search for users.
     *
     * @param dn
     */
    void setBaseDN(String dn) {
        this.values.put("baseDN", dn);
    }

    String getBaseDN() {
        return values.get("baseDN");
    }

    /**
     * Returns string representation of the config
     *
     * @return string representation
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        values.forEach((key, value) ->
            sb.append(String.format("%s=%s\n", key, value)));
        return sb.toString();
    }

    public static void main(String[] args) {
        LDAPConnectorConfig a = new LDAPConnectorConfig();
        System.out.println(a);
    }
}
