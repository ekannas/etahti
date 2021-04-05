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

public class StatistikTambangActivity extends AppCompatActivity {
    private TextView edPremiumTon, edPremiumLiter, edSolarDrum, edSolarLiter,
            edOliTON, edOliLiter, edMinyakTanahTon, edMinyakTanahLiter, edMinyakMentah,
            edPertalite, edGas3kg, edGasKosong3kg,
            edGas12kg, edGasKosong12kg, edBatubara;
    private SQLiteHandler db;
    String IDUSER;
    SweetAlertDialog dialog;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_tambang);
        this.getSupportActionBar().setTitle("Statistik Bukti Bahan Bakar/Tambang");
        edPremiumTon = findViewById(R.id.edPremiumTon);
        edPremiumLiter = findViewById(R.id.edPremiumLiter);
        edSolarDrum = findViewById(R.id.edSolarDrum);
        edSolarLiter = findViewById(R.id.edSolarLiter);
        edOliTON = findViewById(R.id.edOliTon);
        edOliLiter = findViewById(R.id.edOliLiter);
        edMinyakTanahTon = findViewById(R.id.edMinyakTon);
        edMinyakTanahLiter = findViewById(R.id.edMinyakLiter);
        edMinyakMentah = findViewById(R.id.edMinyakMentah);
        edPertalite = findViewById(R.id.edPertalite);
        edGas3kg = findViewById(R.id.edGas3kg);
        edGasKosong3kg = findViewById(R.id.edGas3Ksongkg);
        edGas12kg = findViewById(R.id.edGas12kg);
        edGasKosong12kg = findViewById(R.id.edGas12Ksongkg);
        edBatubara = findViewById(R.id.edGBatuBara);
        db = new SQLiteHandler(StatistikTambangActivity.this);
        session = new SessionManager(StatistikTambangActivity.this);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/statistik_bb_bahan_bakar", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        edPremiumTon.setText(user.getString("premium_ton"));
                        edPremiumLiter.setText(user.getString("premium_liter"));
                        edSolarDrum.setText(user.getString("solar_drum"));
                        edSolarLiter.setText(user.getString("solar_liter"));
                        edOliTON.setText(user.getString("oli_ton"));
                        edOliLiter.setText(user.getString("oli_liter"));
                        edMinyakTanahTon.setText(user.getString("minyak_ton"));
                        edMinyakTanahLiter.setText(user.getString("minyak_liter"));
                        edMinyakMentah.setText(user.getString("minyak_mentah"));
                        edPertalite.setText(user.getString("pertalite"));
                        edGas3kg.setText(user.getString("gas_3"));
                        edGasKosong3kg.setText(user.getString("gas_3_ksng"));
                        edGas12kg.setText(user.getString("gas_12"));
                        edGasKosong12kg.setText(user.getString("gas_12_ksng"));
                        edBatubara.setText(user.getString("batubara"));
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
        Intent intent = new Intent(StatistikTambangActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(StatistikTambangActivity.this, StatistikBuktiActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}