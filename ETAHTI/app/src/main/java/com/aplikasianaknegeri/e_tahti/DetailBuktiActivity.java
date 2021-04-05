package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplikasianaknegeri.e_tahti.helper.APIConfig;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailBuktiActivity extends AppCompatActivity {
    CardView cvUang, cvDokumen, cvLogam, cvTanah, cvBangunan, cvMesin,
            cvSenjata, cvBahanBakar, cvPersediaan;

    String NIKBUKTI;

    EditText tvKode, tvTanggal, tvNIK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bukti);
        this.getSupportActionBar().setTitle("Detail Barang Bukti");
        cvUang = findViewById(R.id.cardUangDetail);
        cvDokumen = findViewById(R.id.cardDokumenDetail);
        cvLogam = findViewById(R.id.cardLogamDetail);
        cvTanah = findViewById(R.id.cardTanahDetail);
        cvBangunan = findViewById(R.id.cardGedungDetail);
        cvMesin = findViewById(R.id.cardMesinDetail);
        cvSenjata = findViewById(R.id.cardSenjataDetail);
        cvBahanBakar = findViewById(R.id.cardBahanTambangDetail);
        cvPersediaan = findViewById(R.id.cardPersediaanDetail);
        tvKode = findViewById(R.id.edKodeRegDetail);
        tvTanggal = findViewById(R.id.edTanggalRegDetail);
        tvNIK= findViewById(R.id.edNIKRegDetail);

        Intent i = getIntent();
        if(i.hasExtra("idunik"))
        {
            Bundle b = getIntent().getExtras();
            NIKBUKTI= b.getString("idunik");
            loadBuktiDetail(NIKBUKTI);

        }
        cvBahanBakar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBuktiActivity.this, DetailBahanBakarActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvUang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBuktiActivity.this, DetailUangActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();

            }
        });
        cvTanah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBuktiActivity.this, DetailBuktiTanahActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();

            }
        });
        cvBangunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetailBuktiActivity.this, DetailGedungActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBuktiActivity.this, DetailDokumenActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvLogam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBuktiActivity.this, DetailLogamActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvSenjata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBuktiActivity.this, DetailSenjataActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailBuktiActivity.this, DetailMesinActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();

            }
        });
        cvPersediaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetailBuktiActivity.this, DetailPersediaanActivity.class);
                i.putExtra("NIKBUKTI", NIKBUKTI);
                startActivity(i);
                finish();
            }
        });

    }
    private void loadBuktiDetail(final String NIKBUKTI){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/bukti_detail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        tvNIK.setText(user.getString("nik"));
                        tvTanggal.setText(user.getString("tanggal"));
                        tvKode.setText(user.getString("kode"));
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
                params.put("idunik", NIKBUKTI);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DetailBuktiActivity.this, BarangBuktiActivity.class);
        startActivity(i);
        finish();
    }
}