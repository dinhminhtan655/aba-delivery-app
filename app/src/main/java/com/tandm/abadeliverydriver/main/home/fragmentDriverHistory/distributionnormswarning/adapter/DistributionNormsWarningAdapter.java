package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.distributionnormswarning.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemDisNormsWarningBinding;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.distributionnormswarning.model.DistributionNormsWarning;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;

import java.util.List;

import butterknife.ButterKnife;

public class DistributionNormsWarningAdapter extends DataBoundListAdapter<DistributionNormsWarning, ItemDisNormsWarningBinding> {


    private Context context;
    private View root;


    @Nullable
    @Override
    public List<DistributionNormsWarning> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemDisNormsWarningBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_dis_norms_warning, parent, false);
    }

    @Override
    protected void bind(ItemDisNormsWarningBinding binding, DistributionNormsWarning item, int position) {
        binding.setItem(item);
        root = binding.getRoot();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected boolean areItemsTheSame(DistributionNormsWarning oldItem, DistributionNormsWarning newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(DistributionNormsWarning oldItem, DistributionNormsWarning newItem) {
        return false;
    }
}
