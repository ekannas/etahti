package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BarangBuktiTambahActivity extends AppCompatActivity {

    CardView cvUang, cvDokumen, cvLogam, cvTanah, cvBangunan, cvMesin,
            cvSenjata, cvBahanBakar, cvPersediaan;
    String NIKBUKTI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang_bukti_tambah);
        cvUang = findViewById(R.id.cardUang);
        cvDokumen = findViewById(R.id.cardDokumen);
        cvLogam = findViewById(R.id.cardLogam);
        cvTanah = findViewById(R.id.cardTanah);
        cvBangunan = findViewById(R.id.cardGedung);
        cvMesin = findViewById(R.id.cardMesin);
        cvSenjata = findViewById(R.id.cardSenjata);
        cvBahanBakar = findViewById(R.id.cardBahanTambang);
        cvPersediaan = findViewById(R.id.cardPersediaan);
        cvUang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiUangActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiDokumenActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvLogam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiLogamActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvTanah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiTanahActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvBangunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiGedungActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvMesin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiMesinActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvSenjata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiSenjataActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvBahanBakar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiBahanBakarActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        cvPersediaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BarangBuktiTambahActivity.this, BuktiPersediaanActivity.class);
                i.putExtra("NIKBUKTI",NIKBUKTI);
                startActivity(i);
                finish();
            }
        });
        Intent i = getIntent();
        if(i.hasExtra("idunik"))
        {
            Bundle b = getIntent().getExtras();
            NIKBUKTI= b.getString("idunik");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(BarangBuktiTambahActivity.this, BarangBuktiActivity.class);
        startActivity(i);
        finish();
    }
}