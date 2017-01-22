package com.junior.dwan.devintensiv.data.network.req;

/**
 * Created by Might on 15.09.2016.
 */
public class UserLoginReq {
    private String email;
    private String password;

    public UserLoginReq(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
