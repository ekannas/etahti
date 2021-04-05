package com.aplikasianaknegeri.e_tahti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplikasianaknegeri.e_tahti.adapter.BuktiAdapter;
import com.aplikasianaknegeri.e_tahti.adapter.TahananAdapter;
import com.aplikasianaknegeri.e_tahti.helper.APIConfig;
import com.aplikasianaknegeri.e_tahti.helper.SQLiteHandler;
import com.aplikasianaknegeri.e_tahti.helper.SessionManager;
import com.aplikasianaknegeri.e_tahti.model.barangbukti;
import com.aplikasianaknegeri.e_tahti.model.tahanan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.aplikasianaknegeri.e_tahti.helper.APIConfig.urlAPI;

public class BarangBuktiActivity extends AppCompatActivity {

    private SQLiteHandler db;
    SweetAlertDialog dialog;
    private SessionManager session;

    String IDUSER;
    EditText edCari;
    RecyclerView rvBukti;
    ArrayList<barangbukti> barangbuktiArray;
    BuktiAdapter buktiAdapter;

    String param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_bukti);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Barang Bukti");
        edCari = findViewById(R.id.edCariNikBukti);
        rvBukti = findViewById(R.id.recyclerBarangBukti);
        edCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                param = edCari.getText().toString();
                loadBuktiSearch(param);
            }
        });
        db = new SQLiteHandler(BarangBuktiActivity.this);
        session = new SessionManager(BarangBuktiActivity.this);
        if (!session.isLoggedIn()) {
            logoutUser();
        }else{
            HashMap<String, String> user = db.getUserDetails();
            IDUSER = user.get("kode");
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) BarangBuktiActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View inputNik = inflater.inflate(R.layout.input_nik, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BarangBuktiActivity.this);
                alertDialogBuilder.setView(inputNik);
                final AlertDialog alertDialog = alertDialogBuilder.show();
                final int sdk = android.os.Build.VERSION.SDK_INT;
                final EditText edNIK = inputNik.findViewById(R.id.edNIKBukti);
                final Button btnHitung = inputNik.findViewById(R.id.btnSimpanNIK);
                btnHitung.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String nikbukti = edNIK.getText().toString();
                        registerBarangBukti(nikbukti, IDUSER);
//
//                        Intent i = new Intent(BarangBuktiActivity.this, BarangBuktiTambahActivity.class);
//                        i.putExtra("NIK", nikbukti);
//                        startActivity(i);
//                        finish();
                    }
                });
            }
        });

        loadBukti();
    }
    private void loadBuktiSearch(final String param){
        StringRequest stringTahanan = new StringRequest(Request.Method.POST, urlAPI + "/api/bukti_search", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    barangbuktiArray = new ArrayList<>();
                    JSONArray dataArray = obj.getJSONArray("result");
                    for (int i = 0; i < dataArray.length(); i++) {
                        barangbukti buktiModel = new barangbukti();
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        buktiModel.setId(dataObj.getString("idunik"));

                        buktiModel.setTanggal(dataObj.getString("tanggal"));
                        buktiModel.setNik(dataObj.getString("nik"));
                        buktiModel.setKode(dataObj.getString("kode"));
                        barangbuktiArray.add(buktiModel);
                    }
                    setupRecyclerBukti();
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
                params.put("param", param);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BarangBuktiActivity.this);
        requestQueue.add(stringTahanan);
    }
    private void loadBukti(){
        StringRequest stringTahanan = new StringRequest(Request.Method.POST, urlAPI + "/api/bukti", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    barangbuktiArray = new ArrayList<>();
                    JSONArray dataArray = obj.getJSONArray("result");
                    for (int i = 0; i < dataArray.length(); i++) {
                        barangbukti buktiModel = new barangbukti();
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        buktiModel.setId(dataObj.getString("idunik"));

                        buktiModel.setTanggal(dataObj.getString("tanggal"));
                        buktiModel.setNik(dataObj.getString("nik"));
                        buktiModel.setKode(dataObj.getString("kode"));
                        barangbuktiArray.add(buktiModel);
                    }
                    setupRecyclerBukti();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(BarangBuktiActivity.this);
        requestQueue.add(stringTahanan);
    }
    private void setupRecyclerBukti(){
        buktiAdapter = new BuktiAdapter(BarangBuktiActivity.this, barangbuktiArray);
        rvBukti.setAdapter(buktiAdapter);
        rvBukti.setLayoutManager(new LinearLayoutManager(BarangBuktiActivity.this, LinearLayoutManager.VERTICAL, false));
    }
    private void registerBarangBukti(final String nik, final String iduser)
    {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/register_barangbukti", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();
                        JSONObject user = obj.getJSONObject("user");
                        String idunik = user.getString("idunik");
                        Intent i = new Intent(BarangBuktiActivity.this, BarangBuktiTambahActivity.class);
                        i.putExtra("idunik",idunik);
                        startActivity(i);
                        finish();
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
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nik", nik);
                params.put("idinput", iduser);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BarangBuktiActivity.this);
        requestQueue.add(stringRequest);
    }
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUser();
        Intent intent = new Intent(BarangBuktiActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(BarangBuktiActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}