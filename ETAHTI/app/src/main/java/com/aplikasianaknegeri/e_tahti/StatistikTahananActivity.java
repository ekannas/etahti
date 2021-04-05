package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplikasianaknegeri.e_tahti.helper.APIConfig;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatistikTahananActivity extends AppCompatActivity {
    public static final int[] MY_COLORS = {
            Color.parseColor("#82560D"),
            Color.parseColor("#FFB500"),
    };
    PieChart pieChart;
    TextView tvLaki, tvPerem, tvAnak, tvDewasa, tvTotal;
    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();

    private ArrayList<String> lokasi;
    int polriJum =0;
    int lapasJum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_tahanan);
        this.getSupportActionBar().setTitle("Statistik Tahanan");
        lokasi = new ArrayList<String>();

        pieChart = findViewById(R.id.piechartTahanan);
        tvLaki = findViewById(R.id.tvstatLakilaki);
        tvPerem = findViewById(R.id.tvstatPerempuan);
        tvAnak = findViewById(R.id.tvstatAnak);
        tvDewasa = findViewById(R.id.tvstatDewasa);
        tvTotal = findViewById(R.id.tvstatTotal);
//        pieChart.setUsePercentValues(true);
//        pieChart.setDrawHoleEnabled(false);
//        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"");
//        ArrayList<Integer> colors = new ArrayList<>();
//        for (int c: MY_COLORS)
//        {
//            colors.add(c);
//        }
//        pieEntryList.add(new PieEntry(80,"LAPAS"));
//        pieEntryList.add(new PieEntry(20,"POLRI"));
//
//        pieDataSet.setColors(colors);
//        pieData = new PieData(pieDataSet);
//        pieChart.setData(pieData);
//        pieChart.invalidate();
        loadDataTahanan();
    }

    private void loadDataTahanan(){
        pieEntryList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI + "/api/statistiktahanan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        JSONObject user = obj.getJSONObject("user");

                        tvLaki.setText(user.getString("jumlah_laki"));
                        tvPerem.setText(user.getString("jumlah_perempuan"));
                        tvAnak.setText(user.getString("jumlah_anak"));
                        tvDewasa.setText(user.getString("jumlah_dewasa"));
                        tvTotal.setText(user.getString("jumlah_tahanan"));

                        polriJum = Integer.parseInt(user.getString("jumlah_polri"));
                        lapasJum = Integer.parseInt(user.getString("jumlah_lapas"));

                        pieChart.setUsePercentValues(true);
                        pieChart.setDrawHoleEnabled(false);

                        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"");
                        ArrayList<Integer> colors = new ArrayList<>();
                        for (int c: MY_COLORS)
                        {
                            colors.add(c);
                        }
                        pieEntryList.add(new PieEntry(polriJum,"POLRI"));
                        pieEntryList.add(new PieEntry(lapasJum,"LAPAS"));

                        pieDataSet.setColors(colors);
                        pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StatistikTahananActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}