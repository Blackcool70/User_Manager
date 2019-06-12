package com.usrmngr.server.core.model.RMI;

import java.io.Serializable;

public class Request implements Serializable {
    private String name;
    public void setName(String name){
        this.name =  name;

    }

    @Override
    public String toString() {
        return name;
    }
}
