package com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemPayslipBikerBinding;
import com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.model.PayslipBiker;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class PayslipBikerAdapter extends DataBoundListAdapter<PayslipBiker, ItemPayslipBikerBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<PayslipBiker> onClick;

    public PayslipBikerAdapter(RecyclerViewItemClick<PayslipBiker> onClick) {
        this.onClick = onClick;
    }

    @Override
    protected ItemPayslipBikerBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_payslip_biker, parent, false);
    }

    @Override
    protected void bind(ItemPayslipBikerBinding binding, PayslipBiker item, int position) {
        binding.setItem(item);
        root = binding.getRoot();


        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(item,position,0);
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(PayslipBiker oldItem, PayslipBiker newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(PayslipBiker oldItem, PayslipBiker newItem) {
        return false;
    }
}
