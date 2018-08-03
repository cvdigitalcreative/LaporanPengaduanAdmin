package com.example.user.laporanpengaduanadmin.mFragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.laporanpengaduanadmin.R;
import com.example.user.laporanpengaduanadmin.mAdapters.KategoriDetailListAdapter;
import com.example.user.laporanpengaduanadmin.mAdapters.MonitoringDataAdapter;
import com.example.user.laporanpengaduanadmin.mDatas.HistoryInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonitoringDataFragment extends Fragment {
    private ProgressDialog progressDialog;
    private ArrayList<HistoryInfo> infoHistory;
    private DatabaseReference databaseRef;
    private MonitoringDataAdapter adapter;
    private ListView lvMonitoringData;
    private String kategoriListId;

    public MonitoringDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitoring_data, container, false);

        lvMonitoringData = view.findViewById(R.id.lv_monitoringData);

        Bundle bundle = getArguments();
        kategoriListId = bundle.getString("kategoriID");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Silahkan tunggu proses memuat...");
        progressDialog.show();

        infoHistory = new ArrayList<>();
        databaseRef = FirebaseDatabase.getInstance().getReference("user/");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                infoHistory.clear();

//                System.out.println("testing   change");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("kategori").getValue(String.class) != null && snapshot.child("kategori").getValue(String.class).equals(kategoriListId)){
                        HistoryInfo info = snapshot.getValue(HistoryInfo.class);

                        if(snapshot.hasChild("minggu1")){
                            info.setMinggu1(snapshot.child("minggu1").getValue().toString());
                            info.setMinggu2(snapshot.child("minggu2").getValue().toString());
                            info.setMinggu3(snapshot.child("minggu3").getValue().toString());
                            info.setMinggu4(snapshot.child("minggu4").getValue().toString());
                        }
                        else{
                            info.setMinggu1("-");
                            info.setMinggu2("-");
                            info.setMinggu3("-");
                            info.setMinggu4("-");
                        }

                        if(snapshot.hasChild("lamapengerjaan")){
                            info.setLamaPengerjaan(snapshot.child("lamapengerjaan").getValue().toString());
                        }
                        else {
                            info.setLamaPengerjaan("0");
                        }

                        if(snapshot.hasChild("checkminggu1")){
                            info.setCheckMinggu1(snapshot.child("checkminggu1").getValue().toString());
                            info.setCheckMinggu2(snapshot.child("checkminggu2").getValue().toString());
                            info.setCheckMinggu3(snapshot.child("checkminggu3").getValue().toString());
                            info.setCheckMinggu4(snapshot.child("checkminggu4").getValue().toString());
                        }
                        else{
                            info.setCheckMinggu1("belum dikerjakan");
                            info.setCheckMinggu2("belum dikerjakan");
                            info.setCheckMinggu3("belum dikerjakan");
                            info.setCheckMinggu4("belum dikerjakan");
                        }

                        if(snapshot.hasChild("tanggalawal")){
                            info.setTanggalAwal(snapshot.child("tanggalawal").getValue().toString());
                        }
                        else{
                            info.setTanggalAwal("-");
                        }

                        if(snapshot.hasChild("tanggalselesai")){
                            info.setTanggalSelesai(snapshot.child("tanggalselesai").getValue().toString());
                        }
                        else{
                            info.setTanggalSelesai("-");
                        }

                        infoHistory.add(info);
                    }
                }

                adapter = new MonitoringDataAdapter(getActivity(),infoHistory);
                lvMonitoringData.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}
