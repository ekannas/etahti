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

import com.aplikasianaknegeri.e_tahti.DetailBuktiActivity;
import com.aplikasianaknegeri.e_tahti.DetailTahananActivity;
import com.aplikasianaknegeri.e_tahti.R;
import com.aplikasianaknegeri.e_tahti.model.barangbukti;
import com.aplikasianaknegeri.e_tahti.model.tahanan;

import java.util.ArrayList;

public class BuktiAdapter extends RecyclerView.Adapter<BuktiAdapter.RvViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<barangbukti> barangbuktis;
    private Context mContext;
    public BuktiAdapter(Context context, ArrayList<barangbukti> barangbuktis){
        inflater = LayoutInflater.from(context);
        this.barangbuktis = barangbuktis;
        mContext = context;
    }
    public BuktiAdapter.RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.item_barangbukti, parent, false);
        BuktiAdapter.RvViewHolder holder = new BuktiAdapter.RvViewHolder(view);
        return holder;
    }
    public void onBindViewHolder(final BuktiAdapter.RvViewHolder holder, final int position){
        final String idtahanan = barangbuktis.get(position).getId();
        holder.tvTanggal.setText(barangbuktis.get(position).getTanggal());
        holder.tvKode.setText(barangbuktis.get(position).getKode());
        holder.tvNIK.setText(barangbuktis.get(position).getNik());

        holder.cvBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailBuktiActivity.class);
                i.putExtra("idunik", idtahanan);
                mContext.startActivity(i);
                ((Activity)mContext).finish();
            }
        });
    }
    public int getItemCount()
    {
        return barangbuktis.size();
    }

    public class RvViewHolder extends RecyclerView.ViewHolder{
        TextView tvTanggal, tvKode, tvNIK;
        CardView cvBukti;
        public RvViewHolder(View itemView){
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.textTglInputBukti);
            tvNIK = itemView.findViewById(R.id.textNIKBukti);
            tvKode = itemView.findViewById(R.id.textKodeBukti);
            cvBukti = itemView.findViewById(R.id.cardBarangBuktiItem);
        }
    }
}
