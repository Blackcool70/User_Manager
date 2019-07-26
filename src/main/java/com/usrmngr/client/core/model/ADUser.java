package com.usrmngr.client.core.model;

import org.json.JSONObject;

public class ADUser extends ADObject {
    ADUser manager;
    public ADUser(JSONObject adUserASJson) {
        super(adUserASJson);
    }
    public ADUser(){

    }

    public String getDN() {
        return getUserAttribute("dn");
    }

    public String getCN() {
        return getUserAttribute("cn");
    }

    public String getFirstName() {
        return getUserAttribute(("givenName"));
    }

    public String getLastName() {
        return getUserAttribute("lastName");
    }

    public String getInitials() {
        return getUserAttribute("initials");
    }

    public String getDisplayName() {
        return getUserAttribute("displayName");
    }

    public String getDescription() {
        return getUserAttribute("description");
    }

    String getOffice() {
        return getUserAttribute("physicalDeliveryOfficeName");
    }

    /**
     * Returns the attribute value as "<empty>" if the key returns an empty string
     * @param key
     * @return the String value or "<empty>" if the string value is empty.
     */
    String getUserAttribute(String key){
        String value = getAttribute(key);
        return value == null || value.equals("")  ? "<empty>" : value;
    }

    String getTelephone() {
        return getUserAttribute("telephoneNumber");
    }

    String getEmail() {
        return getUserAttribute("mail");
    }

    String getLogonName() {
        return getUserAttribute("userPrincipalName");
    }
    public String toString(){
        return  getDisplayName();
    }

}
