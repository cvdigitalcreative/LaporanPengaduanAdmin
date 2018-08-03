package com.example.user.laporanpengaduanadmin.mAdapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.laporanpengaduanadmin.R;
import com.example.user.laporanpengaduanadmin.mDatas.HistoryInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by USER on 16/10/2017.
 */

public class MonitoringDataAdapter extends BaseAdapter {
    Activity mActivity;
    ArrayList<HistoryInfo> ListKategoriSelected;
    LayoutInflater inflater;
    HistoryInfo hInfo;
    DatabaseReference reference;

    public MonitoringDataAdapter(Activity mActivity, ArrayList<HistoryInfo> hinfo) {
        this.mActivity= mActivity;
        this.ListKategoriSelected = hinfo;
    }

    @Override
    public int getCount() {
        return ListKategoriSelected.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return ListKategoriSelected.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.monitoring_item,parent,false);
        }

        TextView tvKategoriPengerjaan = convertView.findViewById(R.id.tv_kategoriPengerjaan);
        TextView tvNamaPengerjaan = convertView.findViewById(R.id.tv_namaPengerjaan);
        TextView tvLokasiPengerjaan = convertView.findViewById(R.id.tv_lokasiPengerjaan);
        TextView tvKategori= convertView.findViewById(R.id.tv_kategori);
        TextView tvLokasi= convertView.findViewById(R.id.tv_lokasi);
        TextView tvLamaPengerjaan = convertView.findViewById(R.id.tv_lamaPengerjaan);
        final TextView tvTanggalAwal = convertView.findViewById(R.id.tv_tanggalAwal);
        TextView tvTanggalSelesai = convertView.findViewById(R.id.tv_tanggalSelesai);
        TextView tvMinggu1 = convertView.findViewById(R.id.tv_minggu1Detail);
        TextView tvMinggu2 = convertView.findViewById(R.id.tv_minggu2Detail);
        TextView tvMinggu3 = convertView.findViewById(R.id.tv_minggu3Detail);
        TextView tvMinggu4 = convertView.findViewById(R.id.tv_minggu4Detail);
        TextView tvGmail = convertView.findViewById(R.id.tv_gmail);
        TextView tvTanggal = convertView.findViewById(R.id.tv_tanggal);
        final CheckBox tvCentang1 = convertView.findViewById(R.id.tv_centang1Detail);
        final CheckBox tvCentang2 = convertView.findViewById(R.id.tv_centang2Detail);
        final CheckBox tvCentang3 = convertView.findViewById(R.id.tv_centang3Detail);
        final CheckBox tvCentang4 = convertView.findViewById(R.id.tv_centang4Detail);

        hInfo = (HistoryInfo) getItem(position);
        tvKategoriPengerjaan.setText("Kategori Pengerjaan : "+hInfo.getKategori());
        tvNamaPengerjaan.setText("Nama Pengadu : "+hInfo.getNama());
        tvLokasiPengerjaan.setText("Lokasi Pengerjaan : "+hInfo.getLokasi());
        tvLamaPengerjaan.setText("Lama Pengerjaan : "+hInfo.getLamaPengerjaan());
        tvTanggalAwal.setText("Tanggal Awal : "+hInfo.getTanggalAwal());
        tvTanggalSelesai.setText("Tanggal Selesai : "+hInfo.getTanggalSelesai());
        tvMinggu1.setText("1. "+hInfo.getMinggu1());
        tvMinggu2.setText("2. "+hInfo.getMinggu2());
        tvMinggu3.setText("3. "+hInfo.getMinggu3());
        tvMinggu4.setText("4. "+hInfo.getMinggu4());
        tvGmail.setText(hInfo.getGmail());
        tvTanggal.setText(hInfo.getTanggal());
        tvLokasi.setText(hInfo.getLokasi());
        tvKategori.setText(hInfo.getKategori());

        if(hInfo.getCheckMinggu1().equals("belum dikerjakan")){
            tvCentang1.setChecked(false);
            tvCentang1.setText("belum dikerjakan");
        }
        else{
            tvCentang1.setChecked(true);
            tvCentang1.setText("sudah dikerjakan");
        }

        if(hInfo.getCheckMinggu2().equals("belum dikerjakan")){
            tvCentang2.setChecked(false);
            tvCentang2.setText("belum dikerjakan");
        }
        else{
            tvCentang2.setChecked(true);
            tvCentang2.setText("sudah dikerjakan");
        }

        if(hInfo.getCheckMinggu3().equals("belum dikerjakan")){
            tvCentang3.setChecked(false);
            tvCentang3.setText("belum dikerjakan");
        }
        else{
            tvCentang3.setChecked(true);
            tvCentang3.setText("sudah dikerjakan");
        }

        if(hInfo.getCheckMinggu4().equals("belum dikerjakan")){
            tvCentang4.setChecked(false);
            tvCentang4.setText("belum dikerjakan");
        }
        else{
            tvCentang4.setChecked(true);
            tvCentang4.setText("sudah dikerjakan");
        }

        /*Toast.makeText(mActivity.getApplicationContext(), hInfo.getGmail(), Toast.LENGTH_SHORT).show();
        if(hInfo.getCheckMinggu1().equals("belum dikerjakan")){
            tvCentang1.setChecked(false);
            tvCentang1.setText("belum dikerjakan");
        }
        else if(hInfo.getCheckMinggu2().equals("belum dikerjakan") && hInfo.getCheckMinggu1().equals("sudah dikerjakan")){
            tvCentang1.setChecked(true);
            tvCentang2.setChecked(false);
            tvCentang1.setEnabled(false);
            tvCentang1.setText("sudah dikerjakan");
            tvCentang2.setText("belum dikerjakan");
        }
        else if(hInfo.getCheckMinggu3().equals("belum dikerjakan") && hInfo.getCheckMinggu2().equals("sudah dikerjakan") && hInfo.getCheckMinggu1().equals("sudah dikerjakan")){
            tvCentang1.setChecked(true);
            tvCentang2.setChecked(true);
            tvCentang1.setEnabled(false);
            tvCentang2.setEnabled(false);
            tvCentang3.setChecked(false);
            tvCentang1.setText("sudah dikerjakan");
            tvCentang2.setText("sudah dikerjakan");
            tvCentang3.setText("belum dikerjakan");
        }
        else if(hInfo.getCheckMinggu4().equals("belum dikerjakan") && hInfo.getCheckMinggu3().equals("sudah dikerjakan") && hInfo.getCheckMinggu2().equals("sudah dikerjakan") && hInfo.getCheckMinggu1().equals("sudah dikerjakan")){
            tvCentang1.setChecked(true);
            tvCentang2.setChecked(true);
            tvCentang3.setChecked(true);
            tvCentang1.setEnabled(false);
            tvCentang2.setEnabled(false);
            tvCentang3.setEnabled(false);
            tvCentang4.setChecked(false);
            tvCentang1.setText("sudah dikerjakan");
            tvCentang2.setText("sudah dikerjakan");
            tvCentang3.setText("sudah dikerjakan");
            tvCentang4.setText("belum dikerjakan");
        }
        else if(hInfo.getCheckMinggu4().equals("sudah dikerjakan") && hInfo.getCheckMinggu3().equals("sudah dikerjakan") && hInfo.getCheckMinggu2().equals("sudah dikerjakan") && hInfo.getCheckMinggu1().equals("sudah dikerjakan")){
            tvCentang1.setChecked(true);
            tvCentang2.setChecked(true);
            tvCentang3.setChecked(true);
            tvCentang4.setChecked(true);
            tvCentang1.setEnabled(false);
            tvCentang2.setEnabled(false);
            tvCentang3.setEnabled(false);
            tvCentang4.setEnabled(false);
            tvCentang1.setText("sudah dikerjakan");
            tvCentang2.setText("sudah dikerjakan");
            tvCentang3.setText("sudah dikerjakan");
            tvCentang4.setText("sudah dikerjakan");
        }
        else if(hInfo.getCheckMinggu4().equals("belum dikerjakan") && hInfo.getCheckMinggu3().equals("belum dikerjakan") && hInfo.getCheckMinggu2().equals("belum dikerjakan") && hInfo.getCheckMinggu1().equals("belum dikerjakan")){
            tvCentang1.setChecked(false);
            tvCentang2.setChecked(false);
            tvCentang3.setChecked(false);
            tvCentang4.setChecked(false);
            tvCentang1.setText("belum dikerjakan");
            tvCentang2.setText("belum dikerjakan");
            tvCentang3.setText("belum dikerjakan");
            tvCentang4.setText("belum dikerjakan");
        }*/

        tvCentang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryInfo historyInfopilih = (HistoryInfo)getItem(position);

                if(!historyInfopilih.getTanggalAwal().equals("-")){
                    if(historyInfopilih.getCheckMinggu1().equals("belum dikerjakan")){
                        tvCentang1.setChecked(false);
                    }
                    else{
                        tvCentang1.setChecked(true);
                    }

                    final String gmail = historyInfopilih.getGmail();
                    final String lokasi = historyInfopilih.getLokasi();
                    final String kategori = historyInfopilih.getKategori();
                    final String tanggal = historyInfopilih.getTanggal();

                    DatabaseReference reference;
                    reference = FirebaseDatabase.getInstance().getReference("user");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String emailDB = snapshot.child("gmail").getValue().toString();
                                String tanggalDB = snapshot.child("tanggal").getValue().toString();
                                String kategoriDB = snapshot.child("kategori").getValue().toString();
                                String lokasiDB = snapshot.child("lokasi").getValue().toString();

                                if(gmail.equals(emailDB) && tanggal.equals(tanggalDB) && kategori.equals(kategoriDB) && lokasi.equals(lokasiDB)){
                                    if(tvCentang1.isChecked()){
                                        snapshot.child("checkminggu1").getRef().setValue("belum dikerjakan");
                                    }
                                    else{
                                        snapshot.child("checkminggu1").getRef().setValue("sudah dikerjakan");
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        tvCentang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryInfo historyInfopilih = (HistoryInfo)getItem(position);

                if(!historyInfopilih.getTanggalAwal().equals("-")){
                    if(historyInfopilih.getCheckMinggu2().equals("belum dikerjakan")){
                        tvCentang2.setChecked(false);
                    }
                    else{
                        tvCentang2.setChecked(true);
                    }

                    final String gmail = historyInfopilih.getGmail();
                    final String lokasi = historyInfopilih.getLokasi();
                    final String kategori = historyInfopilih.getKategori();
                    final String tanggal = historyInfopilih.getTanggal();

                    DatabaseReference reference;
                    reference = FirebaseDatabase.getInstance().getReference("user");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String emailDB = snapshot.child("gmail").getValue().toString();
                                String tanggalDB = snapshot.child("tanggal").getValue().toString();
                                String kategoriDB = snapshot.child("kategori").getValue().toString();
                                String lokasiDB = snapshot.child("lokasi").getValue().toString();

                                if(gmail.equals(emailDB) && tanggal.equals(tanggalDB) && kategori.equals(kategoriDB) && lokasi.equals(lokasiDB)){
                                    if(tvCentang2.isChecked()){
                                        snapshot.child("checkminggu2").getRef().setValue("belum dikerjakan");
                                    }
                                    else{
                                        snapshot.child("checkminggu2").getRef().setValue("sudah dikerjakan");
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        tvCentang3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryInfo historyInfopilih = (HistoryInfo)getItem(position);

                if(!historyInfopilih.getTanggalAwal().equals("-")){
                    if(historyInfopilih.getCheckMinggu3().equals("belum dikerjakan")){
                        tvCentang3.setChecked(false);
                    }
                    else{
                        tvCentang3.setChecked(true);
                    }

                    final String gmail = historyInfopilih.getGmail();
                    final String lokasi = historyInfopilih.getLokasi();
                    final String kategori = historyInfopilih.getKategori();
                    final String tanggal = historyInfopilih.getTanggal();

                    DatabaseReference reference;
                    reference = FirebaseDatabase.getInstance().getReference("user");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String emailDB = snapshot.child("gmail").getValue().toString();
                                String tanggalDB = snapshot.child("tanggal").getValue().toString();
                                String kategoriDB = snapshot.child("kategori").getValue().toString();
                                String lokasiDB = snapshot.child("lokasi").getValue().toString();

                                if(gmail.equals(emailDB) && tanggal.equals(tanggalDB) && kategori.equals(kategoriDB) && lokasi.equals(lokasiDB)){
                                    if(tvCentang3.isChecked()){
                                        snapshot.child("checkminggu3").getRef().setValue("belum dikerjakan");
                                    }
                                    else{
                                        snapshot.child("checkminggu3").getRef().setValue("sudah dikerjakan");
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        tvCentang4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryInfo historyInfopilih = (HistoryInfo)getItem(position);

                if(!historyInfopilih.getTanggalAwal().equals("-")){
                    if(historyInfopilih.getCheckMinggu4().equals("belum dikerjakan")){
                        tvCentang4.setChecked(false);
                    }
                    else{
                        tvCentang4.setChecked(true);
                    }

                    final String gmail = historyInfopilih.getGmail();
                    final String lokasi = historyInfopilih.getLokasi();
                    final String kategori = historyInfopilih.getKategori();
                    final String tanggal = historyInfopilih.getTanggal();

                    DatabaseReference reference;
                    reference = FirebaseDatabase.getInstance().getReference("user");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String emailDB = snapshot.child("gmail").getValue().toString();
                                String tanggalDB = snapshot.child("tanggal").getValue().toString();
                                String kategoriDB = snapshot.child("kategori").getValue().toString();
                                String lokasiDB = snapshot.child("lokasi").getValue().toString();

                                if(gmail.equals(emailDB) && tanggal.equals(tanggalDB) && kategori.equals(kategoriDB) && lokasi.equals(lokasiDB)){
                                    if(tvCentang4.isChecked()){
                                        snapshot.child("checkminggu4").getRef().setValue("belum dikerjakan");
                                    }
                                    else{
                                        snapshot.child("checkminggu4").getRef().setValue("sudah dikerjakan");
                                        snapshot.child("status").getRef().setValue("Pengaduan sudah dikerjakan");
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        return convertView;
    }

}
