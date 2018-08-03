package com.example.user.laporanpengaduanadmin.mAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.laporanpengaduanadmin.R;
import com.example.user.laporanpengaduanadmin.mDatas.HistoryInfo;

import java.util.ArrayList;

/**
 * Created by USER on 03/09/2017.
 */

public class LaporanAkhirAdapter extends BaseAdapter {
    Context context;
    ArrayList<HistoryInfo> AllPengaduan;

    public LaporanAkhirAdapter(Context context, ArrayList<HistoryInfo> allPengaduan) {
        this.context = context;
        AllPengaduan = allPengaduan;
    }

    @Override
    public int getCount() {
        return AllPengaduan.size();
    }

    @Override
    public Object getItem(int i) {
        return AllPengaduan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        HistoryInfo hInfo;

        if(convertView == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.laporan_akhir_item,parent,false);
        }

        TextView tvTanggal = convertView.findViewById(R.id.tv_tanggalDetailItem);
        TextView tvKategori = convertView.findViewById(R.id.tv_kategoriDetailItem);
        TextView tvNama = convertView.findViewById(R.id.tv_namaDetailItem);
        TextView tvGmail = convertView.findViewById(R.id.tv_gmailDetailItem);
        TextView tvDeskripsi = convertView.findViewById(R.id.tv_deskripsiDetailItem);
        ImageView ivFoto = convertView.findViewById(R.id.iv_fotoDetailItem);
        TextView tvLokasi = convertView.findViewById(R.id.tv_lokasiDetailItem);
        TextView tvStatus = convertView.findViewById(R.id.tv_statusDetailItem);

        hInfo = (HistoryInfo) getItem(position);

        tvTanggal.setText("Dilaporkan pada tanggal : "+hInfo.getTanggal());
        tvKategori.setText("Kategori pelaporan: "+hInfo.getKategori());
        tvNama.setText("Nama pelapor : "+hInfo.getNama());
        tvGmail.setText("Email pelapor : "+hInfo.getGmail());
        tvDeskripsi.setText("Deskripsi laporan : "+hInfo.getDeskripsi());
        tvLokasi.setText("Lokasi laporan : "+hInfo.getLokasi());

        if(hInfo.getStatus().equals("Pengaduan belum dikerjakan")){
            tvStatus.setText(hInfo.getStatus());
            tvStatus.setBackgroundColor(Color.RED);
            tvStatus.setTextColor(Color.WHITE);
        }
        else if(hInfo.getStatus().equals("Pengaduan sedang dikerjakan")){
            tvStatus.setText(hInfo.getStatus());
            tvStatus.setBackgroundColor(Color.BLUE);
            tvStatus.setTextColor(Color.WHITE);
        }
        else{
            tvStatus.setText(hInfo.getStatus());
            tvStatus.setBackgroundColor(Color.GREEN);
            tvStatus.setTextColor(Color.BLACK);
        }

        Glide.with(context).load(hInfo.getUrl()).into(ivFoto);

        return convertView;
    }
}
