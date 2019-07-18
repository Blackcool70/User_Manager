package com.usrmngr.client.core.model.Connectors;

import com.unboundid.ldap.sdk.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Encapsulates  an ldap connection
 */
public class LDAPConnector {
    private LDAPConnection connection;
    private BindResult bindResult;
    private LDAPConfig config;

    LDAPConnector() {
        connection = new LDAPConnection();
    }

    public LDAPConnector(LDAPConfig config) {
        this.config = new LDAPConfig(config);
    }

    public static void main(String[] args) {

    }

    /**
     * Returns the current config.
     * @return the current config.
     */
    public LDAPConfig getConfig() {
        return this.config;
    }

    /**
     * Sets the config file to be used by the connector.
     * @param config
     */
    public void setConfig(LDAPConfig config) {
        this.config = config;
    }


    public String getBaseDN() {
        return this.config.getBaseDN();
    }

    public void setBaseDN(String dn) {
        this.config.setBaseDN(dn);
    }

    public int getPort() {
        return this.config.getPort();
    }

    public void setPort(int port) {
        this.config.setPort(port);
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

    private JSONArray resultsToJSONArray(SearchResult result) {
        JSONArray jsonArray = new JSONArray();
        if (result == null) return jsonArray;
        result.getSearchEntries().forEach(
                searchResultEntry -> jsonArray.put(toJSON(searchResultEntry)));
        return jsonArray;
    }

    protected JSONArray search(String query, String... attributes) {
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
        return resultsToJSONArray(result);
    }

    public boolean isAuthenticated() {
        return bindResult != null && bindResult.getResultCode() == ResultCode.SUCCESS;
    }

    void disconnect() {
        if (isConnected()) {
            connection.close();
        }
        bindResult = null;

    }
}

