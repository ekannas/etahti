package com.aplikasianaknegeri.e_tahti;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplikasianaknegeri.e_tahti.helper.APIConfig;
import com.aplikasianaknegeri.e_tahti.helper.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.aplikasianaknegeri.e_tahti.helper.APIConfig.urlAPI;

public class TahananTambahActivity extends AppCompatActivity {
    ImageView selectedImage;
    ServiceInterface serviceInterface;
    List<Uri> files = new ArrayList<>();

    private LinearLayout parentLinearLayout;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;






    EditText edNIK, edNama, edKeluarga, edPasal, edTandaLahir, edKeterangan, edAlamat, edKasus;
    Spinner spinSatker, spinUsia, spinJenkel, spinLokasi, spinTanggal, spinBulan, spinTahun;
    Button btnSimpan;
    ImageView imgUploadImage;
    private JSONArray resultSatker;
    private ArrayList<String> satkers;
    List<String> usias;
    List<String> jenkels;
    List<String> lokasis;
    List<String> tanggals;
    List<String> bulans;
    List<String> tahuns;
    private String Document_img1="";
    SweetAlertDialog dialog;
    private static final int REQUEST_WRITE_STORAGE =112;
    private int ReqCode = 0;
    private Bitmap resultPhoto, resultGallery;
    int tahun;
    TextInputLayout textLaySatker, textLayUsia, textLayJenkel, textLayLok, textLayTgl, textLaybln, textLayNIK, textLayNama, textLayKel, textLayTahun, textLayPasal, textLayTndLhr, textLayKet, textLayAlamat, textLayKasus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahanan_tambah);

        parentLinearLayout = findViewById(R.id.parent_linear_layout);

        final ImageView addImage = findViewById(R.id.iv_add_image);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });

        this.getSupportActionBar().setTitle("Tambah Data Tahanan");
        satkers = new ArrayList<String>();
        usias =  new ArrayList<String>();
        jenkels = new ArrayList<String>();
        lokasis = new ArrayList<String>();
        tanggals = new ArrayList<String>();
        bulans = new ArrayList<String>();
        tahuns = new ArrayList<String>();
        edNIK = findViewById(R.id.edNIK);
        edNama = findViewById(R.id.edNama);
        edKeluarga = findViewById(R.id.edKeluarga);
        edPasal = findViewById(R.id.edPasal);
        edTandaLahir = findViewById(R.id.edPasal);
        edAlamat = findViewById(R.id.edAlamat);
        edKeterangan = findViewById(R.id.edKeterangan);
        edKasus = findViewById(R.id.edKasus);
        spinSatker = findViewById(R.id.spinSatker);
        spinUsia = findViewById(R.id.spinUsia);
        spinJenkel = findViewById(R.id.spinJenkel);
        spinLokasi = findViewById(R.id.spinLokasi);
        spinTanggal = findViewById(R.id.spinTanggal);
        spinBulan = findViewById(R.id.spinBulan);
        spinTahun = findViewById(R.id.spinTahun);
        btnSimpan = findViewById(R.id.btnSimpanTahanan);

        imgUploadImage = findViewById(R.id.btnimageProfile);

        textLaySatker = findViewById(R.id.layoutSpinSatker);
        textLayUsia = findViewById(R.id.layoutUsia);
        textLayJenkel = findViewById(R.id.layoutJenkel);
        textLayLok = findViewById(R.id.layoutLokasi);
        textLayTgl = findViewById(R.id.layoutTanggal);
        textLaybln = findViewById(R.id.layoutBulan);
        textLayNIK = findViewById(R.id.layoutNIK);
        textLayNama = findViewById(R.id.layoutNama);
        textLayKel = findViewById(R.id.layoutKeluarga);
        textLayTahun = findViewById(R.id.layoutTahun);
        textLayPasal = findViewById(R.id.layoutPasal);
        textLayTndLhr = findViewById(R.id.layoutTandaLahir);
        textLayKet = findViewById(R.id.layoutKeterangan);
        textLayAlamat = findViewById(R.id.layoutAlamat);
        textLayKasus = findViewById(R.id.layoutKasus);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.getDefault());
        String formattedDateTahun = df.format(c);
        tahun = Integer.parseInt(formattedDateTahun);

        imgUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        Boolean hasPermission= (ContextCompat.checkSelfPermission(TahananTambahActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED);
        Boolean hasPermissionCamera = (ContextCompat.checkSelfPermission(TahananTambahActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
        if(!hasPermission){
            ActivityCompat.requestPermissions(TahananTambahActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+ getPackageName()));
                finish();
                startActivity(i);
                return;
            }
        }
        if(!hasPermissionCamera){
            ActivityCompat.requestPermissions(TahananTambahActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_WRITE_STORAGE);
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+getPackageName()));
                finish();
                startActivity(i);
                return;
            }
        }
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String satker = spinSatker.getSelectedItem().toString();
                String noktp =edNIK.getText().toString();
                String nama = edNama.getText().toString();
                String usia = spinUsia.getSelectedItem().toString();
                String jenkel = spinJenkel.getSelectedItem().toString();
                String alamat = edAlamat.getText().toString();
                String lokasi = spinLokasi.getSelectedItem().toString();
                String keluarga = edKeluarga.getText().toString();
                String tglMasuk = spinTanggal.getSelectedItem().toString();
                String blnMasuk = spinBulan.getSelectedItem().toString();
                String thMasuk = spinTahun.getSelectedItem().toString();
                String kasus = edKasus.getText().toString();
                String pasal = edPasal.getText().toString();
                String tandaLahir = edTandaLahir.getText().toString();
                String keterangan = edKeterangan.getText().toString();
                if(noktp.equals("")){textLayNIK.setError("Nomor KTP tidak boleh kosong");}
                if(satker.equals("Pilih Satuan Kerja")){textLaySatker.setError("Pilih Satuan Kerja");}
                if(nama.equals("")){textLayNama.setError("Nama tidak boleh kosong");}
                if(usia.equals("Pilih Usia")){textLayUsia.setError("Usia tidak boleh kosong");}
                if(jenkel.equals("Pilih Jenis Kelamin")){textLayJenkel.setError("Pilih jenis kelamin");}
                if(alamat.equals("")){textLayAlamat.setError("Alamat tidak boleh kosong");}
                if(lokasi.equals("Pilih Lokasi")){textLayLok.setError("Pilih lokasi");}
                if(keluarga.equals("")){textLayKel.setError("Keluarga tidak boleh kosong");}
                if(tglMasuk.equals("")){textLayTgl.setError("Tanggal masuk tidak boleh kosong");}
                if(blnMasuk.equals("")){textLaybln.setError("Bulan masuk tidak boleh kosong");}
                if(thMasuk.equals("")){textLayTahun.setError("Tahun tidak boleh kosong");}
                if(kasus.equals("")){textLayKasus.setError("Kasus tidak boleh kosong");}
                if(pasal.equals("")){textLayPasal.setError("Pasal tidak boleh kosong");}
                if(tandaLahir.equals("")){textLayTndLhr.setError("Tanda lahir tidak boleh kosong");}
                if(keterangan.equals("")){textLayKet.setError("Keterangan tidak boleh kosong");}
                if((noktp.equals(""))||(satker.equals("Pilih Satuan Kerja"))||(nama.equals(""))||(usia.equals("Pilih Usia"))||(jenkel.equals("Pilih Jenis Kelamin"))||(alamat.equals(""))||(lokasi.equals(""))||(keluarga.equals(""))||(tglMasuk.equals(""))||
                        (blnMasuk.equals(""))||(thMasuk.equals(""))||(kasus.equals(""))||(pasal.equals(""))||(tandaLahir.equals(""))||(keterangan.equals("")))
                {
                    Toast.makeText(TahananTambahActivity.this, "Mohon isi data dengan lengkap", Toast.LENGTH_SHORT).show();
                }else {
                    if(ReqCode==1)
                    {
                        insertTahananWithPic(satker,noktp,nama,usia,jenkel,alamat,lokasi,keluarga,tglMasuk,blnMasuk,thMasuk,kasus,pasal,tandaLahir,keterangan, resultPhoto);
                    }else if(ReqCode==2)
                    {
                        insertTahananWithPic(satker,noktp,nama,usia,jenkel,alamat,lokasi,keluarga,tglMasuk,blnMasuk,thMasuk,kasus,pasal,tandaLahir,keterangan, resultGallery);
                    }else if(ReqCode==0)
                    {
                        insertTahanan(satker,noktp,nama,usia,jenkel,alamat,lokasi,keluarga,tglMasuk,blnMasuk,thMasuk,kasus,pasal,tandaLahir,keterangan);
                    }

                }
            }
        });
        getDataSpinSatker();
        getDataSpinUsia();
        getDataSpinJenkel();
        getDataSpinLokasi();
        getDataTanggal();
        getDataBulan();
        getDataTahun();

    }

    private void insertTahanan(final String satker,final String noktp,final String nama,final String usia,final String jenkel,final String alamat,final String lokasi,final String keluarga,final String tglMasuk,final String blnMasuk,final String thMasuk,final String kasus,final String pasal,final String tandaLahir,final String keterangan)
    {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConfig.urlAPI +"/api/register_tahanan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String Msg = obj.getString("msg");
                    if (Msg.equals("true")) {
                        dialog.dismiss();

                        Intent i = new Intent(TahananTambahActivity.this, TahananActivity.class);
                        startActivity(i);
                        finish();
                    } else if (Msg.equals("false")) {
                        dialog.dismiss();

                        Intent i = new Intent(TahananTambahActivity.this, TahananActivity.class);
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
                params.put("ktp", noktp);
                params.put("nama", nama);
                params.put("satker", satker);
                params.put("usia", usia);
                params.put("jenis_kelamin", jenkel);
                params.put("lokasi", lokasi);
                params.put("masukTgl", tglMasuk);
                params.put("masukBln", blnMasuk);
                params.put("masukThn", thMasuk);
                params.put("alamat", alamat);
                params.put("kasus", kasus);
                params.put("pasal", pasal);
                params.put("keluarga", keluarga);
                params.put("tanda_lahir", tandaLahir);
                params.put("keterangan", keterangan);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TahananTambahActivity.this);
        requestQueue.add(stringRequest);
    }
    private void insertTahananWithPic(final String satker,final String noktp,final String nama,final String usia,final String jenkel,final String alamat,final String lokasi,final String keluarga,final String tglMasuk,final String blnMasuk,final String thMasuk,final String kasus,final String pasal,final String tandaLahir,final String keterangan,final Bitmap bitmap)
    {
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(false);
        dialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        dialog.dismiss();
                    }
                },
                5000);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, urlAPI + "/api/register_tahanan_image",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject jObj = new JSONObject(String.valueOf(response));
                            String Msg = jObj.getString("msg");
                            String ErrorMsg = jObj.getString("error_msg");
                            if (Msg.equals("false")) {
                                dialog.dismiss();
                                new SweetAlertDialog(TahananTambahActivity.this)
                                        .setTitleText("Peringatan")
                                        .setContentText(ErrorMsg)
                                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                            }
                                        }).show();
                            } else if (Msg.equals("true")) {
                                dialog.dismiss();
                                Intent i = new Intent(TahananTambahActivity.this, TahananActivity.class);
                                startActivity(i);
                                finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            Intent i = new Intent(TahananTambahActivity.this, TahananActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ktp", noktp);
                params.put("nama", nama);
                params.put("satker", satker);
                params.put("usia", usia);
                params.put("jenis_kelamin", jenkel);
                params.put("lokasi", lokasi);
                params.put("masukTgl", tglMasuk);
                params.put("masukBln", blnMasuk);
                params.put("masukThn", thMasuk);
                params.put("alamat", alamat);
                params.put("kasus", kasus);
                params.put("pasal", pasal);
                params.put("keluarga", keluarga);
                params.put("tanda_lahir", tandaLahir);
                params.put("keterangan", keterangan);
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("foto", new DataPart(imagename +".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);


    }
    private void getDataSpinUsia(){
        usias.add("Pilih Usia");
        usias.add("Dewasa");
        usias.add("Anak");
        ArrayAdapter<String> adapterUsia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, usias);
        adapterUsia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinUsia.setAdapter(adapterUsia);
    }
    private void getDataSpinJenkel(){
        jenkels.add("Pilih Jenis Kelamin");
        jenkels.add("Laki-laki");
        jenkels.add("Perempuan");
        ArrayAdapter<String> adapterJenkel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, jenkels);
        adapterJenkel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJenkel.setAdapter(adapterJenkel);
    }
    private void getDataSpinLokasi(){
        lokasis.add("Pilih Lokasi");
        lokasis.add("POLRI");
        lokasis.add("LAPAS");
        ArrayAdapter<String> adapterLokasi = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lokasis);
        adapterLokasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLokasi.setAdapter(adapterLokasi);
    }
    private void getDataTahun(){
        for(int i = 1945; i<=tahun; i++)
        {
            tahuns.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterTahun = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tahuns);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTahun.setAdapter(adapterTahun);
        int postahun = adapterTahun.getPosition(String.valueOf(tahun));
        spinTahun.setSelection(postahun);
    }
    private void getDataTanggal(){
        tanggals.add("01");
        tanggals.add("02");
        tanggals.add("03");
        tanggals.add("04");
        tanggals.add("05");
        tanggals.add("06");
        tanggals.add("07");
        tanggals.add("08");
        tanggals.add("09");
        tanggals.add("10");
        tanggals.add("11");
        tanggals.add("12");
        tanggals.add("13");
        tanggals.add("14");
        tanggals.add("15");
        tanggals.add("16");
        tanggals.add("17");
        tanggals.add("18");
        tanggals.add("19");
        tanggals.add("20");
        tanggals.add("21");
        tanggals.add("22");
        tanggals.add("23");
        tanggals.add("24");
        tanggals.add("25");
        tanggals.add("26");
        tanggals.add("27");
        tanggals.add("28");
        tanggals.add("29");
        tanggals.add("30");
        tanggals.add("31");
        ArrayAdapter<String> adapterTanggal = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tanggals);
        adapterTanggal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTanggal.setAdapter(adapterTanggal);
    }
    private void getDataBulan(){
        bulans.add("Januari");
        bulans.add("Februari");
        bulans.add("Maret");
        bulans.add("April");
        bulans.add("Mei");
        bulans.add("Juni");
        bulans.add("Juli");
        bulans.add("Agustus");
        bulans.add("September");
        bulans.add("Oktober");
        bulans.add("November");
        bulans.add("Desember");
        ArrayAdapter<String> adapterBulan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bulans);
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBulan.setAdapter(adapterBulan);
    }
    private void getDataSpinSatker(){
        StringRequest stringRequest = new StringRequest(urlAPI+"/api/satker", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    resultSatker = j.getJSONArray("result");
                    getSatker(resultSatker);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getSatker(JSONArray j){
        satkers.add("Pilih Satuan Kerja");
        for(int i=0; i<j.length(); i++)
        {
            try{
                JSONObject json = j.getJSONObject(i);
                satkers.add(json.getString("nama"));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        spinSatker.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, satkers));
    }
    private void selectImage(){
        final CharSequence[] options = { "Ambil foto dari camera", "Ambil foto dari galeri","Batal" };
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(TahananTambahActivity.this);
        builder.setTitle("Upload Foto Tahanan");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Ambil foto dari camera"))
                {
                    ReqCode = 1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Ambil foto dari galeri"))
                {
                    ReqCode = 2;
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(TahananTambahActivity.this, TahananActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
    //===== add image in layout
    public void addImage() {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView=inflater.inflate(R.layout.image, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
        parentLinearLayout.isFocusable();

        selectedImage = rowView.findViewById(R.id.number_edit_text);
        selectImage(TahananTambahActivity.this);
    }

    //===== select image
    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Choose a Media");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    //===== bitmap to Uri
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "intuenty", null);
        Log.d("image uri",path);
        return Uri.parse(path);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {

                        Bitmap img = (Bitmap) data.getExtras().get("data");
                        selectedImage.setImageBitmap(img);
                        Picasso.get().load(getImageUri(TahananTambahActivity.this,img)).into(selectedImage);

                        String imgPath = FileUtil.getPath(TahananTambahActivity.this,getImageUri(TahananTambahActivity.this,img));

                        files.add(Uri.parse(imgPath));
                        Log.e("image", imgPath);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri img = data.getData();
                        Picasso.get().load(img).into(selectedImage);

                        String imgPath = FileUtil.getPath(TahananTambahActivity.this,img);

                        files.add(Uri.parse(imgPath));
                        Log.e("image", imgPath);

                    }
                    break;
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==RESULT_OK)
//        {
//            if (requestCode == 1) {
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        break;
//                    }
//                }
//                try {
//
//                    Bitmap bitmap;
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
//                    resultPhoto = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
//                    bitmap=getResizedBitmap(bitmap, 400);
//                    resultPhoto = getResizedBitmap(resultPhoto, 400);
//                    imgUploadImage.setImageBitmap(bitmap);
//                    BitMapToString(bitmap);
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    OutputStream outFile = null;
//                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                    try {
//                        outFile = new FileOutputStream(file);
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                        outFile.flush();
//                        outFile.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else if (requestCode == 2) {
//                Uri selectedImage = data.getData();
//                String[] filePath = { MediaStore.Images.Media.DATA };
//                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
//                c.moveToFirst();
//                int columnIndex = c.getColumnIndex(filePath[0]);
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                resultGallery = (BitmapFactory.decodeFile(picturePath));
//                thumbnail=getResizedBitmap(thumbnail, 400);
//                resultGallery = getResizedBitmap(resultGallery, 400);
//                //Log.w("path of image from gallery......******************.........", picturePath+"");
//                imgUploadImage.setImageBitmap(thumbnail);
//                BitMapToString(thumbnail);
//            }
//        }
//    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public byte [] getFileDataFromDrawable(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}