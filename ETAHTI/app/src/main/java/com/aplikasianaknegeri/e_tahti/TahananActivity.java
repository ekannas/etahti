package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplikasianaknegeri.e_tahti.adapter.TahananAdapter;
import com.aplikasianaknegeri.e_tahti.model.tahanan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.aplikasianaknegeri.e_tahti.helper.APIConfig.urlAPI;

public class TahananActivity extends AppCompatActivity {
    EditText edCari;
    RecyclerView rvTahanan;
    ArrayList<tahanan> tahananArray;
    TahananAdapter tahananAdapter;
    String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahanan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Tahanan");
        edCari = findViewById(R.id.edCari);
        rvTahanan = findViewById(R.id.recyclerTahanan);
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
                loadTahananSearch(param);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TahananActivity.this, TahananTambahActivity.class);
                startActivity(i);
                finish();
            }
        });
        loadTahanan();
    }
    private void loadTahananSearch(final String param){
        StringRequest stringTahanan = new StringRequest(Request.Method.POST, urlAPI + "/api/tahanan_search", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    tahananArray = new ArrayList<>();
                    JSONArray dataArray = obj.getJSONArray("result");
                    for (int i = 0; i < dataArray.length(); i++) {
                        tahanan tahananModel = new tahanan();
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        tahananModel.setId(dataObj.getString("id"));

                        tahananModel.setMasuk(dataObj.getString("masuk"));
                        tahananModel.setLokasi(dataObj.getString("lokasi"));
                        tahananModel.setNik(dataObj.getString("ktp"));
                        tahananModel.setNama(dataObj.getString("nama"));
                        tahananModel.setUsia(dataObj.getString("usia"));
                        tahananModel.setJenkel(dataObj.getString("jenis_kelamin"));
                        tahananModel.setSatker(dataObj.getString("satker"));
                        tahananArray.add(tahananModel);
                    }
                    setupRecyclerTahanan();
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
        RequestQueue requestQueue = Volley.newRequestQueue(TahananActivity.this);
        requestQueue.add(stringTahanan);
    }
    private void loadTahanan(){
        StringRequest stringTahanan = new StringRequest(Request.Method.POST, urlAPI + "/api/tahanan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    tahananArray = new ArrayList<>();
                    JSONArray dataArray = obj.getJSONArray("result");
                    for (int i = 0; i < dataArray.length(); i++) {
                        tahanan tahananModel = new tahanan();
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        tahananModel.setId(dataObj.getString("id"));

                        tahananModel.setMasuk(dataObj.getString("masuk"));
                        tahananModel.setLokasi(dataObj.getString("lokasi"));
                        tahananModel.setNik(dataObj.getString("ktp"));
                        tahananModel.setNama(dataObj.getString("nama"));
                        tahananModel.setUsia(dataObj.getString("usia"));
                        tahananModel.setJenkel(dataObj.getString("jenis_kelamin"));
                        tahananModel.setSatker(dataObj.getString("satker"));
                        tahananArray.add(tahananModel);
                    }
                    setupRecyclerTahanan();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(TahananActivity.this);
        requestQueue.add(stringTahanan);
    }
    private void setupRecyclerTahanan(){
        tahananAdapter = new TahananAdapter(TahananActivity.this, tahananArray);
        rvTahanan.setAdapter(tahananAdapter);
        rvTahanan.setLayoutManager(new LinearLayoutManager(TahananActivity.this, LinearLayoutManager.VERTICAL, false));
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(TahananActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}