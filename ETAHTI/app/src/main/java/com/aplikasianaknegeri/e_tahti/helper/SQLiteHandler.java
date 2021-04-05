package com.aplikasianaknegeri.e_tahti.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler  extends SQLiteOpenHelper {

    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "usertahti";
    private static final String TABLE_USER = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_KODE = "kode";
    private static final String KEY_NRP = "nrp";

    public SQLiteHandler(Context context){super(context, DATABASE_NAME, null, DATABASE_VERSION);}
    String CREATE_LOGIN_TABLE ="CREATE TABLE "+TABLE_USER+"("
            +KEY_ID + " INTEGER PRIMARY KEY,"
            +KEY_KODE + " TEXT,"
            +KEY_NRP + " TEXT"+")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOGIN_TABLE);
        Log.d(TAG, "Database table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
        onCreate(db);
    }

    public void addUser(String kode, String nrp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KODE, kode);
        values.put(KEY_NRP, nrp);
        long id = db.insert(TABLE_USER, null, values);
        db.close();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        String selectQuery = "SELECT * FROM "+TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            user.put("kode", cursor.getString(1));
            user.put("nrp", cursor.getString(2));
        }
        cursor.close();
        db.close();
        return user;
    }

    public void deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }
}
