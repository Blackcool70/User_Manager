package com.usrmngr.client.core.model.Connectors;

import com.unboundid.ldap.sdk.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;

/**
 * Encapsulates  an ldap connector
 */
public class LDAPConnector implements Connector {
    private LDAPConnection connection;
    private BindResult bindResult;

    private Configuration config;

    LDAPConnector() {
        connection = new LDAPConnection();
    }

    public static void main(String[] args) {

    }

    /**
     * Returns the current config.
     *
     * @return the current config.
     */
    public Configuration getConfig() {
        return this.config;
    }

    /**
     * Sets the config file to be used by the connector.
     *
     * @param config
     */
    public void setConfig(Configuration config) {
        this.config = config;
    }


    public String getBaseDN() {
        return config.get("baseDN");
    }

    public void setBaseDN(String dn) {
        config.put("baseDN", dn);
    }

    public int getPort() {
        return Integer.parseInt(config.get("port"));
    }

    public void setPort(int port) {
        this.config.put("port", String.valueOf(port));
    }

    @Override
    public void setHost(String host) {
        this.config.put("host", host);
    }

    @Override
    public String getHost() {
        return this.config.get("host");
    }

    public void authenticate(String userName, String password) {
        try {
            bindResult = connection.bind(userName, password);
        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        if (isConnected()) disconnect();
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
            System.err.println(e.getMessage());
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
            result = connection.search(searchRequest);
        } catch (LDAPException e) {
            e.printStackTrace();
        }
        return resultsToJSONArray(result);
    }

    public boolean isAuthenticated() {
        return bindResult != null && bindResult.getResultCode() == ResultCode.SUCCESS;
    }

    public void disconnect() {
        if (isConnected()) {
            connection.close();
        }
        bindResult = null;

    }
}

