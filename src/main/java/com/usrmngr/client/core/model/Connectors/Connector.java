package com.usrmngr.client.core.model.Connectors;

public interface Connector {
    void connect();

    void disconnect();

    boolean isConnected();

    int getPort();

    void setPort(int port);

    void setHost(String server);

    String getHost();

    Configuration getConfig();

    void setConfig(Configuration config);
}
