package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailBahanBakarActivity extends AppCompatActivity {
    private EditText edPremiumTon, edPremiumLiter, edSolarDrum, edSolarLiter,
            edOliTON, edOliLiter, edMinyakTanahTon, edMinyakTanahLiter, edMinyakMentah,
            edPertalite, edGas3kg, edGasKosong3kg,
            edGas12kg, edGasKosong12kg, edBatubara;
    private Button btnSimpan;
    String ID_TAHANAN;
    String IDBUKTIDETAIL="";
    SweetAlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bahan_bakar);
        this.getSupportActionBar().setTitle("Detail Bukti Bahan Bakar/Tambang");
        Intent i = getIntent();
        if(i.hasExtra("NIKBUKTI"))
        {
            Bundle b = getIntent().getExtras();
            ID_TAHANAN= b.getString("NIKBUKTI");
            loadBarangBukti(ID_TAHANAN);
            //Toast.makeText(this, ""+ID_TAHANAN, Toast.LENGTH_SHORT).show();
        }

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
        btnSimpan = findViewById(R.id.btnSimpanBahanBakar);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String premiumTon = edPremiumTon.getText().toString();
                String premiumLiter = edPremiumLiter.getText().toString();
                String solarDrum = edSolarDrum.getText().toString();
                String solarLiter = edSolarLiter.getText().toString();
                String oliTon = edOliTON.getText().toString();
                String oliLiter = edOliLiter.getText().toString();
                String minyakTon = edMinyakTanahTon.getText().toString();
                String minyakLiter = edMinyakTanahLiter.getText().toString();
                String minyakMentah = edMinyakMentah.getText().toString();
                String pertalite = edPertalite.getText().toString();
                String gas3kg = edGas3kg.getText().toString();
                String gasKsong3kg = edGasKosong3kg.getText().toString();
                String gas12kg = edGas12kg.getText().toString();
                String gasKsong12kg = edGasKosong12kg.getText().toString();
                String batubara = edBatubara.getText().toString();

                    simpanBahanBakar(IDBUKTIDETAIL,premiumTon,premiumLiter,solarDrum,solarLiter,oliTon,oliLiter,minyakTon, minyakLiter, minyakMentah,
                            pertalite, gas3kg, gasKsong3kg, gas12kg, gasKsong12kg, batubara);

            }
        });



    }
    private void loadBarangBukti(final String idtahanan){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/detail_bb_bahan_bakar", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        IDBUKTIDETAIL = user.getString("id");
//                        Toast.makeText(DetailBahanBakarActivity.this, ""+user.getString("premium_ton"), Toast.LENGTH_SHORT).show();
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
                params.put("idunik", idtahanan);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    private void simpanBahanBakar(final String iddetail, final String premiumTon, final String premiumLiter, final String solarDrum, final String solarLiter, final String oliTon, final String oliLiter, final String minyakTon, final String minyakLiter, final String  minyakMentah,
                                  final String pertalite, final String  gas3kg, final String gasKsong3kg, final String  gas12kg, final String  gasKsong12kg, final String  batubara){
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/insert_bb_tambang", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();

                        Intent i = new Intent(DetailBahanBakarActivity.this, DetailBuktiActivity.class);
                        i.putExtra("idunik",ID_TAHANAN);
                        startActivity(i);
                        finish();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();

                        Intent i = new Intent(DetailBahanBakarActivity.this, DetailBuktiActivity.class);
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
                params.put("id", iddetail);
                params.put("id_uniq", ID_TAHANAN);
                params.put("premium_ton", premiumTon);
                params.put("premium_liter", premiumLiter);
                params.put("solar_drum", solarDrum);
                params.put("solar_liter", solarLiter);
                params.put("oli_ton", oliTon);
                params.put("oli_liter", oliLiter);
                params.put("minyak_ton", minyakTon);
                params.put("minyak_liter", minyakLiter);
                params.put("minyak_mentah", minyakMentah);
                params.put("pertalite", pertalite);
                params.put("gas_3", gas3kg);
                params.put("gas_3_ksng", gasKsong3kg);
                params.put("gas_12", gas12kg);
                params.put("gas_12_ksng", gasKsong12kg);
                params.put("batubara", batubara);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailBahanBakarActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(DetailBahanBakarActivity.this, DetailBuktiActivity.class);
        i.putExtra("idunik",ID_TAHANAN);
        startActivity(i);
        finish();
    }
}