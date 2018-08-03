package com.example.user.laporanpengaduanadmin.mFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.laporanpengaduanadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonitoringPengerjaanFragment extends Fragment {


    public MonitoringPengerjaanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitoring_pengerjaan, container, false);

        getFragmentManager().beginTransaction()
                .add(R.id.monitoring_pengerjaan_container,
                        new MonitoringKategoriFragment(),
                        MonitoringKategoriFragment.class.getSimpleName()).commit();

        return view;
    }

}
