package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ShipmentAdvancePayment;

import java.util.List;

public class ShipmentAdvancePaymentAdapter extends BaseAdapter implements SpinnerAdapter {


    List<ShipmentAdvancePayment> shipmentAdvancePaymentList;
    Context context;

    public ShipmentAdvancePaymentAdapter(List<ShipmentAdvancePayment> shipmentAdvancePaymentList, Context context) {
        this.shipmentAdvancePaymentList = shipmentAdvancePaymentList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shipmentAdvancePaymentList.size();
    }

    @Override
    public Object getItem(int position) {
        return shipmentAdvancePaymentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView.inflate(context, R.layout.item_shipment_fee_spinner, null);
        TextView itemShipmentFee = view.findViewById(R.id.itemShipmentFee);
        TextView itemCusFee = view.findViewById(R.id.itemCusFee);

        itemShipmentFee.setText(shipmentAdvancePaymentList.get(position).atmShipmentID);
        itemCusFee.setText(shipmentAdvancePaymentList.get(position).getCustomerCodeSwap());

        return view;
    }

}
