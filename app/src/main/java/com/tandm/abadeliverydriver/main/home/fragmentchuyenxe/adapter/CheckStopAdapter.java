package com.tandm.abadeliverydriver.main.home.fragmentchuyenxe.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemCheckStoreBinding;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreDriver;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;

import butterknife.ButterKnife;

public class CheckStopAdapter extends DataBoundListAdapter<StoreDriver, ItemCheckStoreBinding> {


    private Context context;
    private View root;

    public CheckStopAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ItemCheckStoreBinding createBinding(ViewGroup parent, int viewType) {
//        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_check_store, parent, false);
    }

    @Override
    protected void bind(ItemCheckStoreBinding binding, StoreDriver item, int position) {
        binding.setItem(item);
        root = binding.getRoot();
    }

    @Override
    protected boolean areItemsTheSame(StoreDriver oldItem, StoreDriver newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(StoreDriver oldItem, StoreDriver newItem) {
        return false;
    }
}
