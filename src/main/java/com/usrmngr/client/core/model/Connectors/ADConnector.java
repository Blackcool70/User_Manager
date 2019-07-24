package com.usrmngr.client.core.model.Connectors;

import com.usrmngr.util.Alert.AlertMaker;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Encapsulates actions that can be preformed in active directory services.
 */
public class ADConnector extends LDAPConnector {
    public ADConnector(Configuration config){
        super(config);
    }

    public JSONObject getADUser(String cn, String... attributes) {
        JSONArray results = search(String.format(ADLDAPFilters.USER_SEARCH_FILTER, cn), attributes);
        if(results.isEmpty()) {
            AlertMaker.showSimpleAlert("Alert", "Unable to get user. Try again.");
            //may want to refresh user list when this fails
        }
        return results.getJSONObject(0);
    }
    public void setConfig(Configuration config){
        super.setConfig(config);
    }
    public JSONArray getAllADUsers(String ... attributes){
        return search(ADLDAPFilters.ALL_USER_SEARCH_FILTER,attributes);
    }

    public static void main(String[] args) {

    }

    private static class ADLDAPFilters {
        private static final String USER_SEARCH_FILTER = "(cn=%s)";
        private static final String ALL_USER_SEARCH_FILTER = "(objectClass=User)";
    }
}
