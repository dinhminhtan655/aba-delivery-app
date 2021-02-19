package com.tandm.abadeliverydriver.main.home.fragmenthomedriver.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ImageFee;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import java.util.List;

public class ADCADShipmentAdapter extends RecyclerView.Adapter<ADCADShipmentAdapter.ItemRowHolder> {


    private List<ImageFee> imageFees;
    private Context context;
    private RecyclerViewItemClick<ImageFee> onClick;


    public ADCADShipmentAdapter(List<ImageFee> imageFees, Context context, RecyclerViewItemClick<ImageFee> onClick) {
        this.imageFees = imageFees;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public ADCADShipmentAdapter.ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.item_image_fee) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_fee, null);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagebuttonadd, null);
        }

        return new ADCADShipmentAdapter.ItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADCADShipmentAdapter.ItemRowHolder holder, int position) {

        if (position == imageFees.size()) {
            holder.btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClick(null,position,0);
                }
            });


        } else {
            ImageFee imageFee = imageFees.get(holder.getAdapterPosition());

            Uri uri = imageFees.get(position).getUri();
            holder.itemImage.setImageURI(uri);

            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClick(imageFee,position,1);
                }
            });

            holder.btnCloseImageFee2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClick(imageFee,position,2);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
//        return (null != imageFees ? imageFees.size() : 0);
        return imageFees.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == imageFees.size()) ? R.layout.imagebuttonadd : R.layout.item_image_fee;
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private ImageView btnCloseImageFee2;
        private ImageButton btnCamera;

        public ItemRowHolder(@NonNull View itemView) {
            super(itemView);
            this.btnCloseImageFee2 = itemView.findViewById(R.id.btnCloseImageFee2);
            this.itemImage = itemView.findViewById(R.id.itemImage);
            this.btnCamera = itemView.findViewById(R.id.btnCamera);
        }

    }
}
