package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreDriver;

import java.util.List;

public class PickAdapter extends PagerAdapter {

    private Context context;
    private List<StoreDriver> list;

    public PickAdapter(Context context, List<StoreDriver> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_vp, container, false);
        TextView tvPickUp = v.findViewById(R.id.tvPickUpVP) ;
        TextView tvCHDriver = v.findViewById(R.id.tvCHDriverVP);
        TextView tvAddressDriver = v.findViewById(R.id.tvAddressDriverVP);
        tvPickUp.setText(list.get(position).getStoreCode(list.get(position).stoP_TYPE) + " " + String.valueOf(position+1) +"/"+list.size());
        tvCHDriver.setText(list.get(position).store_Name);
        tvAddressDriver.setText(list.get(position).addresS_LINE);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
