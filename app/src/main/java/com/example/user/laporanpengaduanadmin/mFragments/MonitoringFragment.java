package com.example.user.laporanpengaduanadmin.mFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.laporanpengaduanadmin.R;

public class MonitoringFragment extends Fragment {
    public MonitoringFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitoring, container, false);

        getFragmentManager().beginTransaction()
                .add(R.id.monitor_container,
                        new KategoriFragment(),
                        KategoriFragment.class.getSimpleName()).commit();

        return view;

    }
}
