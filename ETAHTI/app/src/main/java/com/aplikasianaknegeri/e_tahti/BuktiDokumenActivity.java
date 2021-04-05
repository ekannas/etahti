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

public class BuktiDokumenActivity extends AppCompatActivity {
    EditText edCek, edBukuTab, edATM, edSertifikat, edRekKorang, edSuratPerjanjian, edKwitansi, edFaktur, edBPKB, edSTNK, edSIM, edKTP;
    Button btnSimpan;
    SweetAlertDialog dialog;
    String ID_TAHANAN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bukti_dokumen);
        this.getSupportActionBar().setTitle("Bukti Dokumen/Surat Berharga");
        Intent i = getIntent();
        if(i.hasExtra("NIKBUKTI"))
        {
            Bundle b = getIntent().getExtras();
            ID_TAHANAN= b.getString("NIKBUKTI");
        }
        btnSimpan = findViewById(R.id.btnSimpanDokumen);
        edCek = findViewById(R.id.edCek);
        edBukuTab = findViewById(R.id.edBukuTabungan);
        edATM = findViewById(R.id.edATM);
        edSertifikat = findViewById(R.id.edSertifikat);
        edRekKorang =findViewById(R.id.edKoran);
        edSuratPerjanjian = findViewById(R.id.edPerjanjian);
        edKwitansi = findViewById(R.id.edKwitansi);
        edFaktur = findViewById(R.id.edFaktur);
        edBPKB = findViewById(R.id.edBPKPB);
        edSTNK = findViewById(R.id.edSTNK);
        edSIM = findViewById(R.id.edSIM);
        edKTP = findViewById(R.id.edKTP);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cek = edCek.getText().toString();
                String bukutab = edBukuTab.getText().toString();
                String atm = edATM.getText().toString();
                String sertifikat = edSertifikat.getText().toString();
                String rekkoran = edRekKorang.getText().toString();
                String suratper = edSuratPerjanjian.getText().toString();
                String kwitansi = edKwitansi.getText().toString();
                String faktur = edFaktur.getText().toString();
                String bpkb = edBPKB.getText().toString();
                String stnk = edSTNK.getText().toString();
                String sim = edSIM.getText().toString();
                String ktp = edKTP.getText().toString();
                simpanDokumen(cek,bukutab,atm,sertifikat,rekkoran,suratper,kwitansi,faktur,bpkb,stnk,sim,ktp);
            }
        });
    }
    private void simpanDokumen(final String cek,final String bukutab,final String atm,final String sertifikat,final String rekkoran,final String suratper,final String kwitansi,final String faktur,final String bpkb,final String stnk,final String sim,final String ktp){
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/insert_bb_dokumen", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();

                        Intent i = new Intent(BuktiDokumenActivity.this, BarangBuktiTambahActivity.class);
                        i.putExtra("idunik",ID_TAHANAN);
                        startActivity(i);
                        finish();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();

                        Intent i = new Intent(BuktiDokumenActivity.this, BarangBuktiTambahActivity.class);
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
                params.put("cek", cek);
                params.put("buku_tabungan", bukutab);
                params.put("atm", atm);
                params.put("sertifikat", sertifikat);
                params.put("rek_koran", rekkoran);
                params.put("surat_perjanjian", suratper);
                params.put("kwitansi", kwitansi);
                params.put("faktur_jual", faktur);
                params.put("bpkb", bpkb);
                params.put("stnk", stnk);
                params.put("sim", sim);
                params.put("ktp", ktp);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BuktiDokumenActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(BuktiDokumenActivity.this, BarangBuktiTambahActivity.class);
        i.putExtra("idunik",ID_TAHANAN);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}