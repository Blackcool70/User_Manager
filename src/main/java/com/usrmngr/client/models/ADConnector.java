package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;
public class ADConnector {
    private LDAPConnection ldapConnection;
    private String errorMessage;


    public ADConnector(String host, int port, String ldapSearchPath, String userName, String password) {
        errorMessage = null;
        connect(host, port, ldapSearchPath, userName, password);
    }

    public ADConnector(String host, int port) {
        errorMessage = null;
        connect(host, port);
    }

    private void connect(String host, int port) {
        try {
            ldapConnection = new LDAPConnection(host, port);
        } catch (LDAPException e) {
            errorMessage = e.getResultCode().toString();
        }
    }

    private void connect(String host, int port, String ldapSearchPath, String userName, String password) {
        clearErrorMessage();
        String bindDN = String.format("CN=%s,%s", userName, ldapSearchPath);
        try {
            ldapConnection = new LDAPConnection(host, port, bindDN, password);
        } catch (LDAPException e) {
            errorMessage = e.getResultCode().toString();

        }
    }

    public boolean isConnected() {
        return ldapConnection != null && ldapConnection.isConnected();
    }

    public void closeConnection() {
        ldapConnection.close();
        errorMessage = null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private void clearErrorMessage() {
        errorMessage = null;
    }

    public static void main(String[] args) {

    }

}

