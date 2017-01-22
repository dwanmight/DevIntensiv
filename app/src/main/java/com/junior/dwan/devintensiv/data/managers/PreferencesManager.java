package com.junior.dwan.devintensiv.data.managers;

import android.content.SharedPreferences;
import android.net.Uri;

import com.junior.dwan.devintensiv.utils.ConstantManager;
import com.junior.dwan.devintensiv.utils.DevIntensiveApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Might on 10.09.2016.
 */
public class PreferencesManager {

    private SharedPreferences mSharedPreferences;
    private static final String [] USER_FIELDS={ConstantManager.USER_PHONE_KEY,ConstantManager.USER_MAIL_KEY,ConstantManager.USER_VK_KEY,ConstantManager.USER_GIT_KEY,ConstantManager.USER_BIO_KEY};

    public PreferencesManager() {
        this.mSharedPreferences= DevIntensiveApplication.getSharedPreferences();
    }

    public void saveUserProfileData(List<String> userFields){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        for(int i =0;i<USER_FIELDS.length;i++){
            editor.putString(USER_FIELDS[i],userFields.get(i));
        }
        editor.apply();
    }

    public List<String> loadUserProfileData(){
        List<String> userFields =new ArrayList<>();
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_PHONE_KEY,"null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_MAIL_KEY,"null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_VK_KEY,"null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_GIT_KEY,"null"));
        userFields.add(mSharedPreferences.getString(ConstantManager.USER_BIO_KEY, "null"));

        return userFields;
    }

    public void saveUserPhoto(Uri uri){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_PHOTO_KEY, uri.toString());
        editor.apply();
    }

    public Uri loadUserPhoto(){
        return Uri.parse(mSharedPreferences.getString(ConstantManager.USER_PHOTO_KEY, "android.resource://com.junior.dwan.devintensiv/drawable/myphoto"));
    }

    public void saveAuthToken(String authToken){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(ConstantManager.AUTH_TOKEN,authToken);
        editor.apply();
    }

    public String getAuthToken(){
        return mSharedPreferences.getString(ConstantManager.AUTH_TOKEN, null);
    }

    public void saveUserId(String userId){
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString(ConstantManager.USER_ID_KEY,userId);
        editor.apply();
    }

    public String getUserId(){
        return mSharedPreferences.getString(ConstantManager.USER_ID_KEY,null);
    }

}
