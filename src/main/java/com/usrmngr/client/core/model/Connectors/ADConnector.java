package com.usrmngr.client.core.model.Connectors;

import com.usrmngr.client.core.model.ADUser;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Encapsulates actions that can be preformed in active directory services.
 */
public class ADConnector extends LDAPConnector {
    public ADConnector(Configuration config){
        super(config);
    }

    public ADUser getADUser(String cn, String ... attributes){
        return  new ADUser( getADUserASJson(cn,attributes));
    }
    public JSONObject getADUserASJson(String cn, String... attributes) {
        JSONArray results = search(String.format(ADLDAPFilters.USER_SEARCH_FILTER, cn), attributes);
        return results == null ? new JSONObject() :results.getJSONObject(0);
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
