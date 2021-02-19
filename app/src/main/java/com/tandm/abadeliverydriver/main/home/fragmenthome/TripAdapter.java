package com.tandm.abadeliverydriver.main.home.fragmenthome;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemTripMainBinding;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundViewHolder;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemListener2;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class TripAdapter extends DataBoundListAdapter<Store2, ItemTripMainBinding> {

    private Context context;
    private View root;
    private List<Store2> list = null;
    private ArrayList<Store2> arrayList;
    private RecyclerViewItemListener2 onClick;



    public TripAdapter(RecyclerViewItemListener2 onClick, List<Store2> list) {
        this.onClick = onClick;
        this.list = list;
        this.arrayList = new ArrayList<Store2>();
        this.arrayList.addAll(list);
    }


    @Nullable
    @Override
    public List<Store2> getItems() {

        return super.getItems();
    }

    @Override
    protected ItemTripMainBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_trip_main, parent, false);
    }


    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<ItemTripMainBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        if (getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals("VCMFRESH")
                || getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals("BHX")
                || getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals("VCM")) {
            holder.binding.btnGiao.setVisibility(View.GONE);
            holder.binding.edtSLThungBiker.setVisibility(View.GONE);
            holder.binding.lnSLThung.setVisibility(View.GONE);
        }else {
            holder.binding.btnGiao.setVisibility(View.VISIBLE);
            holder.binding.edtSLThungBiker.setVisibility(View.VISIBLE);
            holder.binding.lnSLThung.setVisibility(View.GONE);
            holder.binding.itemBtnNhanHang.setVisibility(View.GONE);
        }
    }

    @Override
    protected void bind(ItemTripMainBinding binding, Store2 item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        binding.itemBtnNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, item.store_Code,item.store_Name,item.address,item.khachHang,item.atM_SHIPMENT_ID,item.orderreleasE_ID,item.packaged_Item_XID, item.totalCarton,item.totalWeight,1);
            }
        });

        binding.edtSLThungBiker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setTotalCarton(Integer.parseInt(s.toString()));
            }
        });



        binding.itemBtnSuCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, item.store_Code,item.store_Name,item.address,item.khachHang,item.atM_SHIPMENT_ID,item.orderreleasE_ID,item.packaged_Item_XID, item.totalCarton,item.totalWeight,2);
            }
        });


        binding.btnGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position, item.store_Code,item.store_Name,item.address,item.khachHang,item.atM_SHIPMENT_ID,item.orderreleasE_ID,item.packaged_Item_XID, item.getTotalCarton(),item.totalWeight,3);

            }
        });


    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected boolean areItemsTheSame(Store2 oldItem, Store2 newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(Store2 oldItem, Store2 newItem) {
        return false;
    }


    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0){
            list.addAll(arrayList);
        }else {
            for (Store2  st: arrayList){
                if (st.store_Code.toLowerCase(Locale.getDefault()).contains(charText)){
                    list.add(st);
                }
            }
        }

        notifyDataSetChanged();

    }
}
