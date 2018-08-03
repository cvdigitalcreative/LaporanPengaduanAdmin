package com.example.user.laporanpengaduanadmin.mAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.laporanpengaduanadmin.R;

import java.util.ArrayList;

/**
 * Created by USER on 16/10/2017.
 */

public class MonitoringKategoriAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> ListKategori;
    private LayoutInflater inflater;

    public MonitoringKategoriAdapter(Context context, ArrayList<String> listKategori) {
        this.context = context;
        ListKategori = listKategori;
    }

    @Override
    public Object getItem(int position) {
        return ListKategori.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return ListKategori.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.monitoring_kategori_item,parent,false);
        }

        TextView tvKategori = convertView.findViewById(R.id.tv_kategoriItem);

        tvKategori.setText(ListKategori.get(position));
        return convertView;
    }
}
