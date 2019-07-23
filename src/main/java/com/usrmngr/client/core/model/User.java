package com.usrmngr.client.core.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class User {
    private HashMap<String, String> user;

    public User(JSONObject jsonObject) {
        this.user = new HashMap<>();
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                user.put(key.toLowerCase(), jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String[] getAttributes() {
        ArrayList<String> arrayList = new ArrayList<>(user.size());
        Collections.addAll(arrayList, user.keySet().toArray(new String[0]));
        return arrayList.toArray(new String[user.size()]);
    }

    public String getAttribute(String string) {
        return user.getOrDefault(string.toLowerCase(), "");
    }

    private void setAttribute(String key) {
        setAttribute(key.toLowerCase(), "");
    }

    private void setAttribute(String key, String value) {
        this.user.put(key.toLowerCase(), value);
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            String key;
            String value;
            for (Map.Entry<String, String> attribute : user.entrySet()) {
                key = attribute.getKey();
                value = attribute.getValue();
                jsonObject.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        String dn = this.getAttribute("displayName");
        String cn =  this.getAttribute("cn");
        return  dn.isEmpty() ? cn : dn;
    }

    public static void main(String[] args) {
        User user = null;
        String DATA_PATH = "src/main/resources/samples/MOCK_DATA.json";
        JSONArray data = null;
        try {
            data = new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            assert data != null;
            user = new User(data.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(user);
    }

}
