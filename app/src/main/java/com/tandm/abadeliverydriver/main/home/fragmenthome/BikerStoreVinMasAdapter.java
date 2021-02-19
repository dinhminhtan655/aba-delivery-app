package com.tandm.abadeliverydriver.main.home.fragmenthome;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.util.List;

public class BikerStoreVinMasAdapter extends RecyclerView.Adapter<BikerStoreVinMasAdapter.BikerStoreVinMasViewHolder> {

    List<StoreATM> storeATMS;
    Context context;

    int row_index = -1;
    RecyclerViewItemClick<StoreATM> itemClick;

    public BikerStoreVinMasAdapter(List<StoreATM> storeATMS, Context context, RecyclerViewItemClick<StoreATM> itemClick) {
        this.storeATMS = storeATMS;
        this.context = context;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public BikerStoreVinMasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.item_store_vinmas_biker, parent, false);
        return new BikerStoreVinMasViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BikerStoreVinMasViewHolder holder, int position) {
        holder.tvStoreCodeVinMas.setText(storeATMS.get(position).locationGID);
        holder.tvPackageItemVinMas.setText(storeATMS.get(position).packagedItem.equals("ABA.CHILLED_FOOD_0-5") ? "Vin" : "Masan");
        holder.tvVehicleVinMas.setText(storeATMS.get(position).vehicle);

        holder.setItemClick(new RecyclerViewItemClick<StoreATM>() {
            @Override
            public void onClick(StoreATM item, int position, int number) {
                row_index = position;
                Utilities.currentItem = storeATMS.get(position);
                itemClick.onClick(item, position, 0);
                notifyDataSetChanged();
            }

            @Override
            public void onLongClick(StoreATM item, int position, int number) {

            }
        });


        if (row_index == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F8F8FA"));
            holder.tvStoreCodeVinMas.setTextColor(Color.parseColor("#c5c5c7"));
            holder.tvPackageItemVinMas.setTextColor(Color.parseColor("#c5c5c7"));
            holder.tvVehicleVinMas.setTextColor(Color.parseColor("#c5c5c7"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.tvStoreCodeVinMas.setTextColor(Color.parseColor("#000000"));
            holder.tvPackageItemVinMas.setTextColor(Color.parseColor("#000000"));
            holder.tvVehicleVinMas.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() {
        return storeATMS.size();
    }

    public class BikerStoreVinMasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvStoreCodeVinMas, tvPackageItemVinMas, tvVehicleVinMas;

        RecyclerViewItemClick<StoreATM> itemClick;

        public void setItemClick(RecyclerViewItemClick<StoreATM> itemClick) {
            this.itemClick = itemClick;
        }

        public BikerStoreVinMasViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStoreCodeVinMas = itemView.findViewById(R.id.tvStoreCodeVinMas);
            tvPackageItemVinMas = itemView.findViewById(R.id.tvPackageItemVinMas);
            tvVehicleVinMas = itemView.findViewById(R.id.tvVehicleVinMas);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            itemClick.onClick(storeATMS.get(getAdapterPosition()), getAdapterPosition(), 0);
        }
    }
}
