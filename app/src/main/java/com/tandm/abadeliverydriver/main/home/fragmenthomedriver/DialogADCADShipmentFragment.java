package com.tandm.abadeliverydriver.main.home.fragmenthomedriver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.ShowFullImageActivity;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ImageFee;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model.ResultImage;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.adapter.ADCADShipmentAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.ADCADShipment;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.recycleviewadapter.RecyclerViewItemClick;
import com.tandm.abadeliverydriver.main.utilities.FileUtils;
import com.tandm.abadeliverydriver.main.utilities.RotateBitmap;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

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


public class DialogADCADShipmentFragment extends DialogFragment {

    public static final String EDIT_TEXT_BUNDLE_KEY = "i";
    public static final int REQUEST_CODE = 113;
    Unbinder unbinder;
    View view;

    @BindView(R.id.rvImage)
    RecyclerView rvImage;
    @BindView(R.id.btnSend)
    Button btnSend;
    List<ImageFee> list;
    ADCADShipmentAdapter adcadShipmentAdapter;

    ProgressDialog progressDialog;

    private String strToken, strATMShipmentId, strStartTime, strDriverName, strDriverId;

    private Uri img_uri;

    List<MultipartBody.Part> partList;

    public static DialogADCADShipmentFragment newInstances(String atmShipmentId, String startTime, String driverName, String driverId) {
        DialogADCADShipmentFragment dialogADCADShipmentFragment = new DialogADCADShipmentFragment();
        Bundle args = new Bundle();
        args.putString("atmshipmentid", atmShipmentId);
        args.putString("starttime", startTime);
        args.putString("drivername", driverName);
        args.putString("driverid", driverId);
        dialogADCADShipmentFragment.setArguments(args);
        return dialogADCADShipmentFragment;
    }

//    public interface ABADialogADCADShipment {
//        void sendInput(int i);
//    }
//
//    public ABADialogADCADShipment onListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog_a_d_c_a_d_shipment, container, false);
        unbinder = ButterKnife.bind(this, view);

        list = new ArrayList<>();
        strATMShipmentId = getArguments().getString("atmshipmentid");
        strStartTime = getArguments().getString("starttime");
        strDriverName = getArguments().getString("drivername");
        strDriverId = getArguments().getString("driverid");
        strToken = "Bearer " + LoginPrefer.getObject(getContext()).access_token;
        adcadShipmentAdapter = new ADCADShipmentAdapter(list, getContext(), new RecyclerViewItemClick<ImageFee>() {
            @Override
            public void onClick(ImageFee item, int position, int number) {
                switch (number) {
                    case 0:
                        selectImage();
                        break;

                    case 1:
                        Intent intent = new Intent(getContext(), ShowFullImageActivity.class);
                        intent.putExtra("imagefee", item.getUri().toString());
                        startActivity(intent);
                        break;

                    case 2:
                        list.remove(position);
                        adcadShipmentAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onLongClick(ImageFee item, int position, int number) {

            }
        });
        rvImage.setHasFixedSize(true);
        rvImage.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvImage.setAdapter(adcadShipmentAdapter);
        rvImage.setNestedScrollingEnabled(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void selectImage() {
        final CharSequence[] options = {"Chụp hình", "Chọn ảnh từ thư viện", "Hủy"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Chụp hình")) {
                    openCamera(1234);
                } else if (options[item].equals("Chọn ảnh từ thư viện")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Hủy")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void openCamera(int i) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        img_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, img_uri);
        startActivityForResult(cameraIntent, i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1234) {
                String path = img_uri.toString();
                list.add(new ImageFee(path, img_uri));

                if (adcadShipmentAdapter != null) {
                    adcadShipmentAdapter.notifyDataSetChanged();
                }

            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String pathPick = selectedImage.toString();
                list.add(new ImageFee(pathPick, selectedImage));


                if (adcadShipmentAdapter != null) {
                    adcadShipmentAdapter.notifyDataSetChanged();
                } else {
                    adcadShipmentAdapter = new ADCADShipmentAdapter(list, getContext(), new RecyclerViewItemClick<ImageFee>() {
                        @Override
                        public void onClick(ImageFee item, int position, int number) {
                            switch (number) {
                                case 0:
                                    list.remove(position);
                                    adcadShipmentAdapter.notifyDataSetChanged();
                                    break;
                                case 1:
                                    Intent intent = new Intent(getContext(), ShowFullImageActivity.class);
                                    intent.putExtra("imagefee", item.getUri().toString());
                                    startActivity(intent);
                                    break;
                            }
                        }

                        @Override
                        public void onLongClick(ImageFee item, int position, int number) {

                        }
                    });
                }

            }
        }
    }

    @OnClick(R.id.btnSend)
    public void sendADCAD(View view) {

        partList = new ArrayList<>();

        if (list.size() > 0) {

            progressDialog = Utilities.getProgressDialog(getContext(), "Vui lòng đợi...");
            progressDialog.show();

            MyRetrofit.initRequest(getContext()).addADCADShipment(strToken, strATMShipmentId, strStartTime, strDriverName, strDriverId).enqueue(new Callback<ADCADShipment>() {
                @Override
                public void onResponse(Call<ADCADShipment> call, Response<ADCADShipment> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Utilities.dismissDialog(progressDialog);
                        if (response.body().id > -1) {

                            if (list.size() > 0) {

                                for (ImageFee l : list) {

                                    Bitmap bmScale = null;
                                    try {
                                        RotateBitmap rotateBitmap = new RotateBitmap();
                                        bmScale = rotateBitmap.HandleSamplingAndRotationBitmap(getContext(), l.getUri());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Uri uri = Utilities.getImageUri(getContext(), Utilities.addDateText(bmScale));

                                    String path = uri.getPath();

                                    File originalfile = FileUtils.getFile(getContext(), uri);

//                                Log.e("media", MimeTypeMap.getFileExtensionFromUrl(path));

                                    RequestBody filepart = RequestBody.create(MediaType.parse(MimeTypeMap.getFileExtensionFromUrl(path)), originalfile);

                                    MultipartBody.Part file = MultipartBody.Part.createFormData("photo", originalfile.getName(), filepart);

                                    partList.add(file);
                                }
                            }


                            MyRetrofit.initRequest(getContext()).addADCADShipmentFile(strToken, response.body().id, partList).enqueue(new Callback<ResultImage>() {
                                @Override
                                public void onResponse(Call<ResultImage> call, Response<ResultImage> response) {
                                    if (response.isSuccessful() && response.body() != null) {

                                        if (Integer.parseInt(response.body().id) > 0) {
                                            Intent intent = new Intent();
                                            intent.putExtra("yourKey", 0);
                                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                                        } else {
                                            Intent intent = new Intent();
                                            intent.putExtra("yourKey", 1);
                                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResultImage> call, Throwable t) {
                                    Toast.makeText(getContext(), "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else if (response.body().id == -1) {
                            Toast.makeText(getContext(), "Chuyến này đã được kết thúc trước đó bởi NVGN vui lòng liên lạc OP để họ kết thúc chuyến", Toast.LENGTH_SHORT).show();
                        }

                        Utilities.dismissDialog(progressDialog);
                    }
                }

                @Override
                public void onFailure(Call<ADCADShipment> call, Throwable t) {
                    Toast.makeText(getContext(), "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                    Utilities.dismissDialog(progressDialog);
                }
            });
        } else {
            Toast.makeText(getContext(), "Vui lòng chụp hình trước khi gửi ít nhất một tấm", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.btnHuy)
    public void dongDialog() {
        Dialog dialog = getDialog();
        dialog.dismiss();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        try {
//            onListener = (ABADialogADCADShipment) getTargetFragment();
//        } catch (ClassCastException e) {
//            Log.e("nono", "onAttach: ClassCastException: " + e.getMessage());
//        }
    }


}