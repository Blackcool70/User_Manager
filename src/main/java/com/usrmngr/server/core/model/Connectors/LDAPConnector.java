package com.usrmngr.server.core.model.Connectors;

import com.unboundid.ldap.sdk.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

public class LDAPConnector {
    private LDAPConnection connection;
    private BindResult bindResult;
    private LDAPConnectorConfig config;

    LDAPConnector() {
        config = new LDAPConnectorConfig();
        connection = new LDAPConnection();
    }

    public LDAPConnector(LDAPConnectorConfig config) {
        this.config = config;
    }

    public void setHostName(String host) {
        this.config.setHostName(host);
    }

    public void setPort(int port) {
        this.config.setPort(port);
    }

    public void setBaseDN(String dn){
        this.config.setBaseDN(dn);
    }
    public String getBaseDN(){
        return this.config.getBaseDN();
    }
    public int getPort() {
        return this.config.getPort();
    }

    public String getHost() {
        return this.config.getHostName();
    }


    public void authenticate(String userName, String password) {
        try {
            bindResult = connection.bind(userName, password);
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            connection = new LDAPConnection(getHost(), getPort());
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    private JSONObject toJSON(SearchResultEntry se) {
        JSONObject object = new JSONObject();
        try {
            object.put("DN", se.getDN());
            Collection<Attribute> attributes = se.getAttributes();
            for (Attribute a : attributes) {
                object.put(a.getName(), a.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    private JSONArray resultsToJSONArray(SearchResult result){
        JSONArray jsonArray = new JSONArray();
        if(result == null) return jsonArray;
        result.getSearchEntries().forEach(
                searchResultEntry -> jsonArray.put(toJSON(searchResultEntry)));
        return  jsonArray;
    }
    private JSONArray search(String query,String ...attributes) {
        SearchResult result = null;
        try {
            Filter filter = Filter.create(query);
            SearchRequest searchRequest =
                    new SearchRequest(getBaseDN(), SearchScope.SUB, filter,
                            attributes);
            System.out.println("Test");
            result = connection.search(searchRequest);
        } catch (LDAPException ignored) {
        }
        return  resultsToJSONArray(result);
    }

    public boolean isAuthenticated() {
        return bindResult != null && bindResult.getResultCode() == ResultCode.SUCCESS;
    }

    public static void main(String[] args) {
        LDAPConnectorConfig lc = new LDAPConnectorConfig();
        lc.setBaseDN("DC=lab,DC=net");
        lc.setHostName("192.168.1.2");
        lc.setPort(389);
        LDAPConnector ld = new LDAPConnector(lc);
        ld.connect();
        System.out.println("Connected: " + ld.isConnected());
        ld.authenticate("CN=Administrator,OU=Users,OU=Company,DC=lab,DC=net", "J3cs4nb!");
        System.out.println("Auth:" + ld.isAuthenticated());
        JSONArray se = ld.search( "(objectClass=User)");
        System.out.println(se.toString());


    }

    private void disconnect() {
        if (isConnected()) {
            connection.close();
        }
        bindResult = null;

    }
}

