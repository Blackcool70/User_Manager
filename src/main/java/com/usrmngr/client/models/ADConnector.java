package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Optional;
import java.util.Properties;

public class ADConnector {

    private LDAPConnection connection;
    private String server;
    private int port;
    private String bindCN;
    private String baseDN;
    private String password;
    private String resultCode;
    private String errorMessageFromServer;

    public ADConnector() {
        setConfigs("", 389, "", "", "");
        connection = new LDAPConnection();
    }

    public ADConnector(String server, int port, String baseDN, String bindDN, String password) {
        setConfigs(server, port, baseDN, bindDN, password);
        connection = new LDAPConnection();
    }

    public ADConnector(Properties properties) {
        if (properties == null) return;
        setConfigs((String) properties.getOrDefault("server", ""),
                Integer.parseInt((String) properties.getOrDefault("port", "389")),
                (String) properties.getOrDefault("baseDN", ""),
                (String) properties.getOrDefault("bindDN", ""),
                (String) properties.getOrDefault("password", ""));
    }

    public static void main(String[] args) {
        ADConnector adConnector = new ADConnector();
        adConnector.setConfigs("192.168.1.2", 389, "DC=lab,DC=net",
                "cn=Administrator,ou=Users,ou=Company,dc=lab,dc=net", "J3cs4nb!");
        if (adConnector.connect()) {
            System.out.println("Connected");
        } else {
            System.out.printf("Unable to connect %s\n", adConnector.getResultCode());
        }
//        JSONObject jsonObject = adConnector.getADUser("Administrator", "mail");
//        System.out.println(jsonObject);
//        System.out.println(adConnector.getResultCode());
        JSONArray jsonArray = adConnector.getAllADUsers("displayName");
        System.out.println(jsonArray);

    }

    public void setConfigs(String server, int port, String baseDN, String bindCN, String password) {
        if (isConnected()) disconnect();
        this.server = server;
        this.port = port;
        this.bindCN = bindCN;
        this.baseDN = baseDN;
        this.password = password;
//        System.out.println(this.toString());
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
        JSONObject jsonObject = new JSONObject();
        Optional<SearchResult> optionalSearchResult = searchAD(String.format(LDAPFilters.USER_SEARCH_FILTER, userName), attributes);
        if (optionalSearchResult.isPresent()) {
            jsonObject = toJSON(optionalSearchResult.get().getSearchEntries().get(0));
        }
        return jsonObject;
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

    private String getResultCode() {
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

    public JSONArray getAllADUsers(String... attributes) {
        JSONArray jsonArray = new JSONArray();
        Optional<SearchResult> optional = searchAD(LDAPFilters.ALL_USER_SEARCH_FILTER, attributes);
        optional.ifPresent(searchResult -> searchResult.getSearchEntries().forEach(
                searchResultEntry -> jsonArray.put(toJSON(searchResultEntry))));
        return jsonArray;
    }

    private Optional<SearchResult> searchAD(String query, String... attributes) {
        Optional<SearchResult> result = Optional.empty();
        try {
            Filter filter = Filter.create(query);
//            System.out.println("Filter:" + filter.toString());
//            System.out.println("Attributes:" + attributes[0]);
            SearchRequest searchRequest =
                    new SearchRequest(baseDN, SearchScope.SUB, filter,
                            attributes);
            result = Optional.of(connection.search(searchRequest));
        } catch (LDAPException e) {
            resultCode = e.getResultCode().toString();
            errorMessageFromServer = e.getDiagnosticMessage();
        }
        return result;
    }

    private static class LDAPFilters {
        private static final String USER_SEARCH_FILTER = "(&(objectClass=User)(anr=%s))";
        private static final String ALL_USER_SEARCH_FILTER = "(objectClass=User)";
    }
}

