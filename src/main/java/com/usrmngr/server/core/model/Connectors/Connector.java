package com.usrmngr.server.core.model.Connectors;

//rule of what all connectors must be able to do
public interface Connector {
    boolean isConnected();
    void disconnect();
    void connect();
}
