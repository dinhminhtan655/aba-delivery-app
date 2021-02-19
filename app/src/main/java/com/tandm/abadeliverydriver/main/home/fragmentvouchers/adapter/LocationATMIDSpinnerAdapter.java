package com.tandm.abadeliverydriver.main.home.fragmentvouchers.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LocationATMID;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.util.List;

public class LocationATMIDSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    List<LocationATMID> locationATMIDList;
    Context context;


    public LocationATMIDSpinnerAdapter(List<LocationATMID> locationATMIDList, Context context) {
        this.locationATMIDList = locationATMIDList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return locationATMIDList.size();
    }

    @Override
    public Object getItem(int i) {
        return locationATMIDList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = View.inflate(context, R.layout.item_late_licenses_spinner, null);
        TextView itemAtmShipmentID = view1.findViewById(R.id.itemAtmShipmentID);
        TextView itemCustomer = view1.findViewById(R.id.itemCustomer);
        TextView itemStartTime = view1.findViewById(R.id.itemStartTime);
        itemAtmShipmentID.setText(locationATMIDList.get(i).atM_SHIPMENT_ID);
        itemCustomer.setText(locationATMIDList.get(i).getCustomerCodeSwap(locationATMIDList.get(i).packagedItem));
        itemStartTime.setText(Utilities.formatDate_ddMMyyyy(locationATMIDList.get(i).startTime));
        return view1;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.dropdown_spinner,null);
        TextView textView = view.findViewById(R.id.dropdown);
        TextView dropdownCustomer = view.findViewById(R.id.dropdownCustomer);
        TextView dropdownStartTime = view.findViewById(R.id.dropdownStartTime);
        textView.setText(locationATMIDList.get(position).atM_SHIPMENT_ID);
        dropdownCustomer.setText(locationATMIDList.get(position).getCustomerCodeSwap(locationATMIDList.get(position).customerCode));
        dropdownStartTime.setText(Utilities.formatDate_ddMMyyyy(locationATMIDList.get(position).startTime));
        return view;
    }
}
