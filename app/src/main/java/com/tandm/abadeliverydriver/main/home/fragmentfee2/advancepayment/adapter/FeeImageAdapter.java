package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ImageFee;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import java.util.List;

public class FeeImageAdapter  extends RecyclerView.Adapter<FeeImageAdapter.ItemRowHolder>{

    private List<ImageFee> imageFees;
    private Context context;
    private RecyclerViewItemClick<ImageFee> onClick;
    public FeeImageAdapter(List<ImageFee> imageFees, Context context, RecyclerViewItemClick<ImageFee> onClick) {
        this.imageFees = imageFees;
        this.context = context;
        this.onClick = onClick;
    }



    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_fee, null);
        return new ItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
        String path = imageFees.get(position).getPath();
        Uri uri = imageFees.get(position).getUri();
        holder.itemImage.setImageURI(uri);

        holder.btnCloseImageFee2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(imageFees.get(position),position,0);
            }
        });

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(imageFees.get(position), position, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != imageFees ? imageFees.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private ImageView btnCloseImageFee2;

        public ItemRowHolder(@NonNull View itemView) {
            super(itemView);
            this.btnCloseImageFee2 = itemView.findViewById(R.id.btnCloseImageFee2);
            this.itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
