package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplikasianaknegeri.e_tahti.helper.APIConfig;
import com.aplikasianaknegeri.e_tahti.helper.SQLiteHandler;
import com.aplikasianaknegeri.e_tahti.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StatistikTanahActivity extends AppCompatActivity {

    private TextView edTanah;
    private SQLiteHandler db;
    String IDUSER;
    SweetAlertDialog dialog;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_tanah);
        this.getSupportActionBar().setTitle("Statistik Tanah");
        edTanah = findViewById(R.id.edTanah);
        db = new SQLiteHandler(StatistikTanahActivity.this);
        session = new SessionManager(StatistikTanahActivity.this);
        if (!session.isLoggedIn()) {
            logoutUser();
        }else{
            HashMap<String, String> user = db.getUserDetails();
            IDUSER = user.get("kode");
            ProfilUser(IDUSER);
        }
    }
    public void ProfilUser(final String id_user){
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/user_detail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        String idsatker = user.getString("satker");
                        loadBarangBukti(idsatker);
                        dialog.dismiss();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("id_user", id_user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void loadBarangBukti(final String id_satker){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/statistik_bb_tanah", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        edTanah.setText(user.getString("tanah"));
                    } else if (Msg.equals("false")) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("satker", id_satker);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUser();
        Intent intent = new Intent(StatistikTanahActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(StatistikTanahActivity.this, StatistikBuktiActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}