package com.usrmngr.server.core.model.Connectors;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Encapsulates actions that can be preformed in active directory services.
 */
public class ADConnector extends LDAPConnector {
    public ADConnector() {
        super();
    }

    JSONObject getADUser(String cn, String... attributes) {
        if (!isConnected()) return null; // or should I through exception?
        JSONArray results = search(String.format(ADLDAPFilters.USER_SEARCH_FILTER, cn), attributes);
        return results.getJSONObject(0);
    }
    JSONArray getAllADUsers(){
        if (!isConnected()) return null; // or should I through exception?
        return search(ADLDAPFilters.ALL_USER_SEARCH_FILTER,"displayName");
    }

    public static void main(String[] args) {
        ADConnector adConnector = new ADConnector();
        LDAPConfig config = new LDAPConfig();
        config.setBaseDN("dc=lab,dc=net");
        config.setHostName("192.168.1.2");
        config.setPort(389);
        adConnector.setConfig(config);
        adConnector.connect();
        System.out.println(adConnector.isConnected());
        adConnector.authenticate("cn=Administrator,ou=Users,ou=Company,dc=lab,dc=net", "J3cs4nb!");
        System.out.println(adConnector.isAuthenticated());
        System.out.println(adConnector.getADUser("Administrator", "displayName"));
        JSONArray users = adConnector.getAllADUsers();
        for (int i=0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if(user.has("displayName")) System.out.println(user.get("displayName"));

        }

    }

    private static class ADLDAPFilters {
        private static final String USER_SEARCH_FILTER = "(&(objectClass=User)(anr=%s))";
        private static final String ALL_USER_SEARCH_FILTER = "(objectClass=User)";
    }
}
