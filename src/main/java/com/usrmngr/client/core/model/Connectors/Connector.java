package com.usrmngr.client.core.model.Connectors;

public interface Connector {
    public void connect();
    public void disconnect();
    public boolean isConnected();
    public void setPort(int port);
    public int getPort();
    public void setServer(String server);
    public String getServer(String server);
    public Configuration getConfig();
    public void setConfig(Configuration config);
}
