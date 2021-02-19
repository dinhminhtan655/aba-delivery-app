package com.tandm.abadeliverydriver.main.home.fragmentHistory;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemHistoryDeliveryBinding;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;

import java.util.List;

import butterknife.ButterKnife;

public class HistoryDeliveryAdapter extends DataBoundListAdapter<HistoryDelivery, ItemHistoryDeliveryBinding> {

    private Context context;
    private View root;

    @Nullable
    @Override
    public List<HistoryDelivery> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemHistoryDeliveryBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_history_delivery, parent, false);
    }

    @Override
    protected void bind(ItemHistoryDeliveryBinding binding, HistoryDelivery item, int position) {
        binding.setItem(item);
        root = binding.getRoot();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected boolean areItemsTheSame(HistoryDelivery oldItem, HistoryDelivery newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(HistoryDelivery oldItem, HistoryDelivery newItem) {
        return false;
    }
}
