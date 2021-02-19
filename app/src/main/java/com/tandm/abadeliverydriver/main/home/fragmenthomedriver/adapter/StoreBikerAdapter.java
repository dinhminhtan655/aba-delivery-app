package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemTripBikerForDriverBinding;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.ShipmentBikerForDriver;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class StoreBikerAdapter extends DataBoundListAdapter<ShipmentBikerForDriver, ItemTripBikerForDriverBinding> {

    private Context context;
    private View root;
    private static List<ShipmentBikerForDriver> stringList;
    private static ArrayList<ShipmentBikerForDriver> arrayList;

    public StoreBikerAdapter( List<ShipmentBikerForDriver> stringList) {
        this.stringList = stringList;
        this.arrayList = new ArrayList<ShipmentBikerForDriver>();
        this.arrayList.addAll(stringList);
    }

    @Nullable
    @Override
    public List<ShipmentBikerForDriver> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemTripBikerForDriverBinding createBinding(ViewGroup parent, int viewType) {
        context = Utilities.unwrap(parent.getContext());
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_trip_biker_for_driver, parent, false);
    }

    @Override
    protected void bind(ItemTripBikerForDriverBinding binding, ShipmentBikerForDriver item, int position) {
        binding.setItem(item);
        root = binding.getRoot();
    }

    @Override
    protected boolean areItemsTheSame(ShipmentBikerForDriver oldItem, ShipmentBikerForDriver newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(ShipmentBikerForDriver oldItem, ShipmentBikerForDriver newItem) {
        return false;
    }
}
