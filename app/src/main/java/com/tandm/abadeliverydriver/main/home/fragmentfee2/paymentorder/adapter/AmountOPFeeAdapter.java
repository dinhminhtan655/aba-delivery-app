package com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemOrderPaymentBinding;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.model.OrderPayment;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundViewHolder;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class AmountOPFeeAdapter extends DataBoundListAdapter<OrderPayment, ItemOrderPaymentBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<OrderPayment> recyclerViewItemClick;

    public AmountOPFeeAdapter(RecyclerViewItemClick<OrderPayment>  recyclerViewItemClick) {
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    @Override
    protected ItemOrderPaymentBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_order_payment, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<ItemOrderPaymentBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        if(getItems().get(position).amountAdjustment > 0){
            holder.binding.tvTitleAmountOP2.setVisibility(View.VISIBLE);
            holder.binding.tvTitleAmountOP1.setVisibility(View.GONE);
            holder.binding.tvTitleAmountOPRemaining2.setVisibility(View.VISIBLE);
            holder.binding.tvTitleAmountOPRemaining.setVisibility(View.GONE);
            holder.binding.tvAmountOP2.setVisibility(View.VISIBLE);
            holder.binding.tvAmountOP1.setVisibility(View.GONE);
            holder.binding.tvAmountOPRemaining2.setVisibility(View.VISIBLE);
            holder.binding.tvAmountOPRemaining.setVisibility(View.GONE);
        }else {
            holder.binding.tvTitleAmountOP2.setVisibility(View.GONE);
            holder.binding.tvTitleAmountOP1.setVisibility(View.VISIBLE);
            holder.binding.tvTitleAmountOPRemaining2.setVisibility(View.GONE);
            holder.binding.tvTitleAmountOPRemaining.setVisibility(View.VISIBLE);
            holder.binding.tvAmountOP2.setVisibility(View.GONE);
            holder.binding.tvAmountOP1.setVisibility(View.VISIBLE);
            holder.binding.tvAmountOPRemaining2.setVisibility(View.GONE);
            holder.binding.tvAmountOPRemaining.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void bind(ItemOrderPaymentBinding binding, OrderPayment item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,0);
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(OrderPayment oldItem, OrderPayment newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(OrderPayment oldItem, OrderPayment newItem) {
        return false;
    }
}
