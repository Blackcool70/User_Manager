package com.usrmngr.client.core.model.Connectors;

/**
*Encapsulates  all the necessary  configuration to create an LDAP connection.
*/
public class LDAPConfig extends Configuration {
    public LDAPConfig(String server,int port){
        this.put("server",server);
        this.put("port",String.valueOf(port));
    }

    public int getPort() {
        try {
            return Integer.parseInt(get("port"));
        }catch (NumberFormatException e){
            String DEFAULT_PORT = "389";
            return Integer.parseInt(DEFAULT_PORT);
        }
    }

    public void setPort(int port) {
        put("port", String.valueOf(port));
    }

    public String getServer() {
        return  get("server");
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
