package com.tandm.abadeliverydriver.main.home.fragmentvouchers.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemLateLicensesDriverBinding;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LateLicenses;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class LateLicensesDriverAdapter extends DataBoundListAdapter<LateLicenses, ItemLateLicensesDriverBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<LateLicenses> recyclerViewItemClick;

    public LateLicensesDriverAdapter(RecyclerViewItemClick<LateLicenses> recyclerViewItemClick) {
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    @Override
    protected ItemLateLicensesDriverBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_late_licenses_driver, parent, false);
    }

    @Override
    protected void bind(ItemLateLicensesDriverBinding binding, LateLicenses item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item, position, 0);
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(LateLicenses oldItem, LateLicenses newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(LateLicenses oldItem, LateLicenses newItem) {
        return false;
    }
}
