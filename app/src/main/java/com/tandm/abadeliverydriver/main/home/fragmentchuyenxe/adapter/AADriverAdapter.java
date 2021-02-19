package com.tandm.abadeliverydriver.main.home.fragmentchuyenxe.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemAaBinding;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.Shipment;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundViewHolder;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewTripListener;

import java.util.List;

import butterknife.ButterKnife;

public class AADriverAdapter extends DataBoundListAdapter<Shipment, ItemAaBinding> {

    private Context context;
    private View root;
    RecyclerViewTripListener onClick;
    private List<Shipment> shipments;
//    private boolean AOA;

//    public AADriverAdapter(boolean AOA,RecyclerViewTripListener onClick) {
//        this.onClick = onClick;
//        this.AOA = AOA;
//    }

    public AADriverAdapter(RecyclerViewTripListener onClick, List<Shipment> shipments) {
        this.onClick = onClick;
        this.shipments = shipments;
    }

    @Nullable
    @Override
    public List<Shipment> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemAaBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_aa, parent, false);
    }


    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<ItemAaBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        if (shipments.get(position).status.equals("ACCEPTED")){
            holder.binding.itemBtnDenied.setVisibility(View.GONE);
            holder.binding.itemBtnAccepted.setVisibility(View.GONE);
        }else if(shipments.get(position).status.equals("ASSIGNED")){
            holder.binding.itemBtnStart.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void bind(ItemAaBinding binding, Shipment item, int position) {
        binding.setItem(item);
        root = binding.getRoot();


        binding.itemBtnDenied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position, item.atM_SHIPMENT_ID, item.trucktype, item.starT_TIME, item.routeno, item.driveR_GID, 0);
            }
        });

        binding.itemBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onClick.onClick(position, item.atM_SHIPMENT_ID, item.trucktype, item.starT_TIME, item.routeno, item.driveR_GID, 1);
            }
        });

        binding.itemBtnAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position, item.atM_SHIPMENT_ID, item.trucktype, item.starT_TIME, item.routeno, item.driveR_GID, 2);
            }
        });


        binding.itemCheckStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position, item.atM_SHIPMENT_ID, item.trucktype, item.starT_TIME, item.routeno, item.driveR_GID, 3);
            }
        });



    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected boolean areItemsTheSame(Shipment oldItem, Shipment newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(Shipment oldItem, Shipment newItem) {
        return false;
    }
}
