package com.usrmngr.client.core.model.Connectors;

public interface Connector {
    void connect();

    void disconnect();

    boolean isConnected();

    int getPort();

    void setPort(int port);

    void setServer(String server);

    String getServer();

    Configuration getConfig();

    void setConfig(Configuration config);
}
