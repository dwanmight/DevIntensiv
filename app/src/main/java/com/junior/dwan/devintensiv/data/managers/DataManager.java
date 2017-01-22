package com.junior.dwan.devintensiv.data.managers;

import android.content.Context;

import com.junior.dwan.devintensiv.data.network.RestService;
import com.junior.dwan.devintensiv.data.network.ServiceGenerator;
import com.junior.dwan.devintensiv.data.network.req.UserLoginReq;
import com.junior.dwan.devintensiv.data.network.res.UserModelRes;
import com.junior.dwan.devintensiv.utils.DevIntensiveApplication;

import retrofit2.Call;

/**
 * Created by Might on 10.09.2016.
 */
public class DataManager {
    private static DataManager INSTANCE=null;

    private Context mContext;
    private PreferencesManager mPreferencesManager;
    private RestService mRestService;

    public DataManager() {
        this.mPreferencesManager =new PreferencesManager();
        this.mRestService= ServiceGenerator.createService(RestService.class);
//        this.mContext= DevIntensiveApplication.getContext();
    }

    public static DataManager getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE=new DataManager();
        }
        return INSTANCE;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    public Context getContext(){
        return mContext;
    }



    //region=======Network=======
    Call<UserModelRes> loginUser(UserLoginReq userLoginReq){
        return loginUser(userLoginReq);
    }

    //endregion

    //region=======DataBase=======

    //endregion
}
