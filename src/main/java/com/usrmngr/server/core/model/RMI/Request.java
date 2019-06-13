package com.usrmngr.server.core.model.RMI;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.UUID;
enum RRESULT {
    EMPTY,
    SUCESS,
    ERROR,
    WARNING,
    FATAL
}

enum RTYPE {
    CREATE,
    UPDATE,
    DELETE
}
public class Request<T> implements Serializable {
    private final RTYPE TYPE;
    private final UUID ID;
    private final String DATA;

    private RRESULT result;
    private String msg;

    Request(RTYPE type, T data ) {
        this.ID = UUID.randomUUID();
        this.TYPE = type;
        this.DATA = (new Gson()).toJson(data,data.getClass());
        this.result = RRESULT.EMPTY;
        this.msg = "None";
    }

    private String getMsg(){
       return  msg;
    }
    private String getData(){
        return this.DATA;
    }

    private RTYPE getType() {
        return this.TYPE;
    }

    public void setResult(RRESULT result) {
        this.result = result;
    }

    public RRESULT getResult() {
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

        Request request = new Request(RTYPE.CREATE,stringBuilder);
        Gson gson = new Gson();
        StringBuilder sb2 = gson.fromJson(request.getData(),StringBuilder.class);
        System.out.println(sb2.toString());
    }
}
