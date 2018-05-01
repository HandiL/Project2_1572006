package com.handi.project2_1572006.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.handi.project2_1572006.Entity.Barang;
import com.handi.project2_1572006.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarangAdapter extends  RecyclerView.Adapter<BarangAdapter.BarangViewHolder>{
    private ArrayList<Barang> barangs;
    private BarangDataListener barangDataClickedListener;

    public void setBarangDataClickedListener(BarangDataListener barangDataClickedListener) {
        this.barangDataClickedListener = barangDataClickedListener;
    }

    public ArrayList<Barang> getBarangs() {
        if(barangs==null)
        {
            barangs = new ArrayList<>();
        }
        return barangs;
    }

    public void setBarangs(ArrayList<Barang> barangs) {
        getBarangs().clear();
        getBarangs().addAll(barangs);

    }

    @Override
    public BarangViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_barang, parent, false);
        return new BarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BarangViewHolder holder, int position) {
        final  Barang barang = getBarangs().get(position);
        String idBarang = barang.getIdBarang();
        String namaBarang = barang.getNamaBarang();
        int hargaJual = barang.getHargaJual();
        int hargaBeli = barang.getHargaBeli();
        int stock = barang.getStock();

        holder.tvNamaBarang.setText(namaBarang);
        holder.tvStockBarang.setText(String.valueOf(stock));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(barangDataClickedListener != null)
                {
                    barangDataClickedListener.onBarangClicked(barang);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    class BarangViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvNamaBarang)
        TextView tvNamaBarang;

        @BindView(R.id.tvStockBarang)
        TextView tvStockBarang;

        public BarangViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface BarangDataListener{
        void onBarangClicked(Barang barang);
    }
}
