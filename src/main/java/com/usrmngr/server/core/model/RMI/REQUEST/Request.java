package com.usrmngr.server.core.model.RMI.REQUEST;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.UUID;


/**
 * A request is a instruction to the SEr
 */
public class Request<T> implements Serializable {
    private final UUID ID;
    private final String DATA;

    private RESULT result;
    private String msg;
    private final TYPE REQUEST_TYPE;

    public Request(T data, TYPE type) {
        this.ID = UUID.randomUUID();
        this.REQUEST_TYPE = type;
        this.DATA = (new Gson()).toJson(data,data.getClass());
        this.result = RESULT.EMPTY;
        this.msg = "";
    }

    private String getMsg(){
       return  msg;
    }
    private String getData(){
        return this.DATA;
    }

    private com.usrmngr.server.core.model.RMI.REQUEST.TYPE getType() {
        return  this.REQUEST_TYPE;
    }

    public void setResult(RESULT result) {
        this.result = result;
    }

    public RESULT getResult() {
        return this.result;
    }

    private String getID() {
        return this.ID.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(getID());
        sb.append("\nTYPE: ").append(getType().toString());
        sb.append("\nRESULT: ").append(getResult().toString());
        sb.append("\nMSG: ").append(getMsg());
        sb.append("\nDATA:\n").append(this.DATA);
        return sb.toString();
    }
    public static void main(String[] args) {
        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append("I am groot");

        Request request = new Request(stringBuilder,TYPE.CREATE);
        Gson gson = new Gson();
        StringBuilder sb2 = gson.fromJson(request.getData(),StringBuilder.class);
        System.out.println(sb2.toString());
    }
}
