package com.example.user.laporanpengaduanadmin.mAdapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class KategoriDetailListAdapter extends BaseAdapter {
    Activity mActivity;
    ArrayList<HistoryInfo> ListKategoriSelected;
    LayoutInflater inflater;
    HistoryInfo hInfo;

    public KategoriDetailListAdapter(Activity mActivity, ArrayList<HistoryInfo> hinfo) {
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
            convertView = inflater.inflate(R.layout.detail_kategori_item,parent,false);
        }

        TextView tvTanggal = convertView.findViewById(R.id.tv_tanggalDetailItem);
        TextView tvKategori = convertView.findViewById(R.id.tv_kategoriDetailItem);
        TextView tvNama = convertView.findViewById(R.id.tv_namaDetailItem);
        TextView tvGmail = convertView.findViewById(R.id.tv_gmailDetailItem);
        TextView tvDeskripsi = convertView.findViewById(R.id.tv_deskripsiDetailItem);
        ImageView ivFoto = convertView.findViewById(R.id.iv_fotoDetailItem);
        TextView tvLokasi = convertView.findViewById(R.id.tv_lokasiDetailItem);
        final Button btnJawaban = convertView.findViewById(R.id.btn_jawaban);

        hInfo = (HistoryInfo) getItem(position);

        tvTanggal.setText("Dilaporkan pada tanggal : "+hInfo.getTanggal());
        tvKategori.setText("Kategori pelaporan: "+hInfo.getKategori());
        tvNama.setText("Nama pelapor : "+hInfo.getNama());
        tvGmail.setText("Email pelapor : "+hInfo.getGmail());
        tvDeskripsi.setText("Deskripsi laporan : "+hInfo.getDeskripsi());
        tvLokasi.setText("Lokasi laporan : "+hInfo.getLokasi());
        Glide.with(mActivity).load(hInfo.getUrl()).into(ivFoto);

        if(hInfo.getStatus().equals("Pengaduan belum dikerjakan")){
            btnJawaban.setText(hInfo.getStatus());
            btnJawaban.setBackgroundColor(Color.RED);
            btnJawaban.setTextColor(Color.WHITE);
        }
        else if(hInfo.getStatus().equals("Pengaduan sedang dikerjakan")){
            btnJawaban.setText(hInfo.getStatus());
            btnJawaban.setBackgroundColor(Color.BLUE);
            btnJawaban.setTextColor(Color.WHITE);
        }
        else{
            btnJawaban.setText(hInfo.getStatus());
            btnJawaban.setBackgroundColor(Color.GREEN);
            btnJawaban.setTextColor(Color.BLACK);
        }

        Button btnHapus = convertView.findViewById(R.id.btn_hapus);

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference;

                final HistoryInfo historyInfo = (HistoryInfo)getItem(position);
                reference = FirebaseDatabase.getInstance().getReference("user/");


                reference.orderByChild("gmail").equalTo(historyInfo.getGmail()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String tgl = dataSnapshot.child("tanggal").getValue(String.class);
                        String ktgr = dataSnapshot.child("kategori").getValue(String.class);
                        String lksi = dataSnapshot.child("lokasi").getValue(String.class);

                        if(tgl.equals(historyInfo.getTanggal()) && ktgr.equals(historyInfo.getKategori()) && lksi.equals(historyInfo.getLokasi())){
                            System.out.println("data yang dihapus" + lksi);
                            dataSnapshot.child("nama").getRef().removeValue();
                            dataSnapshot.child("kategori").getRef().removeValue();
                            dataSnapshot.child("deskripsi").getRef().removeValue();
                            dataSnapshot.child("lokasi").getRef().removeValue();
                            dataSnapshot.child("tanggal").getRef().removeValue();
                            dataSnapshot.child("url").getRef().removeValue();
                            dataSnapshot.child("gmail").getRef().removeValue();
                            dataSnapshot.child("status").getRef().removeValue();
                            dataSnapshot.child("tanggalselesai").getRef().removeValue();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


        btnJawaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final HistoryInfo historyInfopilih = (HistoryInfo)getItem(position);
                String status = historyInfopilih.getStatus();
                final String tanggal_db = historyInfopilih.getTanggal();

                if(status.equals("Pengaduan belum dikerjakan")){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
                    LayoutInflater layoutInflater = mActivity.getLayoutInflater();
                    View mView = layoutInflater.inflate(R.layout.pop_up_belumdikerjakan, null);

                    final EditText et_tanggalMulai = mView.findViewById(R.id.et_tglMulai);
                    final EditText et_tanggalSelesai = mView.findViewById(R.id.et_tglSelesai);
                    Button btnSimpan = mView.findViewById(R.id.btn_Simpan);
                    Button btnKeluar = mView.findViewById(R.id.btn_Keluar);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);

                    btnSimpan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String tanggalMulai = et_tanggalMulai.getText().toString();
                            final String tanggalSelesai = et_tanggalSelesai.getText().toString();
                            String lama_pengerjaan = "0";

                            DateFormat formatter ;
                            Date dateMulai = null, dateSelesai= null;

                            formatter = new SimpleDateFormat("dd-MM-yyyy");

                            try {
                                dateMulai = formatter.parse(tanggalMulai);
                                dateSelesai = formatter.parse(tanggalSelesai);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            long timeDifference = dateSelesai.getTime()- dateMulai.getTime();

                            int daysInBetween = (int)(timeDifference / (24*60*60*1000));
                            lama_pengerjaan = String.valueOf(daysInBetween);

                            DatabaseReference reference;

                            reference = FirebaseDatabase.getInstance().getReference("user/");
                            final HistoryInfo historyInfo = (HistoryInfo)getItem(position);

                            final String finalLamaPengerjaan = lama_pengerjaan;
                            reference.orderByChild("gmail").equalTo(historyInfo.getGmail()).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    String tgl = dataSnapshot.child("tanggal").getValue(String.class);
                                    String ktgr = dataSnapshot.child("kategori").getValue(String.class);
                                    String lksi = dataSnapshot.child("lokasi").getValue(String.class);

                                    if(tgl.equals(historyInfo.getTanggal()) && ktgr.equals(historyInfo.getKategori()) && lksi.equals(historyInfo.getLokasi())){
                                        dataSnapshot.child("status").getRef().setValue("Pengaduan sedang dikerjakan");
                                        dataSnapshot.child("lamapengerjaan").getRef().setValue(finalLamaPengerjaan);
                                        dataSnapshot.child("tanggalselesai").getRef().setValue(tanggalSelesai);
                                        dataSnapshot.child("tanggalawal").getRef().setValue(tanggalMulai);
                                        dataSnapshot.child("minggu1").getRef().setValue("-");
                                        dataSnapshot.child("minggu2").getRef().setValue("-");
                                        dataSnapshot.child("minggu3").getRef().setValue("-");
                                        dataSnapshot.child("minggu4").getRef().setValue("-");
                                    }
                                    dialog.dismiss();
                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });

                    btnKeluar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
                else if(status.equals("Pengaduan sedang dikerjakan")){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
                    LayoutInflater layoutInflater = mActivity.getLayoutInflater();
                    View mView = layoutInflater.inflate(R.layout.pop_up_sedangdikerjakan, null);

                    final TextView tvOnProgress = mView.findViewById(R.id.tv_sedangdikerjakanPengaduan);
                    final Spinner sMinggu = mView.findViewById(R.id.s_minggu);
                    final EditText etMinggu = mView.findViewById(R.id.et_minggu);
                    final Button btnSimpan = mView.findViewById(R.id.btn_Simpan);
                    Button btnKeluar = mView.findViewById(R.id.btn_Keluar);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    DatabaseReference reference;

                    reference = FirebaseDatabase.getInstance().getReference("user/");
                    final HistoryInfo historyInfo = (HistoryInfo)getItem(position);

                    reference.orderByChild("gmail").equalTo(historyInfo.getGmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(final DataSnapshot snapshot : dataSnapshot.getChildren() ){
                                String tgl = snapshot.child("tanggal").getValue(String.class);
                                String ktgr = snapshot.child("kategori").getValue(String.class);
                                String lksi = snapshot.child("lokasi").getValue(String.class);
                                String tgl_slsai = snapshot.child("tanggalselesai").getValue(String.class);

                                if(tgl.equals(historyInfo.getTanggal()) && ktgr.equals(historyInfo.getKategori()) && lksi.equals(historyInfo.getLokasi())){

                                    DateFormat formatter ;
                                    Date dateEnd = null, dateNow= null;

                                    formatter = new SimpleDateFormat("dd-MM-yyyy");

                                    try {
                                        dateEnd = formatter.parse(tgl_slsai);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    final String date_akhir = formatter.format(dateEnd);

                                    String Currentdate = formatter.format(new Date());
                                    try {
                                        dateNow = formatter.parse(Currentdate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    if(dateNow.equals(dateEnd)){
                                        snapshot.child("status").getRef().setValue("Pengaduan sudah dikerjakan");
                                    }
                                    else if(dateNow.before(dateEnd)){
                                        String minggu_now = "-";

                                        if(snapshot.hasChild("minggu1")){
                                            if(!snapshot.child("minggu1").getValue().toString().equals("-")){
                                                minggu_now = snapshot.child("minggu1").getValue().toString();
                                            }
                                        }

                                        if(snapshot.hasChild("minggu2")){
                                            if(!snapshot.child("minggu2").getValue().toString().equals("-")){
                                                minggu_now = snapshot.child("minggu2").getValue().toString();
                                            }
                                        }

                                        if(snapshot.hasChild("minggu3")){
                                            if(!snapshot.child("minggu3").getValue().toString().equals("-")){
                                                minggu_now = snapshot.child("minggu3").getValue().toString();
                                            }
                                        }

                                        if(snapshot.hasChild("minggu4")){
                                            if(!snapshot.child("minggu4").getValue().toString().equals("-")){
                                                minggu_now = snapshot.child("minggu4").getValue().toString();
                                            }
                                        }

                                        if(minggu_now == "-"){
                                            tvOnProgress.setText("pengerjaan pengaduan sedang dikerjakan dan akan selesai pada tanggal "+date_akhir);
                                        }
                                        else{
                                            tvOnProgress.setText("pengerjaan pengaduan sedang mengerjakan "+minggu_now+" dan akan selesai pada tanggal "+date_akhir);
                                        }

                                        btnSimpan.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(mActivity.getApplicationContext(), etMinggu.getText().toString(), Toast.LENGTH_SHORT).show();
                                                String minggu_1 = "-";
                                                String minggu_2 = "-";
                                                String minggu_3 = "-";
                                                String minggu_4 = "-";

                                                if(snapshot.hasChild("minggu1")){
                                                    if(sMinggu.getSelectedItem().toString().equals("Minggu 1")){
                                                        minggu_1 = etMinggu.getText().toString();
                                                    }
                                                    else{
                                                        minggu_1 = snapshot.child("minggu1").getValue().toString();
                                                    }
                                                }

                                                if(snapshot.hasChild("minggu2")){
                                                    if(sMinggu.getSelectedItem().toString().equals("Minggu 2")){
                                                        minggu_2 = etMinggu.getText().toString();
                                                    }
                                                    else{
                                                        minggu_2 = snapshot.child("minggu2").getValue().toString();
                                                    }
                                                }

                                                if(snapshot.hasChild("minggu3")){
                                                    if(sMinggu.getSelectedItem().toString().equals("Minggu 3")){
                                                        minggu_3 = etMinggu.getText().toString();
                                                    }
                                                    else{
                                                        minggu_3 = snapshot.child("minggu3").getValue().toString();
                                                    }
                                                }

                                                if(snapshot.hasChild("minggu4")){
                                                    if(sMinggu.getSelectedItem().toString().equals("Minggu 4")){
                                                        minggu_4 = etMinggu.getText().toString();
                                                    }
                                                    else{
                                                        minggu_4 = snapshot.child("minggu4").getValue().toString();
                                                    }
                                                }

                                                if(minggu_1.equals("-") && minggu_2.equals("-") && minggu_3.equals("-") && minggu_4.equals("-")){
                                                    if(sMinggu.getSelectedItem().toString().equals("Minggu 1")){
                                                        minggu_1 = etMinggu.getText().toString();
                                                    }
                                                    else if(sMinggu.getSelectedItem().toString().equals("Minggu 2")){
                                                        minggu_2 = etMinggu.getText().toString();
                                                    }
                                                    else if(sMinggu.getSelectedItem().toString().equals("Minggu 3")){
                                                        minggu_3 = etMinggu.getText().toString();
                                                    }
                                                    else if(sMinggu.getSelectedItem().toString().equals("Minggu 4")){
                                                        minggu_4 = etMinggu.getText().toString();
                                                    }
                                                }

                                                snapshot.child("minggu1").getRef().setValue(minggu_1);
                                                snapshot.child("minggu2").getRef().setValue(minggu_2);
                                                snapshot.child("minggu3").getRef().setValue(minggu_3);
                                                snapshot.child("minggu4").getRef().setValue(minggu_4);
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();
                                        dialog.setCanceledOnTouchOutside(false);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    btnKeluar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
                else{
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
                    LayoutInflater layoutInflater = mActivity.getLayoutInflater();
                    View mView = layoutInflater.inflate(R.layout.pop_up_sudahdikerjakan, null);

                    final TextView tvSelesai = mView.findViewById(R.id.tv_selesaiPengaduan);
                    Button btnKeluar = mView.findViewById(R.id.btn_Keluar);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    DatabaseReference reference;

                    reference = FirebaseDatabase.getInstance().getReference("user/");
                    final HistoryInfo historyInfo = (HistoryInfo)getItem(position);

                    reference.orderByChild("gmail").equalTo(historyInfo.getGmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                String tgl = snapshot.child("tanggal").getValue(String.class);
                                String ktgr = snapshot.child("kategori").getValue(String.class);
                                String lksi = snapshot.child("lokasi").getValue(String.class);

                                if(tgl.equals(historyInfo.getTanggal()) && ktgr.equals(historyInfo.getKategori()) && lksi.equals(historyInfo.getLokasi())){
                                    tvSelesai.setText("Pengaduan telah selesai dikerjakan");
                                    dialog.show();
                                    dialog.setCanceledOnTouchOutside(false);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    btnKeluar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
