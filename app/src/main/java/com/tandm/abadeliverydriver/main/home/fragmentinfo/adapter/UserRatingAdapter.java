package com.tandm.abadeliverydriver.main.home.fragmentinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentinfo.model.UserRating;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRatingAdapter extends RecyclerView.Adapter<UserRatingAdapter.UserRatingViewHolder> {

    private List<UserRating> userRatings;
    private RecyclerViewItemClick<UserRating> onClick;

    private Context context;

    public UserRatingAdapter(RecyclerViewItemClick<UserRating> onClick) {
        this.onClick = onClick;
    }

    public UserRatingAdapter() {
    }

    @NonNull
    @Override
    public UserRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating_zalo, parent, false);
        return new UserRatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRatingViewHolder holder, int position) {
        UserRating userRating = userRatings.get(position);
        holder.tvOrderNumber.setText(userRating.orderNumber);
        holder.tvComment.setText(userRating.znsRatingComment);
        holder.tvTime.setText(userRating.createdTime);
        holder.simpleRatingBar.setNumStars(userRating.znsRatingValue);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onClick(userRating,position,0);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (userRatings != null) {
            return userRatings.size();
        } else {
            return 0;
        }
    }

    public void setUserRatings(Context context, List<UserRating> userRatings) {
        this.context = context;
        this.userRatings = userRatings;
        notifyDataSetChanged();
    }

    public class UserRatingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvOrderNumber)
        TextView tvOrderNumber;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvComment)
        TextView tvComment;
        @BindView(R.id.simpleRatingBar)
        ScaleRatingBar simpleRatingBar;


        public UserRatingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
