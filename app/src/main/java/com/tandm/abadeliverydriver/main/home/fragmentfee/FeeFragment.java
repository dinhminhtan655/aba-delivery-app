package com.tandm.abadeliverydriver.main.home.fragmentfee;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.FileUtils;
import com.tandm.abadeliverydriver.main.utilities.NumberTextWatcher;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeeFragment extends Fragment {
    private static final int PERMISSION_CODE = 10001;
    private static final int MY_CAMERA_REQUEST_CODE = 2;
    private static final String TAG = "FeeFragment";
    private Unbinder unbinder;
    View view;
    @BindView(R.id.spinFee)
    Spinner spinFee;
    @BindView(R.id.edtMoneyFee)
    TextInputEditText edtMoneyFee;
    @BindView(R.id.edtNoteFee)
    TextInputEditText edtNoteFee;
    @BindView(R.id.imgFee)
    ImageView imgFee;
    @BindView(R.id.btnCloseImageFee)
    ImageButton btnCloseImageFee;
    FeeSpinnerAdapter adapter;

    String strSpinnerFee, strMoneyFee, strNoteFee;

    ProgressDialog progressDialog;

    Uri img_uri;

    ArrayList<Uri> list;

    public FeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fee, container, false);
        unbinder = ButterKnife.bind(this, view);

        list = new ArrayList<>(3);
        getDataSpinnerFee();
        spinFee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Fee fee = (Fee) adapter.getItem(i);
                strSpinnerFee = fee.iD_LoaiPhi;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtMoneyFee.addTextChangedListener(new NumberTextWatcher(edtMoneyFee));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
        }

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Utilities.openSettings(getContext());
                }
            }
        }
    }

    private void getDataSpinnerFee() {
        MyRetrofit.initRequest(getContext()).getFee("Bearer " + LoginPrefer.getObject(getContext()).access_token).enqueue(new Callback<List<Fee>>() {
            @Override
            public void onResponse(Call<List<Fee>> call, Response<List<Fee>> response) {
                if (response.isSuccessful() && response != null) {
                    adapter = new FeeSpinnerAdapter(response.body(), getContext());
                    spinFee.setAdapter(adapter);
                }else {
                    RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                }
            }

            @Override
            public void onFailure(Call<List<Fee>> call, Throwable t) {
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
            }
        });
    }


    @OnClick(R.id.btnSendFee)
    public void sendFee(View view) {

        if (list.size() == 0){
            Snackbar.make(view, "Vui lòng gửi hình tương ứng với loại phí bạn đã chọn!", Snackbar.LENGTH_LONG).show();
        }else {
            progressDialog = Utilities.getProgressDialog(getContext(), "Đang xử lý...");
            progressDialog.show();

            if (!WifiHelper.isConnected(getContext())) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(getContext(), new NoInternet(), TAG, view);
                return;
            }

            Bitmap bmScale = null;
            try {
                bmScale = Utilities.getThumbnail(list.get(0),   getContext());
            }catch (IOException e){
                e.printStackTrace();
            }

            Uri uri = Utilities.getImageUri(getContext(),Utilities.addDateText(bmScale));

            String path = uri.getPath();

            File originalfile = FileUtils.getFile(getContext(), uri);

            Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

            RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

            MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

            strMoneyFee = edtMoneyFee.getText().toString();
            strMoneyFee = strMoneyFee.replace(",", "");
            strNoteFee = edtNoteFee.getText().toString();

            MyRetrofit.initRequest(getContext()).uploadImageFee(LoginPrefer.getObject(getContext()).userName,
                    Utilities.formatDateTime_ddMMMyyyyFromMili(System.currentTimeMillis()),
                    strSpinnerFee, strMoneyFee, strNoteFee, file, "Bearer " + LoginPrefer.getObject(getContext()).access_token).enqueue(new Callback<List<ImageFee>>() {
                @Override
                public void onResponse(Call<List<ImageFee>> call, Response<List<ImageFee>> response) {
                    if (response.isSuccessful() && response != null) {
                        Snackbar.make(view, "Gửi thành công", Snackbar.LENGTH_LONG).show();
                        imgFee.setImageResource(R.drawable.classiccamerapng);
                        btnCloseImageFee.setVisibility(View.INVISIBLE);
                        list.clear();
                        edtMoneyFee.setText("");
                        edtNoteFee.setText("");
                    }
                    Utilities.dismissDialog(progressDialog);
                }

                @Override
                public void onFailure(Call<List<ImageFee>> call, Throwable t) {
                    Utilities.dismissDialog(progressDialog);
                    Snackbar.make(view, "Thất bại! Vui lòng thử lại.", Snackbar.LENGTH_LONG).show();
                }
            });
        }




    }

    @OnClick(R.id.imgFee)
    public void imageFee(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permission, PERMISSION_CODE);
            } else {
                openCamera(2000);
            }
        } else {
            openCamera(2000);
        }
    }

    @OnClick(R.id.btnCloseImageFee)
    public void closeImageFee(View view) {
        btnCloseImageFee.setVisibility(View.INVISIBLE);
        imgFee.setImageResource(R.drawable.classiccamerapng);
        list.clear();
        ContentResolver contentResolver = getContext().getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?", new String[]{imgFee.toString()});
    }

    private void openCamera(int i) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        img_uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, img_uri);
        startActivityForResult(cameraIntent, i);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2000) {
                String path = img_uri.toString();
                Utilities.xulyAnh(getContext(), path, imgFee, img_uri);
                btnCloseImageFee.setVisibility(View.VISIBLE);
                list.add(img_uri);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
