package com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.HistoryStopBikerFragment;
import com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.SalaryBikerFragment;

public class PagerParentHistoryBikerAdapter extends FragmentStatePagerAdapter {


    public PagerParentHistoryBikerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HistoryStopBikerFragment();
                break;
            case 1:
                fragment = new SalaryBikerFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Lịch sử giao";
                break;
            case 1:
                title = "Phiếu lương";
                break;
        }
        return title;
    }
}
