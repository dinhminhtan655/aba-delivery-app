package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.ShipmentOrderPayment;

import java.util.List;

public class ShipmentOPAdapter extends BaseAdapter implements SpinnerAdapter {

    List<ShipmentOrderPayment> shipmentOrderPayments;
    Context context;

    public ShipmentOPAdapter(List<ShipmentOrderPayment> shipmentOrderPayments, Context context) {
        this.shipmentOrderPayments = shipmentOrderPayments;
        this.context = context;
    }

    @Override
    public int getCount() {
        return shipmentOrderPayments.size();
    }

    @Override
    public Object getItem(int position) {
        return shipmentOrderPayments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView.inflate(context, R.layout.item_spinner_order_payment_shipment, null);
        TextView tvShipmentID = view.findViewById(R.id.tvShipmentID);
        TextView tvCustomerCode = view.findViewById(R.id.tvCustomerCode);
        TextView tvStartTime = view.findViewById(R.id.tvStartTime);
        TextView tvAmount = view.findViewById(R.id.tvAmount);
        TextView tvAmountRemaining = view.findViewById(R.id.tvAmountRemaining);
        TextView tvCustomerName = view.findViewById(R.id.tvCustomerName);

        tvShipmentID.setText(shipmentOrderPayments.get(position).atmShipmentID);
        tvCustomerCode.setText(shipmentOrderPayments.get(position).getCustomerCodeSwap(shipmentOrderPayments.get(position).customerCode,shipmentOrderPayments.get(position).packagedItem));
        tvCustomerName.setText(shipmentOrderPayments.get(position).customerName);
        tvStartTime.setText(shipmentOrderPayments.get(position).getDateStartTime(shipmentOrderPayments.get(position).startTime));
        tvAmount.setText("Tiền Tạm ứng: " + shipmentOrderPayments.get(position).formartVND(shipmentOrderPayments.get(position).amount));
        tvAmountRemaining.setText("Chi Phí: " + shipmentOrderPayments.get(position).formartVND(shipmentOrderPayments.get(position).amountTotal));
        return view;
    }
}
