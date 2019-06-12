package com.usrmngr.server.core.model.RMISample;


import java.rmi.Remote;
import java.rmi.RemoteException;
public interface Compute extends Remote {
    <T> T executeTask(Task<T> t) throws RemoteException;
}
