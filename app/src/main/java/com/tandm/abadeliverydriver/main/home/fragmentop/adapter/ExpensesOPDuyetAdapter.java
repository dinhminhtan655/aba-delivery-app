package com.tandm.abadeliverydriver.main.home.fragmentop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemExpensesOpDuyetBinding;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ExpensesAmount;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundViewHolder;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class ExpensesOPDuyetAdapter extends DataBoundListAdapter<ExpensesAmount, ItemExpensesOpDuyetBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<ExpensesAmount> recyclerViewItemClick;

    public ExpensesOPDuyetAdapter(RecyclerViewItemClick<ExpensesAmount> recyclerViewItemClick) {
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    @Override
    protected ItemExpensesOpDuyetBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_expenses_op_duyet, parent, false);
    }


    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<ItemExpensesOpDuyetBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        if(LoginPrefer.getObject(context).Position.equals("OP") || LoginPrefer.getObject(context).Position.equals("KT2")){
            if (getItems().get(holder.getAdapterPosition()).manager.equals("DUYỆT")){
                holder.binding.btnDongYDuyet.setVisibility(View.GONE);
                holder.binding.btnTuChoiDuyet.setVisibility(View.GONE);
            }else if (getItems().get(holder.getAdapterPosition()).manager.equals("KHÔNG DUYỆT")){
                holder.binding.btnDongYDuyet.setVisibility(View.GONE);
                holder.binding.btnTuChoiDuyet.setVisibility(View.GONE);
            }
        }else if (LoginPrefer.getObject(context).Position.equals("OP2") || LoginPrefer.getObject(context).Position.equals("KT")){
            if (getItems().get(holder.getAdapterPosition()).opApproved.equals("DUYỆT")){
                holder.binding.btnDongYDuyet.setVisibility(View.GONE);
                holder.binding.btnTuChoiDuyet.setVisibility(View.GONE);
            }else if (getItems().get(holder.getAdapterPosition()).opApproved.equals("KHÔNG DUYỆT")){
                holder.binding.btnDongYDuyet.setVisibility(View.GONE);
                holder.binding.btnTuChoiDuyet.setVisibility(View.GONE);
            }
        }
//        else if (LoginPrefer.getObject(context).Position.equals("KT")){
//            if (getItems().get(holder.getAdapterPosition()).technicalApproved.equals("DUYỆT")){
//                holder.binding.btnDongYDuyet.setVisibility(View.GONE);
//                holder.binding.btnTuChoiDuyet.setVisibility(View.GONE);
//            }else if (getItems().get(holder.getAdapterPosition()).technicalApproved.equals("KHÔNG DUYỆT")){
//                holder.binding.btnDongYDuyet.setVisibility(View.GONE);
//                holder.binding.btnTuChoiDuyet.setVisibility(View.GONE);
//            }
//        }else if (LoginPrefer.getObject(context).Position.equals("KT2")){
//            if (getItems().get(holder.getAdapterPosition()).techManagerApproved.equals("DUYỆT")){
//                holder.binding.btnDongYDuyet.setVisibility(View.GONE);
//                holder.binding.btnTuChoiDuyet.setVisibility(View.GONE);
//            }else if (getItems().get(holder.getAdapterPosition()).techManagerApproved.equals("KHÔNG DUYỆT")){
//                holder.binding.btnDongYDuyet.setVisibility(View.GONE);
//                holder.binding.btnTuChoiDuyet.setVisibility(View.GONE);
//            }
//        }

    }

    @Override
    protected void bind(ItemExpensesOpDuyetBinding binding, ExpensesAmount item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,0);
            }
        });


        binding.btnDongYDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,1);
            }
        });

        binding.btnTuChoiDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item,position,2);
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(ExpensesAmount oldItem, ExpensesAmount newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(ExpensesAmount oldItem, ExpensesAmount newItem) {
        return false;
    }
}
