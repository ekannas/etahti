package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class StatistikBuktiActivity extends AppCompatActivity {

    public static final int[] MY_COLORS = {
            Color.parseColor("#F8981D"),
            Color.parseColor("#FFC107"),
            Color.parseColor("#CDDC39"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#E91E63"),
            Color.parseColor("#D4008D"),
            Color.parseColor("#185991"),
            Color.parseColor("#DC39C1"),
    };
    PieChart pieChart;

    PieData pieData;
    List<PieEntry> pieEntryList = new ArrayList<>();
    CardView cardUang, cardDokumen, cardLogam, cardTanah, cardGedung, cardMesin, cardSenjata, cardTambang, cardPersediaan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik_bukti);
        this.getSupportActionBar().setTitle("Statistik Barang Bukti");
        cardUang = findViewById(R.id.cardStatBuktiUang);
        cardDokumen = findViewById(R.id.cardStatBuktiDokumen);
        cardLogam = findViewById(R.id.cardStatBuktiLogam);
        cardTanah = findViewById(R.id.cardStatBuktiTanah);
        cardGedung = findViewById(R.id.cardStatBuktiGedung);
        cardMesin = findViewById(R.id.cardStatBuktiMesin);
        cardSenjata = findViewById(R.id.cardStatBuktiSenjata);
        cardTambang = findViewById(R.id.cardStatBuktiTambang);
        cardPersediaan = findViewById(R.id.cardStatBuktiPersediaan);
        cardUang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatistikBuktiActivity.this, StatistikUangActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( StatistikBuktiActivity.this, StatistikDokumenActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardLogam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatistikBuktiActivity.this, StatistikLogamActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardTanah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatistikBuktiActivity.this, StatistikTanahActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardGedung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatistikBuktiActivity.this, StatistikGedungActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatistikBuktiActivity.this, StatistikMesinActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardSenjata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatistikBuktiActivity.this, StatistikSenjataActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardTambang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatistikBuktiActivity.this, StatistikTambangActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardPersediaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StatistikBuktiActivity.this, StatistikLogamActivity.class);
                startActivity(i);
                finish();
            }
        });

//        pieChart = findViewById(R.id.piechartBarangBukti);
//        pieChart.setUsePercentValues(true);
//        pieChart.setDrawHoleEnabled(false);
//        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"");
//        ArrayList<Integer> colors = new ArrayList<>();
//        for (int c: MY_COLORS)
//        {
//            colors.add(c);
//        }
//        pieEntryList.add(new PieEntry(80,"UANG"));
//        pieEntryList.add(new PieEntry(30,"DOKUMEN"));
//        pieEntryList.add(new PieEntry(20,"LOGAM"));
//        pieEntryList.add(new PieEntry(60,"TANAH"));
//        pieEntryList.add(new PieEntry(10,"GEDUNG"));
//        pieEntryList.add(new PieEntry(50,"MESIN"));
//        pieEntryList.add(new PieEntry(90,"SENJATA"));
//        pieEntryList.add(new PieEntry(10,"BAHAN BAKAR"));
//        pieEntryList.add(new PieEntry(15,"BARANG PERSEDIAAN"));
//
//        pieDataSet.setColors(colors);
//        pieData = new PieData(pieDataSet);
//        pieChart.setData(pieData);
//        pieChart.invalidate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(StatistikBuktiActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}