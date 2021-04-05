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

public class BuktiSenjataActivity extends AppCompatActivity {
    private EditText edLarasPendek, edAmunisi, edGranatEmas;
    Button btnSimpanSenjata;
    SweetAlertDialog dialog;
    String ID_TAHANAN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_senjata);
        this.getSupportActionBar().setTitle("Bukti Senjata");
        Intent i = getIntent();
        if(i.hasExtra("NIKBUKTI"))
        {
            Bundle b = getIntent().getExtras();
            ID_TAHANAN= b.getString("NIKBUKTI");
        }
        btnSimpanSenjata = findViewById(R.id.btnSimpanSenjata);
        edLarasPendek = findViewById(R.id.edLarasPendek);
        edAmunisi = findViewById(R.id.edAmunisi);
        edGranatEmas = findViewById(R.id.edGranatEmas);
        btnSimpanSenjata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String laraspendek = edLarasPendek.getText().toString();
                String amunisi = edAmunisi.getText().toString();
                String granat = edGranatEmas.getText().toString();
                simpanSenjata(laraspendek, amunisi, granat);
            }
        });
    }
    private void simpanSenjata(final String laraspendek, final String amunisi, final String granat)
    {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/insert_bb_senjata", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();

                        Intent i = new Intent(BuktiSenjataActivity.this, BarangBuktiTambahActivity.class);
                        i.putExtra("idunik",ID_TAHANAN);
                        startActivity(i);
                        finish();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();

                        Intent i = new Intent(BuktiSenjataActivity.this, BarangBuktiTambahActivity.class);
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
                params.put("laras_pendek", laraspendek);
                params.put("amunisi", amunisi);
                params.put("granat", granat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BuktiSenjataActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(BuktiSenjataActivity.this, BarangBuktiTambahActivity.class);
        i.putExtra("idunik",ID_TAHANAN);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}