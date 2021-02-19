package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.distributionnormswarning.DistributionNormsWarningFragment;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historydriverdelivery.HistoryDriverDeliveryFragment;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.PaySlipListFragment;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.SalaryFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {


    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SalaryFragment();
                break;
            case 1:
                fragment = new PaySlipListFragment();
                break;
            case 2:
                fragment = new HistoryDriverDeliveryFragment();
                break;
            case 3:
                fragment = new DistributionNormsWarningFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Lịch sử";
                break;
            case 1:
                title = "Phiếu Lương";
                break;
            case 2:
                title = "Đã giao";
                break;
            case 3:
                title = "Đinh Mức Dầu";
                break;
        }
        return title;

    }
}
