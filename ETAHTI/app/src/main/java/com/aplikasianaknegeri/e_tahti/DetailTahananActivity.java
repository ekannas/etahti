package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailTahananActivity extends AppCompatActivity {
    ImageView imFoto;
    TextView tvNIK, tvSatker, tvLokasi, tvTanggal, tvPasal, tvNama, tvUsia, tvJenkel, tvAlamat, tvKeluarga, tvKasus, tvKeterangan;
    RecyclerView rvBarangBukti;
    String ID_TAHANAN;

    CardView cvUang, cvDokumen, cvLogam, cvTanah, cvBangunan, cvMesin,
            cvSenjata, cvBahanBakar, cvPersediaan;
    SweetAlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tahanan);
        this.getSupportActionBar().setTitle("Detail Data Tahanan");
        imFoto = findViewById(R.id.imageTahanan);
        tvNIK = findViewById(R.id.textNIKDetail);
        tvSatker = findViewById(R.id.textSatkerDetail);
        tvLokasi = findViewById(R.id.textLokasiDetail);
        tvTanggal = findViewById(R.id.textTglDetail);
        tvPasal = findViewById(R.id.textPasalDetail);
        tvNama = findViewById(R.id.textNamaDetail);
        tvUsia = findViewById(R.id.textUsiaDetail);
        tvJenkel = findViewById(R.id.textJenkelDetail);
        tvAlamat = findViewById(R.id.textAlamatDetail);
        tvKeluarga = findViewById(R.id.textKeluargaDetail);
        tvKasus = findViewById(R.id.textKasusDetail);
        tvKeterangan = findViewById(R.id.textKeteranganDetail);

        Intent i = getIntent();
        if(i.hasExtra("id_tahanan"))
        {
            Bundle b = getIntent().getExtras();
            ID_TAHANAN= b.getString("id_tahanan");

        }
        loadDetailTahanan(ID_TAHANAN);
    }
    private void loadDetailTahanan(final String id_tahanan){
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(true);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/tahanan_detail", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        String kode = user.getString("id");
                        tvNIK.setText(user.getString("ktp"));
                        tvNama.setText(user.getString("nama"));
                        tvSatker.setText(user.getString("satker_nama"));
                        tvLokasi.setText(user.getString("lokasi"));
                        tvTanggal.setText(user.getString("masuk"));
                        tvPasal.setText(user.getString("pasal"));
                        tvUsia.setText(user.getString("usia"));
                        tvJenkel.setText(user.getString("jenis_kelamin"));
                        tvAlamat.setText(user.getString("alamat"));
                        tvKeluarga.setText(user.getString("keluarga"));
                        tvKasus.setText(user.getString("kasus"));
                        tvKeterangan.setText(user.getString("keterangan"));
//                        Picasso.with(DetailTahananActivity.this)
//                                .load(APIConfig.urlAPI+"/upload/tahanan/"+user.getString("foto"))
//                                .placeholder(R.drawable.defaultimage)
//                                .into(imFoto);
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
                params.put("id", id_tahanan);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(DetailTahananActivity.this, TahananActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}