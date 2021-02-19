package com.tandm.abadeliverydriver.main.home.fragmentvouchers.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LocationATM;

import java.util.List;

public class LocationATMSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    List<LocationATM> locationATMList;
    Context context;


    public LocationATMSpinnerAdapter(List<LocationATM> locationATMList, Context context) {
        this.locationATMList = locationATMList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return locationATMList.size();
    }

    @Override
    public Object getItem(int i) {
        return locationATMList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = View.inflate(context, R.layout.item_spinner, null);
        TextView tvSp = view1.findViewById(R.id.item_sp);
        tvSp.setText(i+1 + ". " + locationATMList.get(i).locatioN_NAME);
        return tvSp;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.dropdown_spinner,null);
        TextView textView = view.findViewById(R.id.dropdown);
        textView.setText(position + 1 + "." + locationATMList.get(position).locatioN_NAME);
        return view;
    }
}
