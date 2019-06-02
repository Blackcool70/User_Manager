package com.usrmngr.server.core.model;

import java.io.Serializable;
import java.util.UUID;

public class Request implements Serializable {
    private final UUID requestID = java.util.UUID.randomUUID();
    private final UUID requesterID;
    Request(UUID requesterID){
        this.requesterID = requesterID;
    }

    public UUID getRequestID() {
        return requestID;
    }

    public UUID getRequesterID() {
        return requesterID;
    }
}
