package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.github.vipulasri.timelineview.TimelineView;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemSalaryBinding;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypershipment.model.Items;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundViewHolder;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class SalaryAdapter extends DataBoundListAdapter<Items, ItemSalaryBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<Items> onClick;

    public SalaryAdapter(RecyclerViewItemClick<Items> onClick) {
        this.onClick = onClick;
    }

    public SalaryAdapter() {
    }

    @Override
    protected ItemSalaryBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_salary, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<ItemSalaryBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.binding.timeline.initLine(holder.getItemViewType());
    }


    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void bind(ItemSalaryBinding binding, Items item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        binding.timeline.setMarker(context.getResources().getDrawable(R.drawable.coin));


        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(item, position, 0);
            }
        });

        root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClick.onLongClick(item,position,1);
                return false;
            }
        });

    }

    @Override
    protected boolean areItemsTheSame(Items oldItem, Items newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(Items oldItem, Items newItem) {
        return false;
    }
}
