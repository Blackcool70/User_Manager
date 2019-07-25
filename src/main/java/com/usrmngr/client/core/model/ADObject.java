package com.usrmngr.client.core.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.Attributes;

public class ADObject {
    private Attributes attributes;

    public ADObject(JSONObject jsonObject) {
        attributes = new Attributes();
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            try {
                setAttribute(key, jsonObject.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ADObject() {
        attributes = new Attributes();
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        attributes.forEach((k, v) -> jsonObject.put((String) k, v));
        return jsonObject;
    }

    public String getAttribute(String key) {
        return attributes.getValue(key);
    }

    public void setAttribute(String key, String value) {
        attributes.putValue(key, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        attributes.forEach((k, v) -> sb.append(String.format("%s=%s\n", k, v)));
        return sb.toString();
    }

    public Set<String> getAttributeSet(){
        Set<String> stringSet = new HashSet<>();
        attributes.keySet().forEach(k-> stringSet.add((String)k));
       return  stringSet;
    }
    public static void main(String[] args) {
        ADObject ADObject = null;
        String DATA_PATH = "src/main/resources/samples/MOCK_DATA.json";
        JSONArray data = null;
        try {
            data = new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            assert data != null;
            ADObject = new ADObject(data.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(ADObject);
    }

}
