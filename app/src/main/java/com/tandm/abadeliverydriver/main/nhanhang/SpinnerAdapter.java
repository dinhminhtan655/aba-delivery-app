package com.tandm.abadeliverydriver.main.nhanhang;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.nhanhang.model.Problem2sChild;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

    List<Problem2sChild> problem2sChildList;
    Context context;

    public SpinnerAdapter(List<Problem2sChild> problem2sChildList, Context context) {
        this.problem2sChildList = problem2sChildList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return problem2sChildList.size();
    }

    @Override
    public Object getItem(int i) {
        return problem2sChildList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = View.inflate(context, R.layout.item_spinner, null);
        TextView tvSp = view1.findViewById(R.id.item_sp);
        tvSp.setText(i+1 + ". " + problem2sChildList.get(i).event_name);
        return tvSp;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        view = View.inflate(context, R.layout.dropdown_spinner, null);
        TextView textView = view.findViewById(R.id.dropdown);
        textView.setText(position + 1 + ". " + problem2sChildList.get(position).event_name);

        return view;
    }
}
