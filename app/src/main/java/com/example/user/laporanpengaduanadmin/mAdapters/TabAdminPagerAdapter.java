package com.example.user.laporanpengaduanadmin.mAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.laporanpengaduanadmin.mFragments.AboutFragment;
import com.example.user.laporanpengaduanadmin.mFragments.MonitoringFragment;
import com.example.user.laporanpengaduanadmin.mFragments.MonitoringKategoriFragment;
import com.example.user.laporanpengaduanadmin.mFragments.MonitoringPengerjaanFragment;
import com.example.user.laporanpengaduanadmin.mFragments.ProfileFragment;

public class TabAdminPagerAdapter extends FragmentPagerAdapter{
    private String[] title = new String[]{
            "Data Monitoring", "Monitoring Pengerjaan", "About", "Logout"
    };

    public TabAdminPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new MonitoringFragment();
                break;
            case 1:
                fragment = new MonitoringPengerjaanFragment();
                break;
            case 2:
                fragment = new AboutFragment();
                break;
            case 3:
                fragment = new ProfileFragment();
                break;
            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public int getCount() {
        return title.length;
    }
}
