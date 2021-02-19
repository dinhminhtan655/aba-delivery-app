package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historydriverdelivery.adapter;

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
import com.tandm.abadeliverydriver.databinding.ItemHistoryShipmentDriverBinding;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historydriverdelivery.model.HistoryDriverDelivery;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewHisDriverListener;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.util.List;

import butterknife.ButterKnife;

public class HistoryDriverDeliveryAdapter extends DataBoundListAdapter<HistoryDriverDelivery, ItemHistoryShipmentDriverBinding> {

    private Context context;
    private View root;

    RecyclerViewHisDriverListener onClick;

    public HistoryDriverDeliveryAdapter(RecyclerViewHisDriverListener onClick) {
        this.onClick = onClick;
    }

    @Nullable
    @Override
    public List<HistoryDriverDelivery> getItems() {
        return super.getItems();
    }

    @Override
    protected ItemHistoryShipmentDriverBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_history_shipment_driver, parent, false);
    }

    @Override
    protected void bind(ItemHistoryShipmentDriverBinding binding, HistoryDriverDelivery item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        Bitmap bitmap = null;
        try {

            bitmap = Utilities.textToImage(item.atM_SHIPMENT_ID, 150, 150);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        binding.imgQRCode.setImageBitmap(bitmap);

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, item.atM_SHIPMENT_ID, item.delivery_Time, item.getDateStartTime(item.starT_TIME));
            }
        });
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected boolean areItemsTheSame(HistoryDriverDelivery oldItem, HistoryDriverDelivery newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(HistoryDriverDelivery oldItem, HistoryDriverDelivery newItem) {
        return false;
    }
}
