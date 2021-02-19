package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemDetailOpAmountBinding;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.DetailOrderPayment;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class DetailOPFeeAdapter extends DataBoundListAdapter<DetailOrderPayment, ItemDetailOpAmountBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<DetailOrderPayment> recyclerViewItemClick;


    public DetailOPFeeAdapter(RecyclerViewItemClick<DetailOrderPayment> recyclerViewItemClick) {
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    @Override
    protected ItemDetailOpAmountBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_detail_op_amount, parent, false);
    }


    @Override
    protected void bind(ItemDetailOpAmountBinding binding, DetailOrderPayment item, int position) {
        binding.setItem(item);
        root = binding.getRoot();
//        if (item.advancePaymentType.equals("PHÍ VÁ VỎ, SỬA XE")){
//            binding.tvManagerStatus.setVisibility(View.GONE);
//            binding.tvKTMaStatus.setVisibility(View.VISIBLE);
//        }else {
//            binding.tvManagerStatus.setVisibility(View.VISIBLE);
//            binding.tvKTMaStatus.setVisibility(View.GONE);
//        }
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,0);
            }

        });


        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,1);
            }
        });

        binding.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,2);
            }
        });


        binding.btnDelPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,3);
            }
        });


        binding.btnAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,4);
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(DetailOrderPayment oldItem, DetailOrderPayment newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(DetailOrderPayment oldItem, DetailOrderPayment newItem) {
        return false;
    }
}
