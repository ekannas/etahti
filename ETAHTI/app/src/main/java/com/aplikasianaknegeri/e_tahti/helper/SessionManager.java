package com.aplikasianaknegeri.e_tahti.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager  {
    private static String TAG = SessionManager.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    int PRIVATE_MODE= 0;
    private static final String PREF_NAME ="WongSoloPosKasir";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context){
        this._context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setLogin(boolean isLoggedIn)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn(){return pref.getBoolean(KEY_IS_LOGGEDIN, false);}
}
