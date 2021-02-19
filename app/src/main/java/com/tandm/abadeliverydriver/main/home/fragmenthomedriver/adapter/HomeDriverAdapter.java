package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemTripMainDriverBinding;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.StoreDriver;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundViewHolder;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemListener3;
import com.tandm.abadeliverydriver.main.utilities.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class HomeDriverAdapter extends DataBoundListAdapter<StoreDriver, ItemTripMainDriverBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemListener3<StoreDriver> onClick;
    private int a;
    private static List<StoreDriver> stringList;
    private static ArrayList<StoreDriver> arrayList;

    public HomeDriverAdapter(RecyclerViewItemListener3<StoreDriver> onClick, List<StoreDriver> stringList) {
        this.onClick = onClick;
        this.stringList = stringList;
        this.arrayList = new ArrayList<StoreDriver>();
        this.arrayList.addAll(stringList);
    }

    @Nullable
    @Override
    public List<StoreDriver> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemTripMainDriverBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_trip_main_driver, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<ItemTripMainDriverBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.binding.itemAddressDriver.setPaintFlags(holder.binding.itemAddressDriver.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.VCMFRESH)
                || getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.BHX)
                || getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.CP)
                || getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.NEWZEALAND)
                || getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.THREEF)
                || getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.XX6020)
        ) {
            holder.binding.itemBtnNhanHangDriver.setText("Giao Cửa Hàng");
            holder.binding.itemBtnNhanHangDriver.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.binding.itemBtnGiaoHangDriver2.setVisibility(View.GONE);
            holder.binding.itemTotalCartonDriver.setVisibility(View.GONE);
            holder.binding.itemTvTotalCartonDriver.setVisibility(View.GONE);
            holder.binding.itemBtnSuCoDriver.setText("Sự Cố");
            if (getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.VCMFRESH) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.BHX) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABAMEAT_0_5)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.BHX) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.BHX) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.CP) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABAMEAT_0_5)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.CP) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.NEWZEALAND) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABACHILLED_FOOD_0_5)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.NEWZEALAND) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.THREEF) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4)
                    || getItems().get(holder.getAdapterPosition()).totalCarton > 0 && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.XX6020) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABAFRESH_MEAT_0_4)
            ) {
                holder.binding.itemBtnNhanHangDriver.setText("Giao Cửa Hàng");
                holder.binding.itemBtnGiaoHubDriver.setVisibility(View.VISIBLE);
            } else {
                holder.binding.itemBtnNhanHangDriver.setVisibility(View.VISIBLE);
            }
        } else if (!getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.VCMFRESH)
                || !getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.BHX)
                || !getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.VCM)
                || !getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.CP)
                || !getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.NEWZEALAND)
                || !getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.THREEF)
                || !getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.XX6020)
        ) {
            holder.binding.itemBtnNhanHangDriver.setText("Giao Cửa Hàng");
            holder.binding.itemBtnNhanHangDriver.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.binding.itemBtnGiaoHangDriver2.setVisibility(View.GONE);
            holder.binding.itemTotalCartonDriver.setVisibility(View.GONE);
            holder.binding.itemTvTotalCartonDriver.setVisibility(View.GONE);
            holder.binding.itemBtnGiaoHubDriver.setVisibility(View.VISIBLE);
            holder.binding.itemBtnSuCoDriver.setText("Sự Cố");
            if (getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.BHX) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABAICE_0)) {
                holder.binding.itemBtnNhanHangDriver.setText("Giao Đá");
                holder.binding.itemBtnNhanHangDriver.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                holder.binding.itemBtnGiaoHubDriver.setVisibility(View.GONE);
                holder.binding.itemBtnGiaoHangDriver2.setVisibility(View.GONE);
            }
//            else if(getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.BHX) && getItems().get(holder.getAdapterPosition()).packaged_Item_XID.equals(Customer.ABAFROZEN_FOOD__18)){
//                holder.binding.itemBtnGiaoHubDriver.setText("Giao hàng đông");
//                holder.binding.itemBtnNhanHangDriver.setVisibility(View.GONE);
//                holder.binding.itemBtnGiaoHangDriver2.setVisibility(View.GONE);
//            }
        } else if (!getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.VCMFRESH)
                || !getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.BHX)
                || !getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.VCM)
                || !getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.CP)
                || !getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.NEWZEALAND)
                || !getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.THREEF)
                || !getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.XX6020)
        ) {
            holder.binding.itemBtnNhanHangDriver.setText("Sự cố hàng");
            holder.binding.itemBtnGiaoHubDriver.setVisibility(View.GONE);
            holder.binding.itemBtnNhanHangDriver.setBackgroundColor(context.getResources().getColor(R.color.redfaded));
            holder.binding.itemBtnGiaoHangDriver2.setVisibility(View.VISIBLE);
            holder.binding.itemTotalCartonDriver.setVisibility(View.VISIBLE);
            holder.binding.itemTvTotalCartonDriver.setVisibility(View.VISIBLE);
//            if (getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.MASAN)) {
//                holder.binding.lnEdtSLThung.setVisibility(View.VISIBLE);
//            }else {
//                holder.binding.lnEdtSLThung.setVisibility(View. GONE);
//            }
        } else if (!getItems().get(holder.getAdapterPosition()).mobileHub && getItems().get(holder.getAdapterPosition()).khachHang.equals(Customer.VCM)) {

        }

        if (stringList.get(position).isbDaToi()) {
            holder.binding.toggleDriver.setChecked(true);
            holder.binding.toggleDriver.setEnabled(false);
        } else {
            holder.binding.toggleDriver.setChecked(false);
            holder.binding.toggleDriver.setEnabled(true);
        }
        if (getItems().get(holder.getAdapterPosition()).daToi || stringList.get(position).isbDaToi()) {
            holder.binding.toggleDriver.setChecked(true);
            holder.binding.toggleDriver.setEnabled(false);
        } else {
            holder.binding.toggleDriver.setChecked(false);
            holder.binding.toggleDriver.setEnabled(true);
        }


    }

    @Override
    protected void bind(ItemTripMainDriverBinding binding, StoreDriver item, int position) {
        binding.setItem(item);
        root = binding.getRoot();


        binding.itemBtnNhanHangDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, item, binding.toggleDriver, binding.itemPhoneCustomerDriver, 0);
            }
        });

        binding.itemBtnSuCoDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, item, binding.toggleDriver, binding.itemPhoneCustomerDriver, 1);
            }
        });

        binding.itemBtnGiaoHangDriver2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, item, binding.toggleDriver, binding.itemPhoneCustomerDriver, 2);
            }
        });

        binding.itemAddressDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, item, binding.toggleDriver, binding.itemPhoneCustomerDriver, 3);
            }
        });

        binding.toggleDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, item, binding.toggleDriver, binding.itemPhoneCustomerDriver, 4);
            }
        });

        binding.itemBtnGiaoHubDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position, item, binding.toggleDriver, binding.itemPhoneCustomerDriver, 5);
            }
        });

        binding.itemPhoneCustomerDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position, item, binding.toggleDriver, binding.itemPhoneCustomerDriver, 6);
            }
        });

//        binding.itemEdtSLThungMasan.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                item.setTvSLThungGiaoCHValue(s.length() == 0 ? 0 : Integer.parseInt(s.toString()));
//            }
//        });


//        binding.toggleDriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(!b){
//                    binding.toggleDriver.setChecked(true);
//                    binding.toggleDriver.setEnabled(false);
////                    onClick.onClick(position, item.storE_CODE_ATM, item.locatioN_NAME, item.addresS_LINE, item.khachHang,item.delivery_Date, item.mobileHub ,item.totalCarton,binding.toggleDriver,item.totalWeight,4);
//                    stringList.get(position).setbDaToi(true);
//                }else {
//                    binding.toggleDriver.setEnabled(false);
//                    binding.toggleDriver.setChecked(true);
////                    onClick.onClick(position, item.storE_CODE_ATM, item.locatioN_NAME, item.addresS_LINE, item.khachHang,item.delivery_Date, item.mobileHub ,item.totalCarton,binding.toggleDriver,item.totalWeight,4);
//                    stringList.get(position).setbDaToi(true);
//                }
//            }
//        });

    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected boolean areItemsTheSame(StoreDriver oldItem, StoreDriver newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(StoreDriver oldItem, StoreDriver newItem) {
        return false;
    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        stringList.clear();
        if (charText.length() == 0) {
            stringList.addAll(arrayList);
        } else {
            for (StoreDriver st : arrayList) {
                if (st.store_Code_ABA.toLowerCase(Locale.getDefault()).contains(charText)) {
                    stringList.add(st);
                }
            }
        }
        notifyDataSetChanged();
    }

}
