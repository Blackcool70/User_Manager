package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;

public class ADConnector {
    private LDAPConnection ldapConnection;
    private String errorMessage;
    public  ADConnector(){
        ldapConnection = new LDAPConnection();
    }
    public ADConnector(String host, int port, String ldapSearchPath, String userName, String password) {
        ldapConnection = new LDAPConnection();
        connect(host, port, ldapSearchPath, userName, password);
    }

    public ADConnector(String host, int port) {
        ldapConnection = new LDAPConnection();
        connect(host, port);
    }

    /**
     * Connects to an ldap AD server.
     * @param host server name
     * @param port ldap server port
     * @return  true if the connection was successful otherwise false
     */
    private boolean connect(String host, int port) {
        try {
            ldapConnection.connect(host,port);
        } catch (LDAPException e) {
            errorMessage  =  e.getResultCode().toString();
        }
        return  isConnected();
    }

    private void connect(String host, int port, String ldapSearchPath, String userName, String password) {
        connect(host,port);
        String bindDN = String.format("CN=%s,%s", userName, ldapSearchPath);
        bind(bindDN,password);
    }

    public boolean isConnected() {
        return ldapConnection != null && ldapConnection.isConnected();
    }

    public void closeConnection() {
        ldapConnection.close();
    }

    /**
     * Returns any errors generated and consumes the error.
     * @return
     */
    public  String getError(){
        String error = errorMessage != null ? errorMessage: "";
        consumeError();
        return error;
    }
    private  void   consumeError(){
        errorMessage = null;
    }

    private void searchUser(String baseDN, String cn){
        if(isConnected()) {
            try {
                SearchResult searchResult = ldapConnection.search(baseDN, SearchScope.SUB, String.format("(&(objectCategory=person)(objectClass=user)(cn=%s)",cn));
                System.out.println(searchResult.getEntryCount());

            } catch (LDAPSearchException e) {
                e.printStackTrace();
            }
        }
    }
    private  void bind(String bindDN, String password){
        if(this.isConnected()){
            try {
                this.ldapConnection.bind(bindDN,password);
            } catch (LDAPException e) {
                errorMessage = e.getResultCode().toString();
            }
        }
    }

    public static void main(String[] args) {
        ADConnector adConnector = new ADConnector();
        adConnector.connect("192.168.1.2",389);
        if(adConnector.isConnected()){
            System.out.println("connected");
            adConnector.bind("CN=Administrator,OU=Users,OU=Company,DC=lab,DC=net", "xxxx!");
            adConnector.searchUser("dc=lab,dc=net" ,"(objectClass=user)");
            System.out.println("search success!");

        }else{
            System.out.println("Connection failed");
            System.out.println(adConnector.errorMessage);
        }

    }

}

