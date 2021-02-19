package com.tandm.abadeliverydriver.main.home.fragmentHistory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;

import java.util.List;

public class SpinnerCustomerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

    List<Customer> customers;
    Context context;

    public SpinnerCustomerAdapter(List<Customer> customers, Context context) {
        this.customers = customers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Object getItem(int i) {
        return customers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = View.inflate(context, R.layout.item_spinner, null);
        TextView tvSp = view1.findViewById(R.id.item_sp);
        tvSp.setText(i+1 + ". " + customers.get(i).nameCustomer);
        return tvSp;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.dropdown_spinner, null);
        TextView textView = view.findViewById(R.id.dropdown);
        textView.setText(position + 1 + ". " + customers.get(position).nameCustomer);
        return super.getDropDownView(position, convertView, parent);
    }
}
