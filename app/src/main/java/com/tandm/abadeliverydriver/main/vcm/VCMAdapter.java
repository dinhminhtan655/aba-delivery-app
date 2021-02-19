package com.tandm.abadeliverydriver.main.vcm;

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

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassCheckBox;
import com.tandm.abadeliverydriver.main.recycleviewadapter.PassListVCM;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VCMAdapter extends RecyclerView.Adapter<VCMAdapter.VCMChildViewHolder> {

    Context context;
    private List<Khay> list;
    private static List<Khay> stringVCMList;
    PassCheckBox passCheckBox;

    PassListVCM passListVCM;

    public VCMAdapter(List<Khay> stringVCMList, PassCheckBox passCheckBox) {
        this.stringVCMList = stringVCMList;
        this.passCheckBox = passCheckBox;
    }

    public VCMAdapter(PassListVCM passListVCM) {
        this.passListVCM = passListVCM;
    }

    public VCMAdapter() {
    }

    @NonNull
    @Override
    public VCMChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vcm, parent, false);
        return new VCMChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VCMChildViewHolder holder, int position) {
        holder.tvTitleKhay.setText(list.get(position).khay);

        holder.edtKhayGiao.setText(String.valueOf(stringVCMList.get(position).getEdtTextSLKhayGiao()));
        holder.edtKhayLayVe.setText(String.valueOf(stringVCMList.get(position).getEdtTextSLKhayLayVe()));

        holder.bind(position);

        if (stringVCMList.get(position).isChecked()){
            holder.cbItemsKhay.setChecked(true);
        }else {
            holder.cbItemsKhay.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }else {
            return 0;
        }

    }

    public void SendVCM(){
        passListVCM.passList(this.stringVCMList);
    }


    public void selectAll(){

        for (int i = 0; i < stringVCMList.size(); i++){
            if (stringVCMList.get(i).isChecked() == false){
                stringVCMList.get(i).setChecked(true);
                passCheckBox.passCongCB(1);
            }
        }
        notifyDataSetChanged();
    }

    public void setList(Context context, List<Khay> list){
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    public class VCMChildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tvTitleKhay)
        TextView tvTitleKhay;
        @BindView(R.id.cbItemsKhay)
        CheckBox cbItemsKhay;
        @BindView(R.id.edtKhayGiao)
        TextInputEditText edtKhayGiao;
        @BindView(R.id.edtKhayLayVe)
        TextInputEditText edtKhayLayVe;

        public VCMChildViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cbItemsKhay.setOnClickListener(this::onClick);

            edtKhayGiao.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    stringVCMList.get(getAdapterPosition()).setEdtTextSLKhayGiao(edtKhayGiao.getText().toString().equals("") ? 0 : Integer.parseInt(edtKhayGiao.getText().toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtKhayLayVe.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    stringVCMList.get(getAdapterPosition()).setEdtTextSLKhayLayVe(edtKhayLayVe.getText().toString().equals("") ? 0 : Integer.parseInt(edtKhayLayVe.getText().toString()));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        }

        void bind(int position){
            if (stringVCMList.get(position).isChecked()) {
                cbItemsKhay.setChecked(true);
            } else {
                cbItemsKhay.setChecked(false);
            }
        }

        @Override
        public void onClick(View v) {
            int a = getAdapterPosition();
            if (stringVCMList.get(a).isChecked()) {
                cbItemsKhay.setChecked(false);
                stringVCMList.get(a).setChecked(false);
                passCheckBox.passTruCB(1);
            } else {
                cbItemsKhay.setChecked(true);
                stringVCMList.get(a).setChecked(true);
                passCheckBox.passCongCB(1);
            }
        }
    }
}
