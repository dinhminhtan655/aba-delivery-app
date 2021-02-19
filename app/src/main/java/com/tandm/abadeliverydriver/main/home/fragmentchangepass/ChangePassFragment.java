package com.tandm.abadeliverydriver.main.home.fragmentchangepass;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.login.LoginActivity;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassFragment extends Fragment {

    public static final String TAG = "ChangePassFragment";
    View view;
    Unbinder unbinder;
    @BindView(R.id.edtOldPassChange)
    TextInputEditText edtOldPassChange;
    @BindView(R.id.edtNewPassChange)
    TextInputEditText edtNewPassChange;
    @BindView(R.id.edtConfirmNewPassChange)
    TextInputEditText edtConfirmNewPassChange;

    ProgressDialog progressDialog;

    String strOldPass, strNewPass, strConfirmNewPass, strUsername, strID, strGrandType, strToken;

    public ChangePassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnReset)
    public void reset(){
        edtOldPassChange.setText("");
        edtNewPassChange.setText("");
        edtConfirmNewPassChange.setText("");
    }


    @OnClick(R.id.btnOkay)
    public void chagnePass(){
        strOldPass = edtOldPassChange.getText().toString();
        strNewPass = edtNewPassChange.getText().toString();
        strConfirmNewPass = edtConfirmNewPassChange.getText().toString();
        strUsername = LoginPrefer.getObject(getContext()).userName;
        strGrandType = "password";
        strID = LoginPrefer.getObject(getContext()).userId;
        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;

        progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
        progressDialog.show();

        if (!WifiHelper.isConnected(getContext())) {
            Utilities.dismissDialog(progressDialog);
            RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            return;
        }

        MyRetrofit.initRequest(getContext()).changePass(strUsername,strID,strGrandType,strOldPass,strNewPass,strConfirmNewPass,strToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("logo", response.body()+"");
                if (response.isSuccessful() && response.body() != null){
                    Snackbar.make(view, response.body(), Snackbar.LENGTH_LONG).show();
                    LoginPrefer.deleteObject(getActivity());
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }else {
                    Snackbar.make(view, "Đổi mật khẩu thất bại vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
                }
                Utilities.dismissDialog(progressDialog);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });

//        MyRetrofit.initRequest(getContext()).changePass(strUsername,strID,strGrandType,strOldPass,strNewPass,strConfirmNewPass,strToken).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Response<String> response, Retrofit retrofit) {
//                Log.e("logo", response.body()+"");
//                if (response.isSuccess() && response.body() != null){
//                    Snackbar.make(view, "Đổi mật khẩu thành công!", Snackbar.LENGTH_LONG).show();
//                    LoginPrefer.deleteObject(getActivity());
//                    startActivity(new Intent(getContext(), LoginActivity.class));
//                    getActivity().finish();
//                }else {
//                    Snackbar.make(view, "Đổi mật khẩu thất bại vui lòng thử lại!", Snackbar.LENGTH_LONG).show();
//                }
//                Utilities.dismissDialog(progressDialog);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Utilities.dismissDialog(progressDialog);
//                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
//            }
//        });
    }




}
