package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.DateItem;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.GeneralItem;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ListItem;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AmountFeeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    List<ListItem> consolidatedList = new ArrayList<>();
    private RecyclerViewItemClick<GeneralItem> recyclerViewItemClick;

    public AmountFeeAdapter(Context mContext, List<ListItem> consolidatedList, RecyclerViewItemClick<GeneralItem> recyclerViewItemClick) {
        this.mContext = mContext;
        this.consolidatedList = consolidatedList;
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ListItem.TYPE_GENERAL:
                View v1 = inflater.inflate(R.layout.item_body_expenses, parent, false);
                viewHolder = new GeneralViewHolder(v1);
                break;


            case ListItem.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.item_date_expenses, parent, false);
                viewHolder = new DateViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ListItem.TYPE_GENERAL:
                GeneralItem generalItem = (GeneralItem) consolidatedList.get(position);
                GeneralViewHolder generalViewHolder = (GeneralViewHolder) holder;
                generalViewHolder.tvTitleFee.setText(generalItem.getExpensesAmount().advancePaymentType);
                generalViewHolder.tvTitleShipmentID.setText(generalItem.getExpensesAmount().atmShipmentID);
                generalViewHolder.tvAmountExpenses.setText(generalItem.getExpensesAmount().getFormatCurrency());
                generalViewHolder.tvStatusManagerExpenses.setText(generalItem.getExpensesAmount().manager);
                generalViewHolder.tvStatusFinExpenses.setText(generalItem.getExpensesAmount().finApproved);
                try {
                    generalViewHolder.tvTimeCreateExpenses.setText(generalItem.getExpensesAmount().getInvoiceDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                generalViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewItemClick.onClick(generalItem,position,0);
                    }
                });
                break;

            case ListItem.TYPE_DATE:
                DateItem dateItem = (DateItem) consolidatedList.get(position);
                DateViewHolder dateViewHolder = (DateViewHolder) holder;
                dateViewHolder.tvDateExpenses.setText(dateItem.getDate());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return consolidatedList != null ? consolidatedList.size() : 0;
    }


    class DateViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvDateExpenses;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvDateExpenses = itemView.findViewById(R.id.tvDateExpenses);
        }
    }


    class GeneralViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvTitleFee, tvTitleShipmentID, tvAmountExpenses, tvStatusManagerExpenses, tvStatusFinExpenses, tvTimeCreateExpenses;

        public GeneralViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitleFee = itemView.findViewById(R.id.tvTitleFee);
            this.tvTitleShipmentID = itemView.findViewById(R.id.tvTitleShipmentID);
            this.tvAmountExpenses = itemView.findViewById(R.id.tvAmountExpenses);
            this.tvStatusManagerExpenses = itemView.findViewById(R.id.tvStatusManagerExpenses);
            this.tvStatusFinExpenses = itemView.findViewById(R.id.tvStatusFinExpenses);
            this.tvTimeCreateExpenses = itemView.findViewById(R.id.tvTimeCreateExpenses);
        }

    }


    @Override
    public int getItemViewType(int position) {
        return consolidatedList.get(position).getType();
    }


}
