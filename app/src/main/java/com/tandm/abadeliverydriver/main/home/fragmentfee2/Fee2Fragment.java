package com.tandm.abadeliverydriver.main.home.fragmentfee2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.AdvancePaymentActivity;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.feedbackbox.FeedbackBoxActivity;
import com.tandm.abadeliverydriver.main.home.fragmentfee2.paymentorder.PaymentOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fee2Fragment extends Fragment {

    private static final String TAG = "Fee2Fragment";

    private Unbinder unbinder;
    
    @BindView(R.id.imgTamUng)
    ImageView imgTamUng;
    @BindView(R.id.imgThanhToan)
    ImageView imgThanhToan;

    View view;

    public Fee2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fee2, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }



    @OnClick(R.id.imgTamUng)
    public void goToTamUng(View view){
        startActivity(new Intent(getContext(), AdvancePaymentActivity.class));
    }

    @OnClick(R.id.imgThanhToan)
    public void gotoThanhToan(View view){
        startActivity(new Intent(getContext(), PaymentOrderActivity.class));
    }

    @OnClick(R.id.imgFeedback)
    public void gotoGopY(View view){
        startActivity(new Intent(getContext(), FeedbackBoxActivity.class));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
