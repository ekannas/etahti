package com.aplikasianaknegeri.e_tahti;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CardView cardTahanan, cardBarangBukti, cardstatTahanan, cardstatBarangBukti;
    TextView textNama, textSatker, textJabatan;
    private SQLiteHandler db;
    SweetAlertDialog dialog;
    private SessionManager session;
    CircleImageView ciImage;
    String IDUSER;
    RelativeLayout layouProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layouProfile = findViewById(R.id.layout_profile);

        cardTahanan = findViewById(R.id.cardTahanan);
        cardBarangBukti = findViewById(R.id.cardBarangBukti);
        cardstatBarangBukti =findViewById(R.id.cardStatistikBarangBukti);
        cardstatTahanan = findViewById(R.id.cardStatistikTahanan);

        textNama = findViewById(R.id.textNama);
        textSatker = findViewById(R.id.textSatker);
        textJabatan = findViewById(R.id.textJabatan);
        ciImage = findViewById(R.id.imageProfile);
        cardTahanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TahananActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardBarangBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, BarangBuktiActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardstatBarangBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, StatistikBuktiActivity.class);
                startActivity(i);
                finish();
            }
        });
        cardstatTahanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, StatistikTahananActivity.class);
                startActivity(i);
                finish();
            }
        });
        db = new SQLiteHandler(MainActivity.this);
        session = new SessionManager(MainActivity.this);
        if (!session.isLoggedIn()) {
            logoutUser();
        }else{
            HashMap<String, String> user = db.getUserDetails();
            IDUSER = user.get("kode");
            ProfilUser(IDUSER);
        }
        layouProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ProfilActivity.class);
                startActivity(i);
                finish();
            }
        });
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
                        String kode = user.getString("id");
                        textNama.setText(user.getString("username"));
                        textSatker.setText(user.getString("satker_nama"));
                        textJabatan.setText(user.getString("jabatan"));
//                        Picasso.with(MainActivity.this)
//                                .load(APIConfig.urlAPI+"/upload/profil/"+user.getString("foto"))
//                                .placeholder(R.drawable.defaultimage)
//                                .into(ciImage);
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
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUser();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void showDialogSetujui()
    {
        new SweetAlertDialog(this)
                .setTitleText("Konfirmasi")
                .setContentText("Apakah kamu ingin keluar ?")
                .setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                        System.exit(0);
                    }
                })
                .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        showDialogSetujui();
        // super.onBackPressed();
    }
}