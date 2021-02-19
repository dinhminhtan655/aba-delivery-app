package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.FeeType;

import java.util.List;

public class FeeTypeAdapter  extends BaseAdapter implements SpinnerAdapter {

    List<FeeType> feeTypeList;
    Context context;


    public FeeTypeAdapter(List<FeeType> feeTypeList, Context context) {
        this.feeTypeList = feeTypeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return feeTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return feeTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView.inflate(context, R.layout.item_fee_type_spinner, null);
        TextView itemFee2 = view.findViewById(R.id.itemFee2);

        itemFee2.setText(feeTypeList.get(position).feeName);

        return view;
    }
}
