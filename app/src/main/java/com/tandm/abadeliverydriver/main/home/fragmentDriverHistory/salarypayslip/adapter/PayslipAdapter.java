package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemPayslipBinding;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.model.ItemPayslip;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class PayslipAdapter extends DataBoundListAdapter<ItemPayslip, ItemPayslipBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<ItemPayslip> onClick;

    public PayslipAdapter(RecyclerViewItemClick<ItemPayslip> onClick) {
        this.onClick = onClick;
    }

    @Override
    protected ItemPayslipBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_payslip, parent, false);
    }

    @Override
    protected void bind(ItemPayslipBinding binding, ItemPayslip item, int position) {
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
    protected boolean areItemsTheSame(ItemPayslip oldItem, ItemPayslip newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(ItemPayslip oldItem, ItemPayslip newItem) {
        return false;
    }
}
