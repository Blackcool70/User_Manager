package com.usrmngr.client.models;
import com.unboundid.ldap.sdk.*;

public class LDAPTest {
    public static void main(String[] args) {
        try {
            LDAPConnection ldapConnection = new LDAPConnection();
            ldapConnection.connect("192.168.1.2", 389);
            ldapConnection.bind("CN=Administrator,OU=Users,OU=Company,DC=lab,DC=net", "xxxx");
            ldapConnection.search("dc=lab,dc=net",SearchScope.SUB,"objectClass=*");
        }catch (LDAPException e){
            e.printStackTrace();
        }

    }

}
