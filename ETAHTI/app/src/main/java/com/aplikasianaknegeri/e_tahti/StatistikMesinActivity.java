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

public class StatistikMesinActivity extends AppCompatActivity {
    private TextView edRoda2, edRoda3, edRoda4, edRodalbh4, edTruck, edDumpTruck, edTangki, edTrailler,
            edSepeda, edDozer, edExcavator, edLoader, edTractor,
            edDieselHisap, edGenset,
            edKapal, edKapalBarang, edKapalTunda,edTongkang, edSampan, edKlotok,
            edMejaKursi, edLemari, edLaptop, edPrinter, edKomputer, edMonitor, edMesinFC, edKipasAngim, edHandphone, edCCTV, edTimbangan, edStereo, edLED;
    private SQLiteHandler db;
    String IDUSER;
    SweetAlertDialog dialog;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_mesin);
        this.getSupportActionBar().setTitle("Statistik Mesin");
        edRoda2 = findViewById(R.id.edRoda2);
        edRoda3 = findViewById(R.id.edRoda3);
        edRoda4 = findViewById(R.id.edRoda4);
        edRodalbh4 = findViewById(R.id.edRodalbh4);
        edTruck = findViewById(R.id.edTruck);
        edDumpTruck = findViewById(R.id.edDumpTruck);
        edTangki = findViewById(R.id.edTangkiBBM);
        edTrailler = findViewById(R.id.edTrailer);
        edSepeda = findViewById(R.id.edSepeda);
        edDozer = findViewById(R.id.edDozer);
        edExcavator = findViewById(R.id.edExcavator);
        edLoader = findViewById(R.id.edLoader);
        edTractor = findViewById(R.id.edTractor);
        edDieselHisap = findViewById(R.id.edHisalDiesel);
        edGenset = findViewById(R.id.edGenset);
        edKapal = findViewById(R.id.edKapal);
        edKapalBarang = findViewById(R.id.edBarangKargo);
        edKapalTunda = findViewById(R.id.edTugBoat);
        edTongkang = findViewById(R.id.edTongkang);
        edSampan = findViewById(R.id.edSampan);
        edKlotok = findViewById(R.id.edKlotok);
        edMejaKursi = findViewById(R.id.edMejaKursi);
        edLemari = findViewById(R.id.edLemari);
        edLaptop = findViewById(R.id.edLaptop);
        edPrinter = findViewById(R.id.edPrinter);
        edKomputer = findViewById(R.id.edKomputer);
        edMonitor = findViewById(R.id.edMonitor);
        edMesinFC = findViewById(R.id.edMesinFC);
        edKipasAngim = findViewById(R.id.edKipasAngin);
        edHandphone= findViewById(R.id.edHandphone);
        edCCTV = findViewById(R.id.edCCTV);
        edTimbangan = findViewById(R.id.edTimbangan);
        edStereo = findViewById(R.id.edStereo);
        edLED = findViewById(R.id.edLED);
        db = new SQLiteHandler(StatistikMesinActivity.this);
        session = new SessionManager(StatistikMesinActivity.this);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/statistik_bb_mesin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        edRoda2.setText(user.getString("roda_2"));
                        edRoda3.setText(user.getString("roda_3"));
                        edRoda4.setText(user.getString("roda_4"));
                        edRodalbh4.setText(user.getString("roda_lebih_4"));
                        edTruck.setText(user.getString("truck"));
                        edDumpTruck.setText(user.getString("dump_truck"));
                        edTangki.setText(user.getString("tangki_bbm"));
                        edTrailler.setText(user.getString("trailer"));
                        edSepeda.setText(user.getString("sepeda"));
                        edDozer.setText(user.getString("dozer"));
                        edExcavator.setText(user.getString("excavator"));
                        edLoader.setText(user.getString("loader"));
                        edTractor.setText(user.getString("tractor"));
                        edDieselHisap.setText(user.getString("hisap_diesel"));
                        edGenset.setText(user.getString("genset"));
                        edKapal.setText(user.getString("kapal"));
                        edKapalBarang.setText(user.getString("kapal_barang"));
                        edKapalTunda.setText(user.getString("kapal_tunda"));
                        edTongkang.setText(user.getString("tongkang"));
                        edSampan.setText(user.getString("sampan"));
                        edKlotok.setText(user.getString("klotok"));
                        edMejaKursi.setText(user.getString("meja"));
                        edLemari.setText(user.getString("lemari"));
                        edLaptop.setText(user.getString("laptop"));
                        edPrinter.setText(user.getString("printer"));
                        edKomputer.setText(user.getString("komputer"));
                        edMonitor.setText(user.getString("monitor"));
                        edMesinFC.setText(user.getString("mesin_fc"));
                        edKipasAngim.setText(user.getString("kipas_angin"));
                        edHandphone.setText(user.getString("handphone"));
                        edCCTV.setText(user.getString("cctv"));
                        edTimbangan.setText(user.getString("timbangan"));
                        edStereo.setText(user.getString("stereo"));
                        edLED.setText(user.getString("tv"));
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
        Intent intent = new Intent(StatistikMesinActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(StatistikMesinActivity.this, StatistikBuktiActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}