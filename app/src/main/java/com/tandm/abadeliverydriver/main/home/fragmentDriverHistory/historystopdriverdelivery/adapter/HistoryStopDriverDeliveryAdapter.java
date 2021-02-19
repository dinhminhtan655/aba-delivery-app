package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemStopHisDriverBinding;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HistoryStopDriverDelivery;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import java.util.List;

import butterknife.ButterKnife;

public class HistoryStopDriverDeliveryAdapter extends DataBoundListAdapter<HistoryStopDriverDelivery, ItemStopHisDriverBinding> {

    private Context context;
    private View root;

    private RecyclerViewItemClick<HistoryStopDriverDelivery> onClick;

    public HistoryStopDriverDeliveryAdapter(RecyclerViewItemClick<HistoryStopDriverDelivery> onClick) {
        this.onClick = onClick;
    }

    @Nullable
    @Override
    public List<HistoryStopDriverDelivery> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemStopHisDriverBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_stop_his_driver, parent, false);
    }

    @Override
    protected void bind(ItemStopHisDriverBinding binding, HistoryStopDriverDelivery item, int position) {
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
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected boolean areItemsTheSame(HistoryStopDriverDelivery oldItem, HistoryStopDriverDelivery newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(HistoryStopDriverDelivery oldItem, HistoryStopDriverDelivery newItem) {
        return false;
    }
}
