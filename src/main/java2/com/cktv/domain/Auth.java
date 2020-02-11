package com.cktv.domain;

/**
 * Created by hws on 2016/5/29.
 */
public class Auth {
    private long auth_id;
    private String auth_token;

    public long getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(long auth_id) {
        this.auth_id = auth_id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }



}
