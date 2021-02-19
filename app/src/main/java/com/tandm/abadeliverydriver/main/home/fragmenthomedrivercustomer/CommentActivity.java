package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofitWMS;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.adapter.CommentAdapter;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.Comment;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    private static final String TAG = "CommentActivity";

    @BindView(R.id.tvSubject)
    TextView tvSubject;
    @BindView(R.id.ivSendComment)
    ImageView ivSendComment;
    @BindView(R.id.etComment)
    EditText etComment;
    @BindView(R.id.rvOrderDetail)
    RecyclerView rvOrderDetail;

    String strOrderNumber, strUserName, strComment = "", strFlag;

    private ProgressDialog progressDialog;

    CommentAdapter commentAdapter;

    public static int iOk = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        strUserName = LoginPrefer.getObject(this).userName;

        if (getIntent() != null) {
            strOrderNumber = getIntent().getStringExtra("ordernumber");
            tvSubject.setText(strOrderNumber);
        }
        getComment(rvOrderDetail, "5");

    }


    public void getComment(final View view, String strFlag) {

        progressDialog = Utilities.getProgressDialog(CommentActivity.this, "Đang gửi bình luận...");
        progressDialog.show();

        if (!WifiHelper.isConnected(CommentActivity.this)) {
            RetrofitError.errorAction(CommentActivity.this, new NoInternet(), TAG, view);
            progressDialog.dismiss();
            return;
        }

        MyRetrofitWMS.initRequest().getRatingComment(strOrderNumber, "0", strUserName, strComment, strFlag).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().size() > 0) {
                        iOk = 1;
                        commentAdapter = new CommentAdapter(response.body());
                        rvOrderDetail.setAdapter(commentAdapter);
                    } else {
                        iOk = 0;
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(CommentActivity.this, "Vui lòng kiểm tra kết nối Internet", Toast.LENGTH_SHORT).show();
                iOk = 0;
                progressDialog.dismiss();
            }
        });
    }


    @OnClick(R.id.ivSendComment)
    public void sendComment(View view) {
        if (etComment.getText().toString().length() == 0) {
            return;
        }
        Utilities.hideKeyboard(this);
        strComment = etComment.getText().toString();
        strFlag = "1";
        getComment(rvOrderDetail, strFlag);
        etComment.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.d("huhu","hihihi");
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra("extra",iOk);
//        setResult(Activity.RESULT_OK, resultIntent);
//        finish();
    }
}