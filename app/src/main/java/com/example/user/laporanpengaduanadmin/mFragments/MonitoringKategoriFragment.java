package com.example.user.laporanpengaduanadmin.mFragments;


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

import com.example.user.laporanpengaduanadmin.R;
import com.example.user.laporanpengaduanadmin.mAdapters.KategoriListAdapter;
import com.example.user.laporanpengaduanadmin.mAdapters.MonitoringKategoriAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonitoringKategoriFragment extends Fragment {
    private ListView lvKategori;
    private MonitoringKategoriAdapter adapter;

    public MonitoringKategoriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitoring_kategori, container, false);

        lvKategori = view.findViewById(R.id.lv_kategori);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> info;
        final String[] kategori = getActivity().getResources().getStringArray(R.array.spinnerItems);
        info = new ArrayList<String>(Arrays.asList(kategori));

        adapter = new MonitoringKategoriAdapter(getContext(), info);
        lvKategori.setAdapter(adapter);

        lvKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String kategori = lvKategori.getItemAtPosition(position).toString();

                System.out.println(kategori);
                Bundle bundle = new Bundle();
                bundle.putString("kategoriID", kategori);

                MonitoringDataFragment mFragment = new MonitoringDataFragment();
                mFragment.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.monitoring_pengerjaan_container,
                                mFragment,
                                MonitoringDataFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
