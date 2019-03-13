package com.usrmngr.client.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private JSONObject jsonObject;


    public User(){
       this.jsonObject = createJSONObj("","","","","");
    }
    public User(JSONObject user) {
        this.jsonObject = new JSONObject(user);
    }

    public User(String id,String displayName, String fName, String lName, String mInitial) {
        this.jsonObject = createJSONObj(displayName, fName, lName, mInitial, id);
    }

    public User(String id,String displayName) {
        this.jsonObject = createJSONObj(id, displayName, "", "", "");

    }

    public static void main(String[] args) {
        User user = new User("1","Jonny Test");
        System.out.printf("User: %s\n", user);
    }
    public String getId() {
        return getJSONStr("id");
    }
    private JSONObject createJSONObj(String id, String displayName, String fName, String lName, String mInitial) {
        jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("display_name", displayName);
            jsonObject.put("first_name", fName);
            jsonObject.put("last_name", lName);
            jsonObject.put("last_name", lName);
            jsonObject.put("middle_initial", mInitial);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Will return the string asked for from the internal json object.
     *
     * @param string the string to retrieve.
     * @return the selected attribute or empty if attribute not found.
     */
    private String getJSONStr(String string) {
        String result = "";
        try {
            result = jsonObject.getString(string);
        } catch (JSONException ignored) {
        }
        return result;
    }


    public String getDisplayName() {
        return getJSONStr("display_name");
    }

    public void setDisplayName(String displayName) {
        //
    }

    public String getfName() {
        return getJSONStr("first_name");
    }

    public void setfName(String fName) {
    }

    public String getlName() {
        return getJSONStr("last_name");
    }

    public void setlName(String lName) {
    }

    public String getmInitial() {
        return getJSONStr("middle_initial");
    }

    public void setmInitial(String mInitial) {
    }

    public String getTitle() {
        return getJSONStr("title");
    }

    public void setTitle(String title) {
    }

    public String getDepartment() {
        return getJSONStr("department");
    }

    public void setDepartment(String department) {
    }

    public String getOffice() {
        return getJSONStr("office");
    }

    public void setOffice(String office) {
    }

    public String getManger() {
        return getJSONStr("manager_id");
    }

    public void setManger(String manger) {
    }

    public String getOfficeNumber() {
        return getJSONStr("office_number");
    }

    public void setOfficeNumber(String officeNumber) {
    }

    public String getMobilNumber() {
        return getJSONStr("mobil_number");
    }

    public void setMobilNumber(String mobilNumber) {
    }

    @Override
    public String toString() {
        return  jsonObject.toString();
    }


}
