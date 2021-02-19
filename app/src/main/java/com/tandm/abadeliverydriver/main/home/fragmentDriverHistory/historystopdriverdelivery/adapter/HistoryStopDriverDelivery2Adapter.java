package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemStopHisDriverVcmBinding;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.model.HistoryStopDriverDelivery2;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.Customer;

import java.util.List;

import butterknife.ButterKnife;

public class HistoryStopDriverDelivery2Adapter extends DataBoundListAdapter<HistoryStopDriverDelivery2, ItemStopHisDriverVcmBinding> {

    private Context context;
    private View root;

    private RecyclerViewItemClick<HistoryStopDriverDelivery2> onClick;

    public HistoryStopDriverDelivery2Adapter(RecyclerViewItemClick<HistoryStopDriverDelivery2> onClick) {
        this.onClick = onClick;
    }

    @Nullable
    @Override
    public List<HistoryStopDriverDelivery2> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemStopHisDriverVcmBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_stop_his_driver_vcm, parent, false);
    }

    @Override
    protected void bind(ItemStopHisDriverVcmBinding binding, HistoryStopDriverDelivery2 item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        if (item.khachHang.equals(Customer.MASAN) || item.khachHang.equals("BIGC") || item.khachHang.equals(Customer.TOKYODELI) || item.khachHang.equals(Customer.CP) || item.khachHang.equals(Customer.VCMFRESH) || item.khachHang.equals(Customer.NEWZEALAND) || item.khachHang.equals(Customer.THREEF) || item.khachHang.equals(Customer.XX6020)){
            if (item.khachHang.equals(Customer.CP) || item.khachHang.equals(Customer.VCMFRESH) || item.khachHang.equals(Customer.NEWZEALAND) || item.khachHang.equals(Customer.THREEF) || item.khachHang.equals(Customer.XX6020)){
                binding.lnHistory2.setVisibility(View.GONE);
            }
            binding.lnHistory.setVisibility(View.VISIBLE);
        }else {
            binding.lnHistory.setVisibility(View.GONE);
        }

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(item,position,0);
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(HistoryStopDriverDelivery2 oldItem, HistoryStopDriverDelivery2 newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(HistoryStopDriverDelivery2 oldItem, HistoryStopDriverDelivery2 newItem) {
        return false;
    }
}
