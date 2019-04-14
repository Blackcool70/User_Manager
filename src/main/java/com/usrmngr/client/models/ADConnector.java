package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;
import com.usrmngr.client.util.DialogManager;
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
        Optional<Pair<String, String>> result = DialogManager.getCredentials(userName,String.format("Enter credential for: %s on port %s", host, port));
        result.ifPresent(usernamePassword -> System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue()));
//        connection = new LDAPConnection(host, port, port, password);
    }
    public static void main(String[] args) {

    }
}

