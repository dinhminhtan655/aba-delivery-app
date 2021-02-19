package com.tandm.abadeliverydriver.main.home.fragmentgiaobu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemGiaoBuBinding;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.model.GiaoBu;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewGiaoBuListener;

import java.util.List;

import butterknife.ButterKnife;

public class GiaoBuAdapter extends DataBoundListAdapter<GiaoBu, ItemGiaoBuBinding> {

    private Context context;
    private View root;
    private RecyclerViewGiaoBuListener onClick;


    public GiaoBuAdapter(RecyclerViewGiaoBuListener onClick) {
        this.onClick = onClick;
    }

    @Nullable
    @Override
    public List<GiaoBu> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemGiaoBuBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_giao_bu, parent, false);
    }

    @Override
    protected void bind(ItemGiaoBuBinding binding, GiaoBu item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position, item.store_Code, item.store_Name, item.khachHang, item.address,item.atmShipmentID, 0);
            }
        });


    }

    @Override
    protected boolean areItemsTheSame(GiaoBu oldItem, GiaoBu newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(GiaoBu oldItem, GiaoBu newItem) {
        return false;
    }
}
