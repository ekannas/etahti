package com.aplikasianaknegeri.e_tahti.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.aplikasianaknegeri.e_tahti.model.user;

import java.util.Date;
import java.util.HashMap;

public class SessionHandler {
    private static final String PREF_NAME = "UserSessionTahti";
    private static final String KEY_KODE_USER = "kode_user";
    private static final String KEY_NRP = "nrp";
    private static final String KEY_EXPIRES = "expires";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext){
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }
    public void LoginUser(String kode)
    {

        mEditor.putString(KEY_KODE_USER, kode);
        Date date = new Date();

        long milis = date.getTime() +(7*24*60*60*1000);
        mEditor.putLong(KEY_EXPIRES, milis);
        mEditor.commit();
    }

    public boolean isLoggedIn(){
        Date currentDate = new Date();
        long milis = mPreferences.getLong(KEY_EXPIRES, 0);
        if(milis == 0)
        {
            return false;
        }
        Date expiryDate = new Date(milis);
        return currentDate.before(expiryDate);
    }

    public HashMap<String, String> getProfileDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_KODE_USER, mPreferences.getString(KEY_KODE_USER, null));
        return user;
    }

    public user getUserDetail(){
        if(isLoggedIn()){
            return null;
        }
        user user = new user();
        user.setId(mPreferences.getString(KEY_KODE_USER, ""));
        user.setNrp(mPreferences.getString(KEY_NRP,""));
        return user;
    }
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }
}
