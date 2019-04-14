package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;
import com.usrmngr.client.util.DialogManager;
import javafx.application.Platform;
import javafx.util.Pair;
import java.util.Optional;


public class ADConnector {
    private  LDAPConnection connection;

    public ADConnector(){
        connection = new LDAPConnection();
    }
    public ADConnector(String host, int port,String ldapSearchPath,String userName){
        connect(host,port,ldapSearchPath,userName);
    }
    public void connect(String host, int port, String ldapSearchPath,String userName){
        Pair<String, String> credentials;
        Optional<Pair<String, String>> result = DialogManager.getCredentials(userName,String.format("Enter credential for: %s on port %s", host, port));
        if(!result.isPresent()){
            DialogManager.showError("Password is required, Aborting.");
            Platform.exit();
            System.exit(0);
        }
        credentials = result.get();
        userName = credentials.getKey();
        String bindDN =  String.format("CN=%s,%s",userName,ldapSearchPath);
//        System.out.printf("bindDN: %s\n",bindDN);
//        System.out.printf("Username:%s",userName);
//        System.out.printf("password: %s",credentials.getValue());
        try {
            connection = new LDAPConnection(host,port,  bindDN, credentials.getValue());
            DialogManager.showInfo("Connected successfully.");
        } catch (LDAPException e) {
            e.printStackTrace();
            DialogManager.showError("Connection failed! Aborting.");
        }
    }
    public void search(String bindFilter){
    }
    public  void closeConnection(){
        connection.close();
    }
    public static void main(String[] args) {

    }
}

