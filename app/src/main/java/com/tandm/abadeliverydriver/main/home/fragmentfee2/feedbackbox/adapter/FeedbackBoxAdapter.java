package com.tandm.abadeliverydriver.main.home.fragmentfee2.feedbackbox.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemFeedbackBoxBinding;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.feedbackbox.model.FeedbackBox;
import com.tandm.abadeliverydriver.main.recycleviewadapter.DataBoundListAdapter;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;

import butterknife.ButterKnife;

public class FeedbackBoxAdapter extends DataBoundListAdapter<FeedbackBox, ItemFeedbackBoxBinding> {

    private Context context;
    private View root;
    private RecyclerViewItemClick<FeedbackBox> recyclerViewItemClick;

    public FeedbackBoxAdapter(RecyclerViewItemClick<FeedbackBox> recyclerViewItemClick) {
        this.recyclerViewItemClick = recyclerViewItemClick;
    }

    @Override
    protected ItemFeedbackBoxBinding createBinding(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ButterKnife.bind((Activity) context);
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_feedback_box, parent, false);
    }

    @Override
    protected void bind(ItemFeedbackBoxBinding binding, FeedbackBox item, int position) {
        binding.setItem(item);
        root = binding.getRoot();

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewItemClick.onClick(item, position, 0);
            }
        });
    }

    @Override
    protected boolean areItemsTheSame(FeedbackBox oldItem, FeedbackBox newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(FeedbackBox oldItem, FeedbackBox newItem) {
        return false;
    }

}
