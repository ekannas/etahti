package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.aplikasianaknegeri.e_tahti.helper.APIConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuktiLogamActivity extends AppCompatActivity {
    private EditText edgelang, edKalung, edCincin, edAnting;
    private Button btnSimpan;
    SweetAlertDialog dialog;
    String ID_TAHANAN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_logam);
        this.getSupportActionBar().setTitle("Bukti Logam Mulia");
        Intent i = getIntent();
        if(i.hasExtra("NIKBUKTI"))
        {
            Bundle b = getIntent().getExtras();
            ID_TAHANAN= b.getString("NIKBUKTI");
        }
        btnSimpan = findViewById(R.id.btnSimpanLogam);
        edgelang = findViewById(R.id.edGelangEmas);
        edKalung = findViewById(R.id.edKalungEmas);
        edCincin = findViewById(R.id.edCincinEmas);
        edAnting = findViewById(R.id.edAntingEmas);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gelang = edgelang.getText().toString();
                String kalung = edKalung.getText().toString();
                String cincin = edCincin.getText().toString();
                String anting = edAnting.getText().toString();
                simpanLogam(gelang, kalung, cincin, anting);
            }
        });
    }
    private void simpanLogam(final String gelang, final String kalung, final String cincin, final String anting)
    {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/insert_bb_logam", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();

                        Intent i = new Intent(BuktiLogamActivity.this, BarangBuktiTambahActivity.class);
                        i.putExtra("idunik",ID_TAHANAN);
                        startActivity(i);
                        finish();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();

                        Intent i = new Intent(BuktiLogamActivity.this, BarangBuktiTambahActivity.class);
                        i.putExtra("idunik",ID_TAHANAN);
                        startActivity(i);
                        finish();

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
                params.put("id_uniq", ID_TAHANAN);
                params.put("gelang_emas", gelang);
                params.put("kalung_emas", kalung);
                params.put("cincin_emas", cincin);
                params.put("anting_emas", anting);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BuktiLogamActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(BuktiLogamActivity.this,  BarangBuktiTambahActivity.class);
        i.putExtra("idunik",ID_TAHANAN);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}