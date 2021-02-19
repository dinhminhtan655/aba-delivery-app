package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.updatehistorykhay.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historytraydelivery.HistoryTrayDeliveryNotesChild;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassCheckBox;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassList2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryKhayDeliveryNotesAdapter extends RecyclerView.Adapter<HistoryKhayDeliveryNotesAdapter.ItemChildViewHolder> {


    private List<HistoryTrayDeliveryNotesChild> itemChildren;
    private static List<HistoryTrayDeliveryNotesChild> stringList;

    Context context;
    PassCheckBox passCheckBox;

    PassList2 passList;


    public HistoryKhayDeliveryNotesAdapter(List<HistoryTrayDeliveryNotesChild> stringList, PassCheckBox passCheckBox) {
        this.stringList = stringList;
        this.passCheckBox = passCheckBox;
    }

    public HistoryKhayDeliveryNotesAdapter(PassList2 passList) {
        this.passList = passList;
    }

    public HistoryKhayDeliveryNotesAdapter() {

    }

    @NonNull
    @Override
    public ItemChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update_items_pgh, parent, false);
        return new ItemChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemChildViewHolder holder, int position) {
        HistoryTrayDeliveryNotesChild itemChild = itemChildren.get(position);
        holder.tvTitleKhay.setText(String.valueOf(itemChild.trayName));
        holder.edtKhayGiao.setText(String.valueOf(itemChild.trayDelivering));
        holder.edtKhayLayVe.setText(String.valueOf(itemChild.trayReceiving));
        holder.edtUpdateKhayGiao.setText(String.valueOf(stringList.get(position).getEditTextUpdateKhayGiaoCH()));
        holder.edtUpdateKhayLayVe.setText(String.valueOf(stringList.get(position).getEditTextUpdateKhayLayVeTuCH()));
//        holder.edtUpdateKhayGiao.setText(String.valueOf(stringList.get(position).soBich));
//        holder.edtUpdateKhayLayVe.setText(String.valueOf(stringList.get(position).actual_Received));

        holder.bind(position);

        if (stringList.get(position).isChecked()) {
            holder.cbItemsKhay.setChecked(true);
        } else {
            holder.cbItemsKhay.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        if (itemChildren != null) {
            return itemChildren.size();
        } else {
            return 0;
        }
    }

    public void Send() {
        passList.passList(this.stringList);
    }

    public void selectAll() {

        for (int i = 0; i < stringList.size(); i++) {
            if (stringList.get(i).isChecked() == false) {
                stringList.get(i).setChecked(true);
                passCheckBox.passCongCB(1);
            }
        }
        notifyDataSetChanged();
    }


    public void setItemChildren(Context context, List<HistoryTrayDeliveryNotesChild> itemChildren) {
        this.context = context;
        this.itemChildren = itemChildren;
        notifyDataSetChanged();
    }

    public class ItemChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitleKhay)
        TextView tvTitleKhay;
        @BindView(R.id.edtKhayGiao)
        TextInputEditText edtKhayGiao;
        @BindView(R.id.edtKhayLayVe)
        TextInputEditText edtKhayLayVe;
        @BindView(R.id.edtUpdateKhayGiao)
        TextInputEditText edtUpdateKhayGiao;
        @BindView(R.id.edtUpdateKhayLayVe)
        TextInputEditText edtUpdateKhayLayVe;
        @BindView(R.id.cbItemsKhay)
        CheckBox cbItemsKhay;

        public ItemChildViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cbItemsKhay.setOnClickListener(this::onClick);

            edtUpdateKhayGiao.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    stringList.get(getAdapterPosition()).setEditTextUpdateKhayGiaoCH(edtUpdateKhayGiao.getText().toString().equals("") ? "0" : edtUpdateKhayGiao.getText().toString());


                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            edtUpdateKhayLayVe.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    stringList.get(getAdapterPosition()).setEditTextUpdateKhayLayVeTuCH(edtUpdateKhayLayVe.getText().toString().equals("") ? "0" : edtUpdateKhayLayVe.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        void bind(int postion) {
            if (stringList.get(postion).isChecked()) {
                cbItemsKhay.setChecked(true);
            } else {
                cbItemsKhay.setChecked(false);
            }
        }


        @Override
        public void onClick(View v) {
            int a = getAdapterPosition();
            switch (v.getId()) {
                case R.id.cbItems:
                    if (stringList.get(a).isChecked()) {
                        cbItemsKhay.setChecked(false);
                        stringList.get(a).setChecked(false);
                        passCheckBox.passTruCB(1);
                    } else {
                        cbItemsKhay.setChecked(true);
                        stringList.get(a).setChecked(true);
                        passCheckBox.passCongCB(1);
                    }

                    notifyDataSetChanged();
                    break;
            }
        }
    }
}
