package com.usrmngr.client.models;

import com.unboundid.ldap.sdk.*;

public class LDAPConnector {

    public static void main(String[] args) {
    LDAPConnection connection;
        try {
            connection = new LDAPConnection("ldap.forumsys.com",389,"cn=read-only-admin,dc=example,dc=com","password");
            SearchResult searchResult = connection.search("dc=example,dc=com",
                    SearchScope.SUB, "(uid=*)");
            System.out.println(searchResult.getEntryCount() + " entries returned.");
            for (SearchResultEntry e : searchResult.getSearchEntries())
            {
                System.out.println(e.toLDIFString());
                System.out.println();
            }

        } catch (LDAPException e) {
            e.printStackTrace();
        }
    }

}
