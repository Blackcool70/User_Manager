package com.usrmngr.client.GUI.models;

import org.junit.Test;
public class ADConnectorTest {
    @Test
    public void testToString()
    {
        ADConnector adConnector = new ADConnector();
        adConnector.setConfigs("192.168.1.2", 389, "DC=lab,DC=net",
                "cn=Administrator,ou=Users,ou=Company,dc=lab,dc=net", "xxxxxxxx");
        String result = adConnector.toString();
        String expected = "server:192.168.1.2\n" +
                "port:389\n" +
                "baseDN:DC=lab,DC=net\n" +
                "bindCN:cn=Administrator,ou=Users,ou=Company,dc=lab,dc=net\n" +
                "password:xxxxxxxx\n";

//        System.out.println(result);
//        System.out.println(expected);
        assert (result.equals(expected));
    }

    @Test
    public void main() {
    }

    @Test
    public void setConfigs() {
    }

    @Test
    public void connect() {
    }

    @Test
    public void isConnected() {
    }

    @Test
    public void disconnect() {
    }

    @Test
    public void getResultCode() {
    }

    @Test
    public void getErrorMessageFromServer() {
    }

}
