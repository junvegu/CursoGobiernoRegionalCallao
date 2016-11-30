package com.bixspace.ciclodevida.data;

import java.io.Serializable;

/**
 * Created by junior on 30/11/16.
 */

public class AccesToken implements Serializable {

    private String id;
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
