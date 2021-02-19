package com.tandm.abadeliverydriver.main.home.fragmentdieudong;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.WriterException;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmentdieudong.model.DieuDong;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DieuDongFragment extends Fragment {
    private static final String TAG = "DieuDongFragment";
    View view;
    Unbinder unbinder;

    @BindView(R.id.imgQR)
    ImageView imgQR;
    @BindView(R.id.tvDieuDongTitle)
    TextView tvDieuDongTitle;
    @BindView(R.id.tvMaLenh)
    TextView tvMaLenh;
    Bitmap merge;
    String strToken;
    ProgressDialog progressDialog;

    public DieuDongFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dieu_dongk, container, false);
        unbinder = ButterKnife.bind(this, view);
        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;

        getQRCode();

        return view;
    }


    public void getQRCode() {
        progressDialog = Utilities.getProgressDialog(getContext(), "Đang tải...");
        progressDialog.show();


        if (!WifiHelper.isConnected(getActivity())) {
//            RetrofitError.errorAction(getActivity(), new NoInternet(), TAG, view);
            Utilities.dismissDialog(progressDialog);
            imgQR.setVisibility(View.GONE);
            tvDieuDongTitle.setText("Không có kết nối với Internet!");
            return;
        }

        MyRetrofit.initRequest(getContext()).GetDieuDong(strToken, LoginPrefer.getObject(getContext()).MaNhanVien).enqueue(new Callback<List<DieuDong>>() {
            @Override
            public void onResponse(Call<List<DieuDong>> call, Response<List<DieuDong>> response) {
                if (response.isSuccessful() && response != null) {
                    if (response.body().size() == 0) {
                        imgQR.setVisibility(View.GONE);
                        tvDieuDongTitle.setText("Hiện tại bạn chưa có Lệnh Điều Động");
                    } else {

                        Bitmap bitmap = null;
                        try {
                            tvDieuDongTitle.setText("Mã Lệnh Điều Động");
                            bitmap = Utilities.textToImage(response.body().get(0).atM_SHIPMENT_id, 500, 500);
//                            Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logoaba);

//                            merge = Utilities.mergeBitmaps(logo,bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        imgQR.setImageBitmap(bitmap);

                        tvMaLenh.setText(response.body().get(0).atM_SHIPMENT_id);
                    }
                }
                Utilities.dismissDialog(progressDialog);
            }

            @Override
            public void onFailure(Call<List<DieuDong>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
