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

public class DetailMesinActivity extends AppCompatActivity {
    private EditText edRoda2, edRoda3, edRoda4, edRodalbh4, edTruck, edDumpTruck, edTangki, edTrailler,
            edSepeda, edDozer, edExcavator, edLoader, edTractor,
            edDieselHisap, edGenset,
            edKapal, edKapalBarang, edKapalTunda,edTongkang, edSampan, edKlotok,
            edMejaKursi, edLemari, edLaptop, edPrinter, edKomputer, edMonitor, edMesinFC, edKipasAngim, edHandphone, edCCTV, edTimbangan, edStereo, edLED;
    private Button btnSimpan;
    SweetAlertDialog dialog;

    String ID_TAHANAN;
    String IDBUKTIDETAIL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mesin);
        this.getSupportActionBar().setTitle("Detail Bukti Senjata");
        Intent i = getIntent();
        if(i.hasExtra("NIKBUKTI"))
        {
            Bundle b = getIntent().getExtras();
            ID_TAHANAN= b.getString("NIKBUKTI");
            loadBarangBukti(ID_TAHANAN);
        }
        btnSimpan = findViewById(R.id.btnSimpanMesin);
        edRoda2 = findViewById(R.id.edRoda2);
        edRoda3 = findViewById(R.id.edRoda3);
        edRoda4 = findViewById(R.id.edRoda4);
        edRodalbh4 = findViewById(R.id.edRodalbh4);
        edTruck = findViewById(R.id.edTruck);
        edDumpTruck = findViewById(R.id.edDumpTruck);
        edTangki = findViewById(R.id.edTangkiBBM);
        edTrailler = findViewById(R.id.edTrailer);
        edSepeda = findViewById(R.id.edSepeda);
        edDozer = findViewById(R.id.edDozer);
        edExcavator = findViewById(R.id.edExcavator);
        edLoader = findViewById(R.id.edLoader);
        edTractor = findViewById(R.id.edTractor);
        edDieselHisap = findViewById(R.id.edHisalDiesel);
        edGenset = findViewById(R.id.edGenset);
        edKapal = findViewById(R.id.edKapal);
        edKapalBarang = findViewById(R.id.edBarangKargo);
        edKapalTunda = findViewById(R.id.edTugBoat);
        edTongkang = findViewById(R.id.edTongkang);
        edSampan = findViewById(R.id.edSampan);
        edKlotok = findViewById(R.id.edKlotok);
        edMejaKursi = findViewById(R.id.edMejaKursi);
        edLemari = findViewById(R.id.edLemari);
        edLaptop = findViewById(R.id.edLaptop);
        edPrinter = findViewById(R.id.edPrinter);
        edKomputer = findViewById(R.id.edKomputer);
        edMonitor = findViewById(R.id.edMonitor);
        edMesinFC = findViewById(R.id.edMesinFC);
        edKipasAngim = findViewById(R.id.edKipasAngin);
        edHandphone= findViewById(R.id.edHandphone);
        edCCTV = findViewById(R.id.edCCTV);
        edTimbangan = findViewById(R.id.edTimbangan);
        edStereo = findViewById(R.id.edStereo);
        edLED = findViewById(R.id.edLED);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String roda2 = edRoda2.getText().toString();
                String roda3 = edRoda3.getText().toString();
                String roda4 = edRoda4.getText().toString();
                String rodalbh4 = edRodalbh4.getText().toString();
                String truck = edTruck.getText().toString();
                String dumptruck = edDumpTruck.getText().toString();
                String tangki = edTangki.getText().toString();
                String trailer = edTrailler.getText().toString();
                String sepeda = edSepeda.getText().toString();
                String dozer = edDozer.getText().toString();
                String excafator = edExcavator.getText().toString();
                String loader = edLoader.getText().toString();
                String tractor = edTractor.getText().toString();
                String dieselhidap= edDieselHisap.getText().toString();
                String genset = edGenset.getText().toString();
                String kapal = edKapal.getText().toString();
                String kapalBarang = edKapalBarang.getText().toString();
                String kapaltunda = edKapalTunda.getText().toString();
                String tongkang = edTongkang.getText().toString();
                String sampan = edSampan.getText().toString();
                String klotok = edKlotok.getText().toString();
                String mejakursi = edMejaKursi.getText().toString();
                String lemari = edLemari.getText().toString();
                String laptop = edLaptop.getText().toString();
                String printer = edPrinter.getText().toString();
                String komputer = edKomputer.getText().toString();
                String monitor = edMonitor.getText().toString();
                String mesinfc = edMesinFC.getText().toString();
                String kipasangin = edKipasAngim.getText().toString();
                String handphone = edHandphone.getText().toString();
                String cctv = edCCTV.getText().toString();
                String timbangan = edTimbangan.getText().toString();
                String stereo = edStereo.getText().toString();
                String led = edLED.getText().toString();

                    simpanMesin(IDBUKTIDETAIL,roda2, roda3, roda4, rodalbh4, truck, dumptruck, tangki, trailer, sepeda,
                            dozer, excafator, loader, tractor, dieselhidap, genset, kapal, kapalBarang, kapaltunda, tongkang,
                            sampan, klotok, mejakursi, lemari, laptop, printer, komputer, monitor, mesinfc, kipasangin, handphone, cctv,
                            timbangan, stereo, led);

            }
        });
    }
    private void simpanMesin(final String iddetail,final String roda2,final String  roda3,final String roda4,final String  rodalbh4,final String truck,
                             final String dumptruck,final String  tangki,final String  trailer,final String  sepeda,
                             final String dozer,final String  excafator,final String  loader, final String tractor, final String dieselhidap,
                             final String genset,final String  kapal,final String  kapalBarang,final String  kapaltunda,final String  tongkang,
                             final String  sampan,final String  klotok, final String mejakursi, final String lemari,final String  laptop, final String printer,
                             final String komputer,final String  monitor, final String  mesinfc,final String  kipasangin,final String  handphone,final String  cctv,
                             final String timbangan,final String  stereo, final String led){
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/insert_bb_mesin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();

                        Intent i = new Intent(DetailMesinActivity.this, DetailBuktiActivity.class);
                        i.putExtra("idunik",ID_TAHANAN);
                        startActivity(i);
                        finish();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();

                        Intent i = new Intent(DetailMesinActivity.this, DetailBuktiActivity.class);
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
                params.put("roda_2", roda2);
                params.put("roda_3", roda3);
                params.put("roda_4", roda4);
                params.put("roda_lebih_4", rodalbh4);
                params.put("truck", truck);
                params.put("dump_truck", dumptruck);
                params.put("tangki_bbm", tangki);
                params.put("trailer", trailer);
                params.put("sepeda", sepeda);
                params.put("dozer", dozer);
                params.put("excavator", excafator);
                params.put("loader", loader);
                params.put("tractor", tractor);
                params.put("hisap_diesel", dieselhidap);
                params.put("genset", genset);
                params.put("kapal", kapal);
                params.put("kapal_barang", kapalBarang);
                params.put("kapal_tunda", kapaltunda);
                params.put("tongkang", tongkang);
                params.put("sampan", sampan);
                params.put("klotok", klotok);
                params.put("meja", mejakursi);
                params.put("lemari", lemari);
                params.put("laptop", laptop);
                params.put("printer", printer);
                params.put("komputer", komputer);
                params.put("monitor", monitor);
                params.put("mesin_fc", mesinfc);
                params.put("kipas_angin", kipasangin);
                params.put("handphone", handphone);
                params.put("cctv", cctv);
                params.put("timbangan", timbangan);
                params.put("stereo", stereo);
                params.put("tv", led);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailMesinActivity.this);
        requestQueue.add(stringRequest);

    }
    private void loadBarangBukti(final String idtahanan){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/detail_bb_mesin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");
                        IDBUKTIDETAIL = user.getString("id");

                        edRoda2.setText(user.getString("roda_2"));
                        edRoda3.setText(user.getString("roda_3"));
                        edRoda4.setText(user.getString("roda_4"));
                        edRodalbh4.setText(user.getString("roda_lebih_4"));
                        edTruck.setText(user.getString("truck"));
                        edDumpTruck.setText(user.getString("dump_truck"));
                        edTangki.setText(user.getString("tangki_bbm"));
                        edTrailler.setText(user.getString("trailer"));
                        edSepeda.setText(user.getString("sepeda"));
                        edDozer.setText(user.getString("dozer"));
                        edExcavator.setText(user.getString("excavator"));
                        edLoader.setText(user.getString("loader"));
                        edTractor.setText(user.getString("tractor"));
                        edDieselHisap.setText(user.getString("hisap_diesel"));
                        edGenset.setText(user.getString("genset"));
                        edKapal.setText(user.getString("kapal"));
                        edKapalBarang.setText(user.getString("kapal_barang"));
                        edKapalTunda.setText(user.getString("kapal_tunda"));
                        edTongkang.setText(user.getString("tongkang"));
                        edSampan.setText(user.getString("sampan"));
                        edKlotok.setText(user.getString("klotok"));
                        edMejaKursi.setText(user.getString("meja"));
                        edLemari.setText(user.getString("lemari"));
                        edLaptop.setText(user.getString("laptop"));
                        edPrinter.setText(user.getString("printer"));
                        edKomputer.setText(user.getString("komputer"));
                        edMonitor.setText(user.getString("monitor"));
                        edMesinFC.setText(user.getString("mesin_fc"));
                        edKipasAngim.setText(user.getString("kipas_angin"));
                        edHandphone.setText(user.getString("handphone"));
                        edCCTV.setText(user.getString("cctv"));
                        edTimbangan.setText(user.getString("timbangan"));
                        edStereo.setText(user.getString("stereo"));
                        edLED.setText(user.getString("tv"));
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
        Intent i = new Intent(DetailMesinActivity.this, DetailBuktiActivity.class);
        i.putExtra("idunik",ID_TAHANAN);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}