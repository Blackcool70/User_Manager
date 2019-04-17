package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

public class ADConnector {
    private LDAPConnection ldapConnection;
    private String resultCode,errorMessageFromServer;

    public ADConnector() {
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
     *
     * @param host ldap enabled server name
     * @param port ldap enabled server port
     * @return true if the connection was successful otherwise false
     */
    private boolean connect(String host, int port) {
        try {
            ldapConnection.connect(host, port);
        } catch (LDAPException e) {
            resultCode = e.getResultCode().toString();
            errorMessageFromServer = e.getDiagnosticMessage();
        }
        return isConnected();
    }

    private void connect(String host, int port, String ldapSearchPath, String userName, String password) {
        connect(host, port);
        String bindDN = String.format("CN=%s,%s", userName, ldapSearchPath);
        bind(bindDN, password);
    }

    public boolean isConnected() {
        return ldapConnection != null && ldapConnection.isConnected();
    }

    public void closeConnection() {
        ldapConnection.close();
    }

    /**
     * Returns a json object of the requested cn and its attributes.
     * @param baseDN base search
     * @param cn cn entry to search for
     * @param attributes what attributes to return
     * @return a json object of the the requested cn and its attributes.
     */
    private JSONObject getADUser(String baseDN,String cn, String ... attributes) {
        if (!isConnected()) return null;
        SearchResult searchResult = null;
        StringBuilder filterSB = new StringBuilder(String.format("(&(objectCategory=person)(objectClass=user)(cn=%s))",cn));
        try {
            Filter  filter = Filter.create(filterSB.toString());
            searchResult = ldapConnection.search(baseDN, SearchScope.SUB,filter.toString(),attributes);
        } catch (LDAPException e) {
            resultCode = e.getResultCode().toString();
            errorMessageFromServer = e.getDiagnosticMessage();
        }
        return toJSON(searchResult);
    }

    private void bind(String bindDN, String password) {
        if (this.isConnected()) {
            try {
                this.ldapConnection.bind(bindDN, password);
            } catch (LDAPException e) {
                resultCode = e.getResultCode().toString();
            }
        }
    }

    private JSONObject toJSON(SearchResult sr) {
        if(sr == null) return  null;
        JSONObject object = new JSONObject();
        for(SearchResultEntry se : sr.getSearchEntries()){
             Collection<Attribute> attributes =se.getAttributes();
             for( Attribute a : attributes){
                 try {
                     object.put(a.getName(),a.getValue());
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             }
        }
        return  object;
    }

    public static void main(String[] args) {
        ADConnector adConnector = new ADConnector();
        adConnector.connect("192.168.1.2", 389);
        if (adConnector.isConnected()) {
            System.out.println("connected");
            adConnector.bind("CN=Administrator,OU=Users,OU=Company,DC=lab,DC=net", "xxxxxxxx");
            JSONObject object = adConnector.getADUser("dc=lab,dc=net", "Administratr","distinguishedName");
            if(object == null){
                System.out.println(adConnector.errorMessageFromServer);
            }else {
                System.out.println(object);
                System.out.println(adConnector.getErrorMessageFromServer());
            }

        } else {
            System.out.println("Connection failed");
            System.out.println(adConnector.getResultCode());
        }

    }

    public String getResultCode() {
        return  this.resultCode;
    }
    private String getErrorMessageFromServer(){
        return  this.errorMessageFromServer;
    }
}

