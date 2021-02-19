package com.tandm.abadeliverydriver.main.home.fragmenthome;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;

import java.util.List;

public class BikerCustomerAdapter  extends BaseAdapter implements SpinnerAdapter {

    List<BikerCustomer> bikerCustomerList;
    Context context;

    public BikerCustomerAdapter(List<BikerCustomer> bikerCustomerList, Context context) {
        this.bikerCustomerList = bikerCustomerList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bikerCustomerList.size();
    }

    @Override
    public Object getItem(int position) {
        return bikerCustomerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.item_spinner_biker_customer,null);
        TextView tvSp = view.findViewById(R.id.item_sp_biker_customer);
        tvSp.setText(bikerCustomerList.get(position).customerName);
        return view;
    }




}
