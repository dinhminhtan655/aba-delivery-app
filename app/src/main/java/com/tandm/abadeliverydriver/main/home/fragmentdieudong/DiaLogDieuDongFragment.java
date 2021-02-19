package com.tandm.abadeliverydriver.main.home.fragmentdieudong;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.zxing.WriterException;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaLogDieuDongFragment extends DialogFragment {

    private Unbinder unbinder;
    @BindView(R.id.imgDialogDieuDong)
    ImageView imgDialogDieuDong;
    @BindView(R.id.tvIDDialogDieuDong)
    TextView tvIDDialogDieuDong;
    @BindView(R.id.btnDongDiaDieuDong)
    Button btnDongDiaDieuDong;
    View view;
    public DiaLogDieuDongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dia_log_dieu_dong, container, false);
        unbinder = ButterKnife.bind(this,view);

        Bundle mArgs = getArguments();
        String myValue = mArgs.getString("atm_shipment_id");
        Bitmap bitmap = null;
        try {

            bitmap = Utilities.textToImage(myValue, 150, 150);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        imgDialogDieuDong.setImageBitmap(bitmap);

        tvIDDialogDieuDong.setText(myValue);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnDongDiaDieuDong)
    public void dongDiaLog(){
        getDialog().dismiss();
    }


}
