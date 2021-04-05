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

public class BuktiUangActivity extends AppCompatActivity {

    private EditText edRupiah, edAsing, edRinggit, edDinar, edEURO, edRupiahBank;
    private Button btnSimpan;
    SweetAlertDialog dialog;
    String ID_TAHANAN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_uang);
        this.getSupportActionBar().setTitle("Bukti Uang");
        Intent i = getIntent();
        if(i.hasExtra("NIKBUKTI"))
        {
            Bundle b = getIntent().getExtras();
            ID_TAHANAN= b.getString("NIKBUKTI");
        }
        edRupiah = findViewById(R.id.edRupiah);
        edAsing = findViewById(R.id.edAsing);
        edRinggit = findViewById(R.id.edRinggit);
        edDinar = findViewById(R.id.edDinar);
        edEURO = findViewById(R.id.edEURO);
        edRupiahBank = findViewById(R.id.edRupiahBank);
        btnSimpan = findViewById(R.id.btnSimpanUang);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rupiah =edRupiah.getText().toString();
                String asing = edAsing.getText().toString();
                String ringgit = edRinggit.getText().toString();
                String dinar = edDinar.getText().toString();
                String euro = edEURO.getText().toString();
                String rupiahbank = edRupiahBank.getText().toString();
                simpanUang(rupiah,asing,ringgit, dinar, euro, rupiahbank);
//                Intent i = new Intent(BuktiUangActivity.this, BarangBuktiActivity.class);
//                startActivity(i);
//                finish();
            }
        });
    }
    private void simpanUang(final String rupiah,final String asing,final String ringgit,final String  dinar,final String  euro,final String  rupiahbank){
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/insert_bb_uang", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();

                        Intent i = new Intent(BuktiUangActivity.this, BarangBuktiTambahActivity.class);
                        i.putExtra("idunik",ID_TAHANAN);
                        startActivity(i);
                        finish();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();

                        Intent i = new Intent(BuktiUangActivity.this, BarangBuktiTambahActivity.class);
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
                params.put("brangkas_rupiah", rupiah);
                params.put("brangkas_us", asing);
                params.put("brangkas_ringgit", ringgit);
                params.put("brangkas_dinar", dinar);
                params.put("brangkas_euro", euro);
                params.put("bank_rupiah", rupiahbank);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BuktiUangActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(BuktiUangActivity.this,  BarangBuktiTambahActivity.class);
        i.putExtra("idunik",ID_TAHANAN);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}