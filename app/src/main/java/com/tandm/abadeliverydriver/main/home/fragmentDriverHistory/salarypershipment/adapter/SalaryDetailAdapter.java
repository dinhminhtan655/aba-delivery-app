package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemSalaryDetailsBinding;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.ItemsDetail;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;

import butterknife.ButterKnife;

public class SalaryDetailAdapter extends DataBoundListAdapter<ItemsDetail, ItemSalaryDetailsBinding> {

    private Context context;
    private View root;

    @Override
    protected ItemSalaryDetailsBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_salary_details, parent, false);
    }

    @Override
    protected void bind(ItemSalaryDetailsBinding binding, ItemsDetail item, int position) {
        binding.setItem(item);
        root = binding.getRoot();
    }

    @Override
    protected boolean areItemsTheSame(ItemsDetail oldItem, ItemsDetail newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(ItemsDetail oldItem, ItemsDetail newItem) {
        return false;
    }
}
