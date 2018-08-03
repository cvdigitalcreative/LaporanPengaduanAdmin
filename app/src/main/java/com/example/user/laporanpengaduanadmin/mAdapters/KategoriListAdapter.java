package com.example.user.laporanpengaduanadmin.mAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.laporanpengaduanadmin.R;
import com.example.user.laporanpengaduanadmin.mDatas.HistoryInfo;

import java.util.ArrayList;

public class KategoriListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> ListKategori;
    private LayoutInflater inflater;

    public KategoriListAdapter(Context context, ArrayList<String> ListKategori) {
        this.context = context;
        this.ListKategori = ListKategori;
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
            convertView = inflater.inflate(R.layout.kategori_item,parent,false);
        }

        TextView tvKategori = (TextView) convertView.findViewById(R.id.tv_kategoriItem);

        tvKategori.setText(ListKategori.get(position));
        return convertView;
    }
}
