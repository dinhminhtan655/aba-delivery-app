package com.tandm.abadeliverydriver.main.home.fragmentgiaobu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemGiaoBuDetailBinding;
import com.tandm.abadeliverydriver.main.home.fragmentgiaobu.model.GiaoBuDetail;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewUpdateGiaoBuListener;

import java.util.List;

import butterknife.ButterKnife;

public class GiaoBuDetailAdapter extends DataBoundListAdapter<GiaoBuDetail, ItemGiaoBuDetailBinding> {

    private Context context;
    private View root;
    private RecyclerViewUpdateGiaoBuListener onClick;

    public GiaoBuDetailAdapter(RecyclerViewUpdateGiaoBuListener onClick) {
        this.onClick = onClick;
    }

    @Nullable
    @Override
    public List<GiaoBuDetail> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemGiaoBuDetailBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_giao_bu_detail, parent, false);
    }

    @Override
    protected void bind(ItemGiaoBuDetailBinding binding, GiaoBuDetail item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position,item.rowId, item.item_Code, item.item_Name,item.getSoBich(),item.getSoActual(),item.getSoGiaoBu(), 0);
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(GiaoBuDetail oldItem, GiaoBuDetail newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(GiaoBuDetail oldItem, GiaoBuDetail newItem) {
        return false;
    }
}
