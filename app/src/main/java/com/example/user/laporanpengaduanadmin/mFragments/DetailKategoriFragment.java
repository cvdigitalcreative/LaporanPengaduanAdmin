package com.example.user.laporanpengaduanadmin.mFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.laporanpengaduanadmin.R;
import com.example.user.laporanpengaduanadmin.mAdapters.KategoriDetailListAdapter;
import com.example.user.laporanpengaduanadmin.mDatas.HistoryInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailKategoriFragment extends Fragment {
    private ProgressDialog progressDialog;
    private ArrayList<HistoryInfo> infoHistory;
    private DatabaseReference databaseRef;
    private KategoriDetailListAdapter adapter;
    private ListView lvDetailKategori;
    private String kategoriListId;

    public DetailKategoriFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_kategori, container, false);
        lvDetailKategori = (ListView)view.findViewById(R.id.lv_LPKategori);

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

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                infoHistory = new ArrayList<HistoryInfo>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.child("kategori").getValue(String.class) != null && snapshot.child("kategori").getValue(String.class).equals(kategoriListId)){
                        HistoryInfo info = snapshot.getValue(HistoryInfo.class);
                        System.out.println(snapshot.getValue());
                        System.out.println("----------------------");
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
                        else{
                            info.setLamaPengerjaan("0");
                        }

                        infoHistory.add(info);
                    }
                }

                adapter = new KategoriDetailListAdapter(getActivity(),infoHistory);
                lvDetailKategori.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}
