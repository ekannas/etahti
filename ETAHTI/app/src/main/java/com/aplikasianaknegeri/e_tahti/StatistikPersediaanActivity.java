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

public class StatistikPersediaanActivity extends AppCompatActivity {
    TextView edMeranti, edUlinMeter, edUlinPotong, edCampuran, edBorneo, edBatang, edPapanMeter, edRotan, edOlahan, edLailainHutan,
            edKaret, edSawit, edBeras, edPupuk, edGula, edKopi, edLainlainKebun,
            edGanjaKg, edGanjaGram, edGanjaBibit, edMariyuanaGram, edMariyuanaBungkus, edMariyuanaPaket, edHeroinGram, edGorila, edShabuGram, edShabuKlip, edShabuPaket, edExtacyGram, getEdExtacyButir, edDaftarG, edInexGram, edInexButir,
            edKimia, edPotasiumBOM, edUrea50kg, edBakingSoda, edJerigen, edBotolAtonik, edCarnofen, edDexstro, edCarnofenGram, edInsektisida,
            edPakaian, edDompet, edTas, edCelana, edSendal, edJaket,
            edIkan, edKerbau, edLainnya;
    private SQLiteHandler db;
    String IDUSER;
    SweetAlertDialog dialog;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_persediaan);
        this.getSupportActionBar().setTitle("Statistik Persediaan");
        edMeranti = findViewById(R.id.edMerantiBatang);
        edUlinMeter = findViewById(R.id.edulinMeter);
        edUlinPotong = findViewById(R.id.edUlinPotong);
        edCampuran = findViewById(R.id.edCampuran);
        edBorneo = findViewById(R.id.edBorneo);
        edBatang = findViewById(R.id.edBatangPotong);
        edPapanMeter = findViewById(R.id.edPapanMeter);
        edRotan = findViewById(R.id.edRotan);
        edOlahan = findViewById(R.id.edOlahan);
        edLailainHutan = findViewById(R.id.edLainLainHutan);
        edKaret = findViewById(R.id.edGetahKaret);
        edSawit = findViewById(R.id.edBuahSawit);
        edBeras = findViewById(R.id.edPadiKg);
        edPupuk = findViewById(R.id.edPupukKarung);
        edGula = findViewById(R.id.edGulaRafinasi);
        edKopi = findViewById(R.id.edKopi);
        edLainlainKebun = findViewById(R.id.edLainLaLainKebun);
        edGanjaKg = findViewById(R.id.edGanjaKg);
        edGanjaGram = findViewById(R.id.edGanjaGram);
        edGanjaBibit = findViewById(R.id.edGanjaBibit);
        edMariyuanaGram = findViewById(R.id.edMariyuanaGram);
        edMariyuanaBungkus = findViewById(R.id.edMariyuanaBungkus);
        edMariyuanaPaket = findViewById(R.id.edMariyuanaPaket);
        edHeroinGram = findViewById(R.id.edHeroinGram);
        edGorila = findViewById(R.id.edGorilaGram);
        edShabuGram = findViewById(R.id.edShabuGram);
        edShabuKlip = findViewById(R.id.edShabuKlip);
        edShabuPaket = findViewById(R.id.edShabuPaket);
        edExtacyGram = findViewById(R.id.edExtacyGram);
        getEdExtacyButir = findViewById(R.id.edExtacyButir);
        edDaftarG = findViewById(R.id.edDaftarG);
        edInexGram = findViewById(R.id.edInexGram);
        edInexButir= findViewById(R.id.edInexButir);
        edKimia = findViewById(R.id.edKimia);
        edPotasiumBOM = findViewById(R.id.edBomPotasium);
        edUrea50kg = findViewById(R.id.edUrea50Kg);
        edBakingSoda = findViewById(R.id.edBakingSoda);
        edJerigen = findViewById(R.id.edJerigen);
        edBotolAtonik = findViewById(R.id.edAtonik);
        edCarnofen = findViewById(R.id.edCarnofenButir);
        edDexstro = findViewById(R.id.edDexstro);
        edCarnofenGram = findViewById(R.id.edCarnofenGram);
        edInsektisida = findViewById(R.id.edInsektisida);
        edPakaian = findViewById(R.id.edPakaian);
        edDompet = findViewById(R.id.edDompet);
        edTas = findViewById(R.id.edTas);
        edCelana = findViewById(R.id.edCelana);
        edSendal = findViewById(R.id.edSandal);
        edJaket = findViewById(R.id.edJaket);
        edIkan = findViewById(R.id.edIkan);
        edKerbau = findViewById(R.id.edKerbau);
        edLainnya = findViewById(R.id.edLainnya);
        db = new SQLiteHandler(StatistikPersediaanActivity.this);
        session = new SessionManager(StatistikPersediaanActivity.this);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/statistik_bb_persediaan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        edMeranti.setText(user.getString("meranti"));
                        edUlinMeter.setText(user.getString("ulin_meter"));
                        edUlinPotong.setText(user.getString("ulin_potong"));
                        edCampuran.setText(user.getString("campuran"));
                        edBorneo.setText(user.getString("borneo"));
                        edBatang.setText(user.getString("potong"));
                        edPapanMeter.setText(user.getString("papan"));
                        edRotan.setText(user.getString("rotan"));
                        edOlahan.setText(user.getString("kayu_olahan"));
                        edLailainHutan.setText(user.getString("lain_hutan"));
                        edKaret.setText(user.getString("karet"));
                        edSawit.setText(user.getString("sawit"));
                        edBeras.setText(user.getString("padi"));
                        edPupuk.setText(user.getString("pupuk"));
                        edGula.setText(user.getString("gula"));
                        edKopi.setText(user.getString("kopi"));
                        edLainlainKebun.setText(user.getString("lain_kebun"));
                        edGanjaKg.setText(user.getString("ganja_kilo"));
                        edGanjaGram.setText(user.getString("ganja_gram"));
                        edGanjaBibit.setText(user.getString("ganja_bibit"));
                        edMariyuanaGram.setText(user.getString("mariyuana_gram"));
                        edMariyuanaBungkus.setText(user.getString("mariyuana_bungkus"));
                        edMariyuanaPaket.setText(user.getString("mariyuana_paket"));
                        edHeroinGram.setText(user.getString("heroin_gram"));
                        edGorila.setText(user.getString("gorila_gram"));
                        edShabuGram.setText(user.getString("shabu_gram"));
                        edShabuKlip.setText(user.getString("shabu_klip"));
                        edShabuPaket.setText(user.getString("shabu_paket"));
                        edExtacyGram.setText(user.getString("extacy_gram"));
                        getEdExtacyButir.setText(user.getString("extacy_butir"));
                        edDaftarG.setText(user.getString("daftarg"));
                        edInexGram.setText(user.getString("inex_gram"));
                        edInexButir.setText(user.getString("inex_butir"));
                        edKimia.setText(user.getString("kimia"));
                        edPotasiumBOM.setText(user.getString("potasium"));
                        edUrea50kg.setText(user.getString("urea"));
                        edBakingSoda.setText(user.getString("bakingsoda"));
                        edJerigen.setText(user.getString("roundap"));
                        edBotolAtonik.setText(user.getString("botol_atonik"));
                        edCarnofen.setText(user.getString("carnofen"));
                        edDexstro.setText(user.getString("dexstro"));
                        edCarnofenGram.setText(user.getString("carnofen_gram"));
                        edInsektisida.setText(user.getString("insektisida"));
                        edPakaian.setText(user.getString("pakaian"));
                        edDompet.setText(user.getString("dompet"));
                        edTas.setText(user.getString("tas"));
                        edCelana.setText(user.getString("celana"));
                        edSendal.setText(user.getString("sandal"));
                        edJaket.setText(user.getString("jaket"));
                        edIkan.setText(user.getString("ikan"));
                        edKerbau.setText(user.getString("kerbau"));
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
        Intent intent = new Intent(StatistikPersediaanActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(StatistikPersediaanActivity.this, StatistikBuktiActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}