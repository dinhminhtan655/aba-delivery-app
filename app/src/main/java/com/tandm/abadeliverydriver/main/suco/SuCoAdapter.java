package com.tandm.abadeliverydriver.main.suco;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.databinding.ItemProblemBinding;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemListener;
import com.tandm.abadeliverydriver.main.suco.fragment.ProblemChild;

import java.util.List;

public class SuCoAdapter extends RecyclerView.Adapter<SuCoAdapter.ProblemChildViewHolder> {

    private List<ProblemChild> problemChildren;
    private RecyclerViewItemListener onClick;

    public SuCoAdapter(RecyclerViewItemListener onClick) {
        this.onClick = onClick;
    }

    public SuCoAdapter() {
    }

    @NonNull
    @Override
    public ProblemChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProblemBinding itemProblemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_problem, parent, false);
        return new ProblemChildViewHolder(itemProblemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemChildViewHolder holder, int position) {
        ProblemChild currentProblem = problemChildren.get(position);
        holder.itemProblemBinding.setItem(currentProblem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(position, problemChildren.get(position).title, problemChildren.get(position).rowId);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (problemChildren != null) {
            return problemChildren.size();
        } else {
            return 0;
        }

    }

    public void setProblemChildren(List<ProblemChild> problemChildren) {
        this.problemChildren = problemChildren;
        notifyDataSetChanged();
    }

    public class ProblemChildViewHolder extends RecyclerView.ViewHolder {
        private ItemProblemBinding itemProblemBinding;

        public ProblemChildViewHolder(ItemProblemBinding itemProblemBinding) {
            super(itemProblemBinding.getRoot());
            this.itemProblemBinding = itemProblemBinding;
        }
    }
}
