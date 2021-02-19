package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.historystopdriverdelivery.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.nhanhang.model.ItemChild;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassCheckBox;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryDeliveryNotesAdapter extends RecyclerView.Adapter<HistoryDeliveryNotesAdapter.ItemChildViewHolder> {

    private List<ItemChild> itemChildren;
    private static List<ItemChild> stringList;

    Context context;
    PassCheckBox passCheckBox;

    PassList passList;

    public HistoryDeliveryNotesAdapter(List<ItemChild> stringList, PassCheckBox passCheckBox) {
        this.stringList = stringList;
        this.passCheckBox = passCheckBox;
    }

    public HistoryDeliveryNotesAdapter(PassList passList) {
        this.passList = passList;
    }

    public HistoryDeliveryNotesAdapter() {

    }

    @NonNull
    @Override
    public ItemChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_items_pgh, parent, false);
        return new ItemChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryDeliveryNotesAdapter.ItemChildViewHolder holder, int position) {
        ItemChild itemChild = itemChildren.get(position);
        holder.imgHeader.setImageResource(position % 2 == 0 ? R.drawable.basket1 : R.drawable.basket2);
        holder.tvHeader.setText(position + 1 + ". " + itemChildren.get(position).item_Name + " : " + itemChildren.get(position).soBich + " " + itemChildren.get(position).div_Unit);
        holder.edtMaSP.setText(itemChildren.get(position).item_Code);
        holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
        holder.tvSLNhan.setText(String.valueOf(itemChildren.get(position).soBich));
        holder.tvThieuList.setText(String.valueOf(stringList.get(position).getTvSLThieuValue()));
        holder.tvDuList.setText(String.valueOf(stringList.get(position).getTvSLDuValue()));
        holder.tvTraVeList.setText(String.valueOf(stringList.get(position).getTvSLTraVeValue()));
        holder.edtNote.setText(stringList.get(position).getEditTextNote());

        if (stringList.get(position).getTvSLThieuValue() == 0) {
            holder.btnTruThieuList.setEnabled(false);
        } else {
            holder.btnTruThieuList.setEnabled(true);
        }

        if (stringList.get(position).getTvSLDuValue() == 0) {
            holder.btnTruDuList.setEnabled(false);
        } else {
            holder.btnTruDuList.setEnabled(true);
        }


        if (stringList.get(position).getTvSLTraVeValue() == 0) {
            holder.btnTruTraVeList.setEnabled(false);
        } else {
            holder.btnTruTraVeList.setEnabled(true);
        }


        holder.bind(position);

        holder.bindThieu(position);

        holder.bindDu(position);

        holder.bindTraVe(position);


        holder.cardHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean expanded = itemChild.isExpanded();
                itemChild.setExpanded(!expanded);

                notifyItemChanged(position);
            }
        });

        holder.btnTruThieuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringList.get(position).getTvSLThieuValue() > 0) {
                    stringList.get(position).setTvSLThieuValue(stringList.get(position).getTvSLThieuValue() - 1);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() + 1);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvThieuList.setText(String.valueOf(stringList.get(position).getTvSLThieuValue()));
                }
                notifyDataSetChanged();
            }
        });

        holder.btnCongThieuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringList.get(position).getTvSLGiaoCHValue() == 0){
                    stringList.get(position).setTvSLThieuValue(stringList.get(position).getTvSLThieuValue() + 0);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() - 0);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvThieuList.setText(String.valueOf(stringList.get(position).getTvSLThieuValue()));
                }else if (stringList.get(position).getTvSLGiaoCHValue() > 0){
                    stringList.get(position).setTvSLThieuValue(stringList.get(position).getTvSLThieuValue() + 1);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() - 1);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvThieuList.setText(String.valueOf(stringList.get(position).getTvSLThieuValue()));
                }
                notifyDataSetChanged();
            }
        });


        holder.btnTruDuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringList.get(position).getTvSLDuValue() > 0) {
                    stringList.get(position).setTvSLDuValue(stringList.get(position).getTvSLDuValue() - 1);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() + 1);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvDuList.setText(String.valueOf(stringList.get(position).getTvSLDuValue()));
                }
                notifyDataSetChanged();
            }
        });


        holder.btnCongDuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringList.get(position).getTvSLGiaoCHValue() == 0){
                    stringList.get(position).setTvSLDuValue(stringList.get(position).getTvSLDuValue() + 0);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() - 0);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvDuList.setText(String.valueOf(stringList.get(position).getTvSLDuValue()));
                }else if (stringList.get(position).getTvSLGiaoCHValue() > 0){
                    stringList.get(position).setTvSLDuValue(stringList.get(position).getTvSLDuValue() + 1);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() - 1);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvDuList.setText(String.valueOf(stringList.get(position).getTvSLDuValue()));
                }
                notifyDataSetChanged();
            }
        });


        holder.btnTruTraVeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringList.get(position).getTvSLTraVeValue() > 0) {
                    stringList.get(position).setTvSLTraVeValue(stringList.get(position).getTvSLTraVeValue() - 1);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() + 1);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvTraVeList.setText(String.valueOf(stringList.get(position).getTvSLTraVeValue()));
                }
                notifyDataSetChanged();
            }
        });


        holder.btnCongTraVeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringList.get(position).getTvSLGiaoCHValue() == 0){
                    stringList.get(position).setTvSLTraVeValue(stringList.get(position).getTvSLTraVeValue() + 0);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() - 0);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvTraVeList.setText(String.valueOf(stringList.get(position).getTvSLTraVeValue()));
                }else if (stringList.get(position).getTvSLGiaoCHValue() > 0){
                    stringList.get(position).setTvSLTraVeValue(stringList.get(position).getTvSLTraVeValue() + 1);
                    stringList.get(position).setTvSLGiaoCHValue(stringList.get(position).getTvSLGiaoCHValue() - 1);
                    holder.tvSLGiao.setText(String.valueOf(stringList.get(position).getTvSLGiaoCHValue()));
                    holder.tvTraVeList.setText(String.valueOf(stringList.get(position).getTvSLTraVeValue()));
                }
                notifyDataSetChanged();
            }
        });


        if (stringList.get(position).isChecked()) {
            holder.cbItems.setChecked(true);
        } else {
            holder.cbItems.setChecked(false);
        }


        if (stringList.get(position).isCheckedThieu()) {
            holder.cbThieuList.setChecked(true);
        } else {
            holder.cbThieuList.setChecked(false);
        }

        if (stringList.get(position).isCheckedDu()) {
            holder.cbDuList.setChecked(true);
        } else {
            holder.cbDuList.setChecked(false);
        }

        if (stringList.get(position).isCheckedTrave()) {
            holder.cbTraVeList.setChecked(true);
        } else {
            holder.cbTraVeList.setChecked(false);
        }


        boolean expanded = itemChild.isExpanded();
        holder.linearLayout.setVisibility(expanded ? View.VISIBLE : View.GONE);
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

    public void setItemChildren(Context context, List<ItemChild> itemChildren) {
        this.context = context;
        this.itemChildren = itemChildren;
        notifyDataSetChanged();
    }

    public class ItemChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tvNameItems)
        TextView tvHeader;
        @BindView(R.id.imgItems)
        ImageView imgHeader;
        @BindView(R.id.edtCodeItems)
        TextInputEditText edtMaSP;
        @BindView(R.id.tvSLNhan)
        TextView tvSLNhan;
        @BindView(R.id.tvSLGiao)
        TextView tvSLGiao;
        @BindView(R.id.cbThieuList)
        CheckBox cbThieuList;
        @BindView(R.id.cbDuList)
        CheckBox cbDuList;
        @BindView(R.id.cbTraVeList)
        CheckBox cbTraVeList;
        @BindView(R.id.btnTruThieuList)
        ImageButton btnTruThieuList;
        @BindView(R.id.btnCongThieuList)
        ImageButton btnCongThieuList;
        @BindView(R.id.btnTruDuList)
        ImageButton btnTruDuList;
        @BindView(R.id.btnCongDuList)
        ImageButton btnCongDuList;
        @BindView(R.id.btnTruTraVeList)
        ImageButton btnTruTraVeList;
        @BindView(R.id.btnCongTraVeList)
        ImageButton btnCongTraVeList;
        @BindView(R.id.tvThieuList)
        TextView tvThieuList;
        @BindView(R.id.tvDuList)
        TextView tvDuList;
        @BindView(R.id.tvTraVeList)
        TextView tvTraVeList;
        @BindView(R.id.edtNote)
        TextInputEditText edtNote;
        @BindView(R.id.cardHeader)
        CardView cardHeader;
        @BindView(R.id.sub_item)
        LinearLayout linearLayout;
        @BindView(R.id.cbItems)
        CheckBox cbItems;

        public ItemChildViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cbItems.setOnClickListener(this::onClick);
            cbThieuList.setOnClickListener(this::onClick);
            cbDuList.setOnClickListener(this::onClick);
            cbTraVeList.setOnClickListener(this::onClick);

            edtNote.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    stringList.get(getAdapterPosition()).setEditTextNote(edtNote.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        void bind(int postion) {
            if (stringList.get(postion).isChecked()) {
                cbItems.setChecked(true);
            } else {
                cbItems.setChecked(false);
            }
        }


        void bindThieu(int position) {
            if (stringList.get(position).isCheckedThieu()) {
                cbThieuList.setChecked(true);
                btnCongThieuList.setEnabled(true);
                btnTruThieuList.setEnabled(true);
            } else {
                cbThieuList.setChecked(false);
                btnCongThieuList.setEnabled(false);
                btnTruThieuList.setEnabled(false);
            }
        }

        void bindDu(int position) {
            if (stringList.get(position).isCheckedDu()) {
                cbDuList.setChecked(true);
                btnCongDuList.setEnabled(true);
                btnTruDuList.setEnabled(true);
            } else {
                cbDuList.setChecked(false);
                btnCongDuList.setEnabled(false);
                btnTruDuList.setEnabled(false);
            }
        }

        void bindTraVe(int position) {
            if (stringList.get(position).isCheckedTrave()) {
                cbTraVeList.setChecked(true);
                btnCongTraVeList.setEnabled(true);
                btnTruTraVeList.setEnabled(true);
            } else {
                cbTraVeList.setChecked(false);
                btnCongTraVeList.setEnabled(false);
                btnTruTraVeList.setEnabled(false);
            }
        }


        @Override
        public void onClick(View v) {
            int a = getAdapterPosition();
            switch (v.getId()) {
                case R.id.cbItems:
                    if (stringList.get(a).isChecked()) {
                        cbItems.setChecked(false);
                        stringList.get(a).setChecked(false);
                        passCheckBox.passTruCB(1);
                    } else {
                        cbItems.setChecked(true);
                        stringList.get(a).setChecked(true);
                        passCheckBox.passCongCB(1);
                    }

                    notifyDataSetChanged();
                    break;


                case R.id.cbThieuList:
                    if (stringList.get(a).isCheckedThieu()) {
                        cbThieuList.setChecked(false);
                        stringList.get(a).setCheckedThieu(false);
                    } else {
                        cbThieuList.setChecked(true);
                        stringList.get(a).setCheckedThieu(true);

                    }
                    notifyDataSetChanged();
                    break;

                case R.id.cbDuList:
                    if (stringList.get(a).isCheckedDu()) {
                        cbDuList.setChecked(false);
                        stringList.get(a).setCheckedDu(false);
                    } else {
                        cbDuList.setChecked(true);
                        stringList.get(a).setCheckedDu(true);
                    }
                    notifyDataSetChanged();
                    break;


                case R.id.cbTraVeList:
                    if (stringList.get(a).isCheckedTrave()) {
                        cbTraVeList.setChecked(false);
                        stringList.get(a).setCheckedTrave(false);
                    } else {
                        cbTraVeList.setChecked(true);
                        stringList.get(a).setCheckedTrave(true);
                    }
                    notifyDataSetChanged();
                    break;
            }
        }
    }
}
