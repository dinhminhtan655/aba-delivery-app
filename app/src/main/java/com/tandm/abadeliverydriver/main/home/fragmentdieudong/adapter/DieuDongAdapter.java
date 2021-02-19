package com.tandm.abadeliverydriver.main.home.fragmentdieudong.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.zxing.WriterException;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemLenhDieuDongBinding;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.Shipment;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewTripListener;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.util.List;

import butterknife.ButterKnife;

public class DieuDongAdapter extends DataBoundListAdapter<Shipment, ItemLenhDieuDongBinding> {

    private Context context;
    private View root;
    private List<Shipment> shipments;
    RecyclerViewTripListener onClick;

    public DieuDongAdapter(RecyclerViewTripListener onClick, List<Shipment> shipments) {
        this.onClick = onClick;
        this.shipments = shipments;
    }


    public DieuDongAdapter() {
    }

    @Nullable
    @Override
    public List<Shipment> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemLenhDieuDongBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_lenh_dieu_dong, parent, false);
    }

    @Override
    protected void bind(ItemLenhDieuDongBinding binding, Shipment item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        Bitmap bitmap = null;
        try {

            bitmap = Utilities.textToImage(item.atM_SHIPMENT_ID, 150, 150);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        binding.QRCode.setImageBitmap(bitmap);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(position,item.atM_SHIPMENT_ID,item.trucktype,item.starT_TIME,item.routeno,item.driveR_GID,0);
            }
        });

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
