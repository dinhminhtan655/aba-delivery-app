package com.tandm.abadeliverydriver.main.suco;

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
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.suco.fragment.FragmentBottomSheetDialog;
import com.tandm.abadeliverydriver.main.utilities.FileUtils;
import com.tandm.abadeliverydriver.main.utilities.RotateBitmap;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SuCoActivity extends AppCompatActivity implements FragmentBottomSheetDialog.Listener {
    private static final int PERMISSION_CODE = 1000;
    Uri image_uri, image_uri1,image_uri2,image_uri3 ;

    public static String TAG = "SuCoActivity";

    @BindView(R.id.tvThoiGianBaoCao)
    TextView tvThoiGianBaoCao;
    @BindView(R.id.tvMaCuaHangBaoCao)
    TextView tvMaCuaHangBaoCao;
    @BindView(R.id.tvCuaHangBaoCao)
    TextView tvCuaHangBaoCao;
    @BindView(R.id.tvDiaChiBaoCao)
    TextView tvDiaChiBaoCao;
    @BindView(R.id.tbSuCo)
    Toolbar tbSuco;
    @BindView(R.id.edtProblem)
    TextInputEditText edtProblem;
    @BindView(R.id.edtNote)
    TextInputEditText edtNote;
    @BindView(R.id.img1)
    ImageView img1;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.button1)
    ImageButton button1;
    @BindView(R.id.button2)
    ImageButton button2;
    @BindView(R.id.button3)
    ImageButton button3;

    ArrayList<Uri> list;

    public static final int MY_CAMERA_REQUEST_CODE = 1;

    ProgressDialog progressDialog;



    String strStoreCode, strNote, strToken, strCustomerCode, strOrdeR_RELEASE_XID, strPackaged_Item_XID;
    String problemId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_co);
        ButterKnife.bind(this);

        setSupportActionBar(tbSuco);

        Utilities.showBackIcon(getSupportActionBar());

        list = new ArrayList<>(3);

        Bundle b = getIntent().getExtras();
        if (b == null) {
            tvThoiGianBaoCao.setText("");
            tvMaCuaHangBaoCao.setText("");
            tvCuaHangBaoCao.setText("");
            tvDiaChiBaoCao.setText("");
        } else {
            strStoreCode = b.getString("storecode");
            strCustomerCode = b.getString("khachhang");
            strOrdeR_RELEASE_XID = b.getString("orderreleasexid");
            strPackaged_Item_XID = b.getString("packaged_Item_XID");
            tvThoiGianBaoCao.setText(Utilities.formatDate_ddMMyyyy(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis())));
            tvMaCuaHangBaoCao.setText(strStoreCode);
            tvCuaHangBaoCao.setText(b.getString("storename"));
            tvDiaChiBaoCao.setText(b.getString("storeaddress"));

        }

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.img1)
    public void image1(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_DENIED ||
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_DENIED){
                String [] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                requestPermissions(permission, PERMISSION_CODE);
            }else {
                openCamera(1);
            }
        }else {
            openCamera(1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED ){

                }
                else {
                    Utilities.openSettings(SuCoActivity.this);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                String path1 = image_uri1.toString();
                Utilities.xulyAnh(SuCoActivity.this,path1, img1,image_uri1);
                button1.setVisibility(View.VISIBLE);
                list.add(image_uri1);
            }else if (requestCode == 2){
                String path2 = image_uri2.toString();
                Utilities.xulyAnh(SuCoActivity.this,path2, img2,image_uri2);
                button2.setVisibility(View.VISIBLE);
                list.add(image_uri2);
            } else if (requestCode == 3) {
                String path3 = image_uri3.toString();
                Utilities.xulyAnh(SuCoActivity.this,path3, img3,image_uri3);
                button3.setVisibility(View.VISIBLE);
                list.add(image_uri3);
            }
        }

    }

    private void openCamera(int i) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the camera");
        if (i == 1){
            image_uri1 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            image_uri = image_uri1;
        }else if (i == 2){
            image_uri2 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            image_uri = image_uri2;
        }else if (i == 3){
            image_uri3 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            image_uri = image_uri3;
        }
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, i);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.img2)
    public void image2(View view) {
        if (list.size() == 0){
            Snackbar.make(view, "Vui lòng chọn ô đầu tiên", Snackbar.LENGTH_LONG).show();
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED){
                    String [] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(permission, PERMISSION_CODE);
                }else {
                    openCamera(2);
                }
            }else {
                openCamera(2);
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.img3)
    public void image3(View view) {
        if (list.size() == 0){
            Snackbar.make(view, "Vui lòng chọn ô đầu tiên", Snackbar.LENGTH_LONG).show();
        }else if (list.size() == 1){
            Snackbar.make(view, "Vui lòng chọn ô thứ 2", Snackbar.LENGTH_LONG).show();
        }
        else if (list.size() == 2){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED){
                    String [] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    requestPermissions(permission, PERMISSION_CODE);
                }else {
                    openCamera(3);
                }
            }else {
                openCamera(3);
            }
        }
    }



    @OnClick(R.id.button1)
    public void closeImage1(View view) {
        button1.setVisibility(View.INVISIBLE);
        img1.setImageResource(R.drawable.classiccamerapng);
        if (list.size() == 1){
            list.clear();
        }else if (list.size() == 2 || list.size() == 3){
            list.remove(0);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?" , new String[]{ image_uri1.toString() });
    }

    @OnClick(R.id.button2)
    public void closeImage2(View view) {
        button2.setVisibility(View.INVISIBLE);
        img2.setImageResource(R.drawable.classiccamerapng);
        list.remove(1);
        if (list.size() == 1){
            list.clear();
        }else{
            list.remove(1);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?" , new String[]{ image_uri2.toString() });
    }

    @OnClick(R.id.button3)
    public void closeImage3(View view) {
        button3.setVisibility(View.INVISIBLE);
        img3.setImageResource(R.drawable.classiccamerapng);
        if (list.size() == 1){
            list.clear();
        }else if (list.size() == 2){
            list.remove(1);
        }else if (list.size() == 3){
            list.remove(2);
        }
        ContentResolver contentResolver = getContentResolver();
        contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Images.ImageColumns.DATA + "=?" , new String[]{ image_uri3.toString() });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @OnClick(R.id.edtProblem)
    public void bottomSheet() {
        FragmentBottomSheetDialog fragmentBottomSheetDialog = new FragmentBottomSheetDialog();
        fragmentBottomSheetDialog.show(getSupportFragmentManager(), fragmentBottomSheetDialog.getTag());
    }

    @OnClick(R.id.btnBaoCao)
    public void sendBaoCao(View view) {

        if(list.size() == 0){
            Snackbar.make(view, "Vui lòng chọn ít nhất một hình", Snackbar.LENGTH_LONG).show();
        }else {
            progressDialog = Utilities.getProgressDialog(SuCoActivity.this, "Vui lòng chờ...");
            progressDialog.show();

            if (!WifiHelper.isConnected(SuCoActivity.this)) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(SuCoActivity.this, new NoInternet(), TAG, view);
                return;
            }
            strToken = "Bearer " + LoginPrefer.getObject(SuCoActivity.this).access_token;
            strNote = edtNote.getText().toString();
            MyRetrofit.initRequest(SuCoActivity.this).addBaoCao(problemId, strStoreCode, strNote,strCustomerCode, strOrdeR_RELEASE_XID,LoginPrefer.getObject(SuCoActivity.this).userName,strToken).enqueue(new Callback<BaoCao>() {
                @Override
                public void onResponse(Call<BaoCao> call, Response<BaoCao> response) {
                    if (response.isSuccessful() && response != null) {
                        Utilities.dismissDialog(progressDialog);
                        for (int i = 0; i < list.size(); i++){
                            Bitmap bmScale = null;
                            try {
//                                bmScale = Utilities.getThumbnail(list.get(i),   SuCoActivity.this);
                                RotateBitmap rotateBitmap = new RotateBitmap();
                                bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getApplicationContext(),list.get(i));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Uri uri = Utilities.getImageUri(SuCoActivity.this,Utilities.addDateText(bmScale));
                            uploadHinhReport(response.body().id, uri, strToken, view);
                        }
                        list.clear();
                        img1.setImageResource(R.drawable.classiccamerapng);
                        img2.setImageResource(R.drawable.classiccamerapng);
                        img3.setImageResource(R.drawable.classiccamerapng);

                    } else if (response.message().equals("Internal Server Error")) {
                        Utilities.dismissDialog(progressDialog);
                        Snackbar.make(view, strStoreCode + " đã thêm trước đó, vui lòng chọn cửa hàng khác", Snackbar.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<BaoCao> call, Throwable t) {
                    Utilities.dismissDialog(progressDialog);
                    RetrofitError.errorAction(SuCoActivity.this, new NoInternet(), TAG, view);
                }
            });
        }
    }

    private void uploadHinhReport(int id, Uri uri, String token, View view) {


        String path = uri.getPath();

        File originalfile = FileUtils.getFile(this, uri);

        Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

        RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

        MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);


        MyRetrofit.initRequest(SuCoActivity.this).uploadImage(id, file, token).enqueue(new Callback<List<ImageReport>>() {
            @Override
            public void onResponse(Call<List<ImageReport>> call, Response<List<ImageReport>> response) {
                if (response.isSuccessful() && response != null){
                    Snackbar.make(view, "Gửi thành công", Snackbar.LENGTH_LONG).show();
                    finish();
                }else {
                    Snackbar.make(view, "That bai", Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<ImageReport>> call, Throwable t) {
                Utilities.dismissDialog(progressDialog);
                RetrofitError.errorAction(SuCoActivity.this, new NoInternet(), TAG, view);
            }

        });

    }


    @Override
    public void passTitle(String title, int iProblem) {
        problemId = String.valueOf(iProblem);
        edtProblem.setText(title);
    }





}
