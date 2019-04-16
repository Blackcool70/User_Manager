package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;
import com.unboundid.util.args.ScopeArgument;
import com.usrmngr.client.Main;

public class ADConnector {
    private LDAPConnection ldapConnection;
    private String errorMessage;


    public  ADConnector(){
        ldapConnection = new LDAPConnection();
        errorMessage = null;
    }
    public ADConnector(String host, int port, String ldapSearchPath, String userName, String password) {
        ldapConnection = new LDAPConnection();
        errorMessage = null;
        connect(host, port, ldapSearchPath, userName, password);
    }

    public ADConnector(String host, int port) {
        ldapConnection = new LDAPConnection();
        errorMessage = null;
        connect(host, port);
    }

    private void connect(String host, int port) {
        try {
            ldapConnection.connect(host,port);
        } catch (LDAPException e) {
            errorMessage = e.getResultCode().toString();
        }
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
        errorMessage = null;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private void clearErrorMessage() {
        errorMessage = null;
    }
    private void searchUser(String baseDN, String filter){
        try {
            SearchResult searchResult = ldapConnection.search(baseDN,SearchScope.SUB,filter);
            System.out.println(searchResult.getEntryCount());

        } catch (LDAPSearchException e) {
            e.printStackTrace();
        }
    }
    private  void bind(String bindDN, String password){
        if(this.isConnected()){
            try {
                this.ldapConnection.bind(bindDN,password);
            } catch (LDAPException e) {
                e.printStackTrace();
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

