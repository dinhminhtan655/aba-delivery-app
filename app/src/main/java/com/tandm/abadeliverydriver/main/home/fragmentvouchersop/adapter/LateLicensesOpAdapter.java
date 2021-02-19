package com.tandm.abadeliverydriver.main.home.fragmentvouchersop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemLateLicensesOpBinding;
import com.tandm.abadeliverydriver.main.home.fragmentvouchers.model.LateLicenses;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class LateLicensesOpAdapter extends DataBoundListAdapter<LateLicenses, ItemLateLicensesOpBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<LateLicenses> recyclerViewItemClick;

    public LateLicensesOpAdapter(RecyclerViewItemClick<LateLicenses> recyclerViewItemClick) {
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    @Override
    protected ItemLateLicensesOpBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_late_licenses_op, parent, false);
    }

    @Override
    protected void bind(ItemLateLicensesOpBinding binding, LateLicenses item, int position) {
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
