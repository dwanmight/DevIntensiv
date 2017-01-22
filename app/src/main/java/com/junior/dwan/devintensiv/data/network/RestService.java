package com.junior.dwan.devintensiv.data.network;

import com.junior.dwan.devintensiv.data.network.req.UserLoginReq;
import com.junior.dwan.devintensiv.data.network.res.UserModelRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Might on 15.09.2016.
 */
public interface RestService {

    @POST("login")
    Call<UserModelRes> loginUser (@Body UserLoginReq req);


}
