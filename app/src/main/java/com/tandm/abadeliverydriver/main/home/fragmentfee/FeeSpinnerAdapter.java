package com.tandm.abadeliverydriver.main.home.fragmentfee;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;

import java.util.List;

public class FeeSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    List<Fee> feeList;
    Context context;

    public FeeSpinnerAdapter(List<Fee> feeList, Context context) {
        this.feeList = feeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return feeList.size();
    }

    @Override
    public Object getItem(int i) {
        return feeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view.inflate(context, R.layout.item_spinner, null);
        TextView tvSp = view1.findViewById(R.id.item_sp);
        tvSp.setText(i+1 + ". " + feeList.get(i).tenLoaiPhi);
        return tvSp;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.dropdown_spinner,null);
        TextView textView = view.findViewById(R.id.dropdown);
        textView.setText(position + 1 + "." + feeList.get(position).tenLoaiPhi);
        return view;
    }
}
