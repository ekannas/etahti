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

public class DetailPersediaanActivity extends AppCompatActivity {
    Button btnSimpan;
    EditText edMeranti, edUlinMeter, edUlinPotong, edCampuran, edBorneo, edBatang, edPapanMeter, edRotan, edOlahan, edLailainHutan,
            edKaret, edSawit, edBeras, edPupuk, edGula, edKopi, edLainlainKebun,
            edGanjaKg, edGanjaGram, edGanjaBibit, edMariyuanaGram, edMariyuanaBungkus, edMariyuanaPaket, edHeroinGram, edGorila, edShabuGram, edShabuKlip, edShabuPaket, edExtacyGram, getEdExtacyButir, edDaftarG, edInexGram, edInexButir,
            edKimia, edPotasiumBOM, edUrea50kg, edBakingSoda, edJerigen, edBotolAtonik, edCarnofen, edDexstro, edCarnofenGram, edInsektisida,
            edPakaian, edDompet, edTas, edCelana, edSendal, edJaket,
            edIkan, edKerbau, edLainnya;
    SweetAlertDialog dialog;
    String ID_TAHANAN;
    String IDBUKTIDETAIL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_persediaan);
        this.getSupportActionBar().setTitle("Detail Bukti Persediaan");
        Intent i = getIntent();
        if(i.hasExtra("NIKBUKTI"))
        {
            Bundle b = getIntent().getExtras();
            ID_TAHANAN= b.getString("NIKBUKTI");
            loadBarangBukti(ID_TAHANAN);
        }
        btnSimpan = findViewById(R.id.btnSimpanPersediaan);
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
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String meranti = edMeranti.getText().toString();
                String ulinMeter = edUlinMeter.getText().toString();
                String ulinPotong = edUlinPotong.getText().toString();
                String campuran = edCampuran.getText().toString();
                String borneo = edBorneo.getText().toString();
                String batangPotong = edBatang.getText().toString();
                String papanMeter = edPapanMeter.getText().toString();
                String rotan = edRotan.getText().toString();
                String olahan = edOlahan.getText().toString();
                String lainlainHutan = edLailainHutan.getText().toString();
                String karet = edKaret.getText().toString();
                String sawit = edSawit.getText().toString();
                String beras = edBeras.getText().toString();
                String pupuk = edPupuk.getText().toString();
                String gula = edGula.getText().toString();
                String kopi = edKopi.getText().toString();
                String lainlainKebun = edLainlainKebun.getText().toString();
                String ganjakg = edGanjaKg.getText().toString();
                String ganjagram = edGanjaGram.getText().toString();
                String ganjabibit = edGanjaBibit.getText().toString();
                String mariyuanagram = edMariyuanaGram.getText().toString();
                String mariyuanabungkus = edMariyuanaBungkus.getText().toString();
                String mariyuanapaket = edMariyuanaPaket.getText().toString();
                String heroingram = edHeroinGram.getText().toString();
                String gorila = edGorila.getText().toString();
                String shabugram = edShabuGram.getText().toString();
                String shabuklip = edShabuKlip.getText().toString();
                String shabupaket = edShabuPaket.getText().toString();
                String extacygram = edExtacyGram.getText().toString();
                String extacybutir = getEdExtacyButir.getText().toString();
                String daftarg = edDaftarG.getText().toString();
                String inexgram = edInexGram.getText().toString();
                String inexbutir = edInexButir.getText().toString();
                String kimia = edKimia.getText().toString();
                String potasiumBOM = edPotasiumBOM.getText().toString();
                String uera50kg = edUrea50kg.getText().toString();
                String bakingSoda = edBakingSoda.getText().toString();
                String jerigen = edJerigen.getText().toString();
                String botolatonik = edBotolAtonik.getText().toString();
                String carnofen = edCarnofen.getText().toString();
                String dexstro = edDexstro.getText().toString();
                String carnofengram = edCarnofenGram.getText().toString();
                String insektisida = edInsektisida.getText().toString();
                String pakaian = edPakaian.getText().toString();
                String dompet = edDompet.getText().toString();
                String tas = edTas.getText().toString();
                String celana = edCelana.getText().toString();
                String sendal = edSendal.getText().toString();
                String jaket = edJaket.getText().toString();
                String ikan = edIkan.getText().toString();
                String kerbau = edKerbau.getText().toString();
                String lainnya = edLainnya.getText().toString();

                    simpanPersediaan(IDBUKTIDETAIL,meranti, ulinMeter, ulinPotong, campuran, borneo, batangPotong, papanMeter, rotan, olahan, lainlainHutan,
                            karet, sawit, beras, pupuk, gula, kopi, lainlainKebun, ganjakg, ganjagram, ganjabibit, mariyuanagram, mariyuanabungkus, mariyuanapaket,
                            heroingram, gorila, shabugram, shabuklip, shabupaket, extacygram, extacybutir, daftarg, inexgram, inexbutir,
                            kimia, potasiumBOM, uera50kg, bakingSoda, jerigen, botolatonik, carnofen, dexstro, carnofengram, insektisida, pakaian,
                            dompet, tas, celana, sendal, jaket, ikan, kerbau, lainnya);

            }
        });
    }
    private void simpanPersediaan(final String iddetail,final String meranti, final String ulinMeter,final String ulinPotong,final String campuran,final String borneo,final String batangPotong,final String papanMeter,final String  rotan,final String  olahan,final String  lainlainHutan,
                                  final String karet,final String sawit,final String  beras,final String  pupuk,final String  gula,final String  kopi,final String  lainlainKebun,final String  ganjakg,final String  ganjagram,final String  ganjabibit,final String  mariyuanagram,final String  mariyuanabungkus,final String  mariyuanapaket,
                                  final String heroingram,final String  gorila,final String  shabugram,final String shabuklip,final String  shabupaket,final String  extacygram,final String  extacybutir,final String  daftarg, final String inexgram,final String  inexbutir,
                                  final String kimia,final String  potasiumBOM,final String  uera50kg,final String  bakingSoda,final String  jerigen,final String  botolatonik,final String  carnofen,final String  dexstro,final String  carnofengram,final String  insektisida,final String  pakaian,
                                  final String dompet,final String  tas,final String  celana,final String  sendal,final String  jaket,final String  ikan,final String  kerbau,final String  lainnya)
    {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/insert_bb_persediaan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();

                        Intent i = new Intent(DetailPersediaanActivity.this, DetailBuktiActivity.class);

                        i.putExtra("idunik",ID_TAHANAN);
                        startActivity(i);
                        finish();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();

                        Intent i = new Intent(DetailPersediaanActivity.this, DetailBuktiActivity.class);
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
                params.put("meranti", meranti);
                params.put("ulin_meter", ulinMeter);
                params.put("ulin_potong", ulinPotong);
                params.put("campuran", campuran);
                params.put("borneo", borneo);
                params.put("potong", batangPotong);
                params.put("papan", papanMeter);
                params.put("rotan", rotan);
                params.put("kayu_olahan", olahan);
                params.put("lain_hutan", lainlainHutan);
                params.put("karet", karet);
                params.put("sawit", sawit);
                params.put("padi", beras);
                params.put("pupuk", pupuk);
                params.put("gula", gula);
                params.put("kopi", kopi);
                params.put("lain_kebun", lainlainKebun);
                params.put("ganja_kilo", ganjakg);
                params.put("ganja_gram", ganjagram);
                params.put("ganja_bibit", ganjabibit);
                params.put("mariyuana_gram", mariyuanagram);
                params.put("mariyuana_bungkus", mariyuanabungkus);
                params.put("mariyuana_paket", mariyuanapaket);
                params.put("heroin_gram", heroingram);
                params.put("gorila_gram", gorila);
                params.put("shabu_gram", shabugram);
                params.put("shabu_klip", shabuklip);
                params.put("shabu_paket", shabupaket);
                params.put("extacy_gram", extacygram);
                params.put("extacy_butir", extacybutir);
                params.put("daftarg", daftarg);
                params.put("inex_gram", inexgram);
                params.put("inex_butir", inexbutir);
                params.put("kimia", kimia);
                params.put("potasium", potasiumBOM);
                params.put("urea", uera50kg);
                params.put("bakingsoda", bakingSoda);
                params.put("roundap", jerigen);
                params.put("botol_atonik", botolatonik);
                params.put("carnofen", carnofen);
                params.put("dexstro", dexstro);
                params.put("carnofen_gram", carnofengram);
                params.put("insektisida", insektisida);
                params.put("pakaian", pakaian);
                params.put("dompet", dompet);
                params.put("tas", tas);
                params.put("celana", celana);
                params.put("sandal", sendal);
                params.put("jaket", jaket);
                params.put("ikan", ikan);
                params.put("kerbau", kerbau);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailPersediaanActivity.this);
        requestQueue.add(stringRequest);
    }
    private void loadBarangBukti(final String idtahanan){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/detail_bb_persediaan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        IDBUKTIDETAIL = user.getString("id");
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
                params.put("idunik", idtahanan);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DetailPersediaanActivity.this, DetailBuktiActivity.class);
        i.putExtra("idunik",ID_TAHANAN);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}