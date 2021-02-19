package com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofitWMS;
import com.tandm.abadeliverydriver.main.api.NoInternet;
import com.tandm.abadeliverydriver.main.api.RetrofitError;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.CommentActivity;
import com.tandm.abadeliverydriver.main.home.fragmenthomedrivercustomer.model.EDI;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;
import com.tandm.abadeliverydriver.main.utilities.Utilities;
import com.tandm.abadeliverydriver.main.utilities.WifiHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EDIAdapter extends RecyclerView.Adapter<EDIAdapter.EDIViewHolder> {

    private static final String TAG = "EDIAdapter";
    private List<EDI> edis;
    private Runnable r;
    private Context context;
    private RecyclerView recyclerView;
    private Parcelable recyclerViewState;
    private ProgressDialog progressDialog;
    private int REQUEST_CODE;

    public EDIAdapter(List<EDI> edis, Context context, RecyclerView recyclerView, int REQUEST_CODE) {
        this.edis = edis;
        this.context = context;
        this.recyclerView = recyclerView;
        this.REQUEST_CODE = REQUEST_CODE;
    }

    @NonNull
    @Override
    public EDIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhap_xuat, parent, false);
        return new EDIAdapter.EDIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EDIViewHolder holder, int position) {
        Handler handler = new Handler();
        EDI edi = edis.get(holder.getAdapterPosition());
        holder.tvOrderType.setText(String.format("Loại đơn: %s", edi.eDI_OrderTypeDescription));
        holder.tvOrderNumber.setText(String.format("Mã đơn: %s", edi.orderNumber == null ? "" : edi.orderNumber));
        holder.tvOrderDate.setText(Utilities.formatDate_ddMMyyyy(edi.orderDate));
        holder.tvCustomer.setText(edi.customerReference.equals("") ? "Khách hàng: Không có" : String.format("Khách hàng: %s", edi.customerReference));
        holder.tvEDIOrderRemark.setText(String.format("Lưu ý: %s", edi.eDIOrderRemark));
        holder.tvStatusDescription.setText(String.format("Trạng thái: %s", edi.statusDescription));
        holder.tvTimeSlot.setText(String.format("Thời gian: %s", edi.timeSlot));
        holder.tvVehicleType.setText(String.format("Loại xe: %s", edi.vehicleType));
        holder.tvDockNumber.setText(String.format("Cổng: %s", edi.dockNumber));
        holder.tvTotalWeights.setText(String.format("Tổng khối lượng: %s", String.valueOf(edi.totalWeights)));
        holder.tvTotalQuantity.setText(String.format("Tổng số lượng: %s", String.valueOf(edi.totalQuantity)));
        holder.ratingBar.setRating(edi.ratingValue);
        holder.tvLastComment.setText(edi.comment);
        holder.tvLastCommentBy.setText(edi.commentBy);
        if (edi.comment.length() > 0) {
            holder.tvLastCommentBy.setVisibility(View.VISIBLE);
            holder.tvLastComment.setVisibility(View.VISIBLE);
        } else {
            holder.tvLastCommentBy.setVisibility(View.GONE);
            holder.tvLastComment.setVisibility(View.GONE);
        }
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

        holder.voHieuHoaRatingBar(position);


        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    if (r != null)
                        handler.removeCallbacks(r);
                    r = new Runnable() {
                        @Override
                        public void run() {
                            holder.updateRating(holder.ratingBar, edi, edi.phoneNumber, holder.tvOrderNumber.getText().toString().split(":")[1].trim(), rating);
                        }
                    };
                    handler.postDelayed(r, 2000);
                }
            }
        });


        holder.tvCommentClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("ordernumber", edi.orderNumber);
                ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
            }
        });

        if (edi.commentBy.length() > 0) {

        }

    }


    @Override
    public int getItemCount() {
        if (edis != null) {
            return edis.size();
        } else {
            return 0;
        }
    }

    public class EDIViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvOrderType)
        TextView tvOrderType;
        @BindView(R.id.tvOrderNumber)
        TextView tvOrderNumber;
        @BindView(R.id.tvOrderDate)
        TextView tvOrderDate;
        @BindView(R.id.tvCustomer)
        TextView tvCustomer;
        @BindView(R.id.tvEDIOrderRemark)
        TextView tvEDIOrderRemark;
        @BindView(R.id.tvStatusDescription)
        TextView tvStatusDescription;
        @BindView(R.id.tvTimeSlot)
        TextView tvTimeSlot;
        @BindView(R.id.tvVehicleType)
        TextView tvVehicleType;
        @BindView(R.id.tvDockNumber)
        TextView tvDockNumber;
        @BindView(R.id.tvTotalWeights)
        TextView tvTotalWeights;
        @BindView(R.id.tvTotalQuantity)
        TextView tvTotalQuantity;
        @BindView(R.id.tvPhoneNumber)
        TextView tvPhoneNumber;
        @BindView(R.id.tvTruckNumber)
        TextView tvTruckNumber;
        @BindView(R.id.tvCommentClick)
        TextView tvCommentClick;
        @BindView(R.id.tvLastCommentBy)
        TextView tvLastCommentBy;
        @BindView(R.id.tvLastComment)
        TextView tvLastComment;
        @BindView(R.id.ratingBar)
        AppCompatRatingBar ratingBar;

        public EDIViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void voHieuHoaRatingBar(int position) {
            if (edis.get(position).getRatingValue() > 0) {
                ratingBar.setIsIndicator(true);
            } else {
                ratingBar.setIsIndicator(false);
            }
        }


        private void updateRating(final View view, EDI edi, String phoneNumber, String orderNumber, float rating) {

            progressDialog = Utilities.getProgressDialog(context, "Đang gửi đánh giá...");
            progressDialog.show();

            phoneNumber = LoginPrefer.getObject(context).userName;

            if (!WifiHelper.isConnected(context)) {
                RetrofitError.errorAction(context, new NoInternet(), TAG, view);
                progressDialog.dismiss();
                return;
            }

            MyRetrofitWMS.initRequest().insertRatingComment(phoneNumber, orderNumber, rating).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().equals('"' + "OK" + '"')) {
                            edi.ratingValue = rating;
                            edi.setRatingValue(rating);
                            notifyDataSetChanged();
//                        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                            Toast.makeText(context, "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Vui lòng kiểm tra kết nối Internet!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
