package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

public class ADConnector {

    private LDAPConnection connection;
    private String resultCode, errorMessageFromServer;
    private String server, bindCN, password;
    private int port;
    private String baseDN;
    public ADConnector() {
        setConfigs("", 389, "", "", "");
        connection = new LDAPConnection();
    }

    public ADConnector(String server, int port, String baseDN, String bindDN, String password) {
        setConfigs(server, port, baseDN, bindDN, password);
        connection = new LDAPConnection();
    }

    public static void main(String[] args) {
        ADConnector adConnector = new ADConnector();
        adConnector.setConfigs("192.168.1.2", 389, "DC=lab,DC=net",
                "cn=Administrator,ou=Users,ou=Company,dc=lab,dc=net", "xxxxxxxx");
        if (adConnector.connect()) {
            System.out.println("Connected");
        } else {
            System.out.printf("Unable to connect %s\n", adConnector.getResultCode());
        }
        JSONObject jsonObject = adConnector.getADUser("Administrator", "mail");
        System.out.println(jsonObject);
        System.out.println(adConnector.getResultCode());

    }

    public void setConfigs(String server, int port, String baseDN, String bindCN, String password) {
        if (isConnected()) disconnect();
        this.server = server;
        this.port = port;
        this.bindCN = bindCN;
        this.baseDN = baseDN;
        this.password = password;
        System.out.println(this.toString());
    }

    public boolean connect() {
        try {
            connection.connect(server, port);
        } catch (LDAPException e) {
            resultCode = e.getResultCode().toString();
            errorMessageFromServer = e.getDiagnosticMessage();
        }
        return isConnected() && bind(bindCN, password);
    }

    private boolean bind(String bindCN, String password) {
        if (!this.isConnected()) return false;
        boolean result = true;
        try {
            this.connection.bind(bindCN, password);
        } catch (LDAPException e) {
            resultCode = e.getResultCode().toString();
            result = false;
        }
        return result;
    }

    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    public void disconnect() {
        connection.close();
    }

    /**
     * Returns a json object of the requested cn and its attributes.
     *
     * @param attributes what attributes to return
     * @return a json object of the the requested cn and its attributes.
     */
    private JSONObject getADUser(String userName, String... attributes) {
        SearchResult searchResult = null;
        StringBuilder filterSB = new StringBuilder(String.format(LDAPFilters.USER_SEARCH_FILTER, userName));
        boolean result = true;
        try {
            Filter filter = Filter.create(filterSB.toString());
            System.out.println("Filter:" + filter.toString());
            System.out.println("Attributes:" + attributes[0]);
            SearchRequest searchRequest =
                    new SearchRequest(baseDN, SearchScope.SUB, filter,
                            attributes);
            searchResult = connection.search(searchRequest);
        } catch (LDAPException e) {
            resultCode = e.getResultCode().toString();
            errorMessageFromServer = e.getDiagnosticMessage();
            result = false;
        }
        return result ? toJSON(searchResult) : new JSONObject();
    }

    private JSONObject toJSON(SearchResult sr) {
        JSONObject object = new JSONObject();
        for (SearchResultEntry se : sr.getSearchEntries()) {
            Collection<Attribute> attributes = se.getAttributes();
            for (Attribute a : attributes) {
                try {
                    object.put(a.getName(), a.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        return object;
    }

    public String getResultCode() {
        return this.resultCode == null ? "" : this.resultCode;
    }

    public String getErrorMessageFromServer() {
        return this.errorMessageFromServer == null ? "" : this.resultCode;
    }

    @Override
    public String toString() {
        return "server:" + this.server + "\n" +
                "port:" + this.port + "\n" +
                "baseDN:" + this.baseDN + "\n" +
                "bindCN:" + this.bindCN + "\n" +
                "password:" + this.password + "\n";
    }

    private static class LDAPFilters {
        private static final String USER_SEARCH_FILTER = "(&(objectClass=User)(anr=%s))";
    }
}

