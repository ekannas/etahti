package com.aplikasianaknegeri.e_tahti.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aplikasianaknegeri.e_tahti.DetailTahananActivity;
import com.aplikasianaknegeri.e_tahti.R;
import com.aplikasianaknegeri.e_tahti.model.tahanan;

import java.util.ArrayList;

public class TahananAdapter extends RecyclerView.Adapter<TahananAdapter.RvViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<tahanan> tahanans;
    private Context mContext;

    public TahananAdapter(Context context, ArrayList<tahanan> tahanans){
        inflater = LayoutInflater.from(context);
        this.tahanans = tahanans;
        mContext = context;
    }
    public TahananAdapter.RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_tahanan, parent, false);
        TahananAdapter.RvViewHolder holder = new TahananAdapter.RvViewHolder(view);
        return holder;
    }
    public void onBindViewHolder(final TahananAdapter.RvViewHolder holder, final int position){
        final String idtahanan = tahanans.get(position).getId();
        holder.tvTanggal.setText(tahanans.get(position).getMasuk());
        holder.tvLokasi.setText(tahanans.get(position).getLokasi());
        holder.tvNIK.setText(tahanans.get(position).getNik());
        holder.tvNama.setText(tahanans.get(position).getNama());
        holder.tvUsia.setText(tahanans.get(position).getUsia());
        holder.tvJenkel.setText(tahanans.get(position).getJenkel());
        holder.cvTahanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailTahananActivity.class);
                i.putExtra("id_tahanan", idtahanan);
                mContext.startActivity(i);
                ((Activity)mContext).finish();
            }
        });
    }
    public int getItemCount()
    {
        return tahanans.size();
    }
    public class RvViewHolder extends RecyclerView.ViewHolder{
        TextView tvTanggal, tvLokasi, tvNIK, tvNama, tvUsia, tvJenkel, tvDetail;
        CardView cvTahanan;
        public RvViewHolder(View itemView){
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.textTglItem);
            tvLokasi = itemView.findViewById(R.id.textLokasiItem);
            tvNIK = itemView.findViewById(R.id.textNIKItem);
            tvNama = itemView.findViewById(R.id.textNamaItem);
            tvUsia = itemView.findViewById(R.id.textUsiaItem);
            tvJenkel = itemView.findViewById(R.id.textJenkelItem);
            tvDetail = itemView.findViewById(R.id.textDetail);
            cvTahanan = itemView.findViewById(R.id.cardTahananItem);
        }
    }
}
