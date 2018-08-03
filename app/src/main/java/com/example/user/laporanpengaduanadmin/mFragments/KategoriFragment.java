package com.example.user.laporanpengaduanadmin.mFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.laporanpengaduanadmin.R;
import com.example.user.laporanpengaduanadmin.mAdapters.KategoriListAdapter;
import com.example.user.laporanpengaduanadmin.mDatas.HistoryInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class KategoriFragment extends Fragment {
    private DatabaseReference databaseRef;
    private ListView lvKategori;
    private KategoriListAdapter adapter;
    private Button btnLaporanAkhir;

    public KategoriFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kategori, container, false);
        lvKategori = (ListView)view.findViewById(R.id.lv_kategori);
        btnLaporanAkhir = view.findViewById(R.id.btn_laporan);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> info;
        final String[] kategori = getActivity().getResources().getStringArray(R.array.spinnerItems);
        info = new ArrayList<String>(Arrays.asList(kategori));

        adapter = new KategoriListAdapter(getContext(), info);
        lvKategori.setAdapter(adapter);

        lvKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String kategori = lvKategori.getItemAtPosition(position).toString();

                System.out.println(kategori);
                Bundle bundle = new Bundle();
                bundle.putString("kategoriID", kategori);

                DetailKategoriFragment mFragment = new DetailKategoriFragment();
                mFragment.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.monitor_container,
                                mFragment,
                                DetailKategoriFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnLaporanAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaporanAkhirPengaduanFragment mFragment = new LaporanAkhirPengaduanFragment();
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.monitor_container,
                                mFragment,
                                LaporanAkhirPengaduanFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
