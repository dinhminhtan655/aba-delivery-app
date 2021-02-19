package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.Comment;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentAdapter.CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.tvCommentBy.setText(comment.commentBy);
        holder.tvCommentComment.setText(comment.comment);
        holder.tvCommentTime.setText(Utilities.formatDate_ddMMyyHHmm(comment.commentTime));
    }

    @Override
    public int getItemCount() {
        if (comments != null) {
            return comments.size();
        } else {
            return 0;
        }
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCommentBy)
        TextView tvCommentBy;
        @BindView(R.id.tvCommentComment)
        TextView tvCommentComment;
        @BindView(R.id.tvCommentTime)
        TextView tvCommentTime;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
