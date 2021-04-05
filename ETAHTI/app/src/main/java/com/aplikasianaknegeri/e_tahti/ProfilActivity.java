package com.aplikasianaknegeri.e_tahti;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ProfilActivity extends AppCompatActivity {
    CircleImageView cvImageProfile;
    TextView tvNRP, tvNama, tvPangkat, tvSatker, tvEmail;
    Button btnEditProfile, btnLogout;

    String IDUSER;
    private SQLiteHandler db;
    SweetAlertDialog dialog;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        this.getSupportActionBar().setTitle("Profil User");
        cvImageProfile = findViewById(R.id.imageProfile);
        tvNRP = findViewById(R.id.textNRPProfil);
        tvNama = findViewById(R.id.textNamaProfil);
        tvPangkat = findViewById(R.id.textPangkatProfil);
        tvSatker = findViewById(R.id.textSatkerProfil);
        tvEmail= findViewById(R.id.textEmailProfil);
//        btnEditProfile = findViewById(R.id.buttonEditProfile);
        btnLogout = findViewById(R.id.buttonLogoutProfile);

        db = new SQLiteHandler(ProfilActivity.this);
        session = new SessionManager(ProfilActivity.this);
        if (!session.isLoggedIn()) {
            logoutUser();
        }else{
            HashMap<String, String> user = db.getUserDetails();
            IDUSER = user.get("kode");
            ProfilUser(IDUSER);
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

    }
    public void ProfilUser(final String id_user){
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#3F51B5"));
        dialog.setTitleText(R.string.loadingProgress);
        dialog.setCancelable(true);
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
                        tvNRP.setText(user.getString("nrp"));
                        tvNama.setText(user.getString("username"));
                        tvSatker.setText(user.getString("satker_nama"));
                        tvPangkat.setText(user.getString("jabatan"));
                        tvEmail.setText(user.getString("email"));
//                        Picasso.with(ProfilActivity.this)
//                                .load(APIConfig.urlAPI+"/upload/profil/"+user.getString("foto"))
//                                .placeholder(R.drawable.defaultimage)
//                                .into(cvImageProfile);
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
    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProfilActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUser();
        Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}