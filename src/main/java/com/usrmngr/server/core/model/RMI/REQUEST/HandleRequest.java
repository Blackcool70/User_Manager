package com.usrmngr.server.core.model.RMI.REQUEST;

import com.usrmngr.server.core.model.RMI.REQUEST.Request;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HandleRequest  extends Remote {
    Request handle(Request request) throws RemoteException;
}
