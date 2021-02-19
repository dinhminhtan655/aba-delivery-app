package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.tandm.abadeliverydriver.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowFullImageActivity extends AppCompatActivity {

    @BindView(R.id.imgFull)
    ImageView imgFull;

    @BindView(R.id.imgBack)
    ImageView imgBack;
    Uri imageFee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_image);
        ButterKnife.bind(this);
        Bundle intent = getIntent().getExtras();
        imageFee = Uri.parse(intent.getString("imagefee"));

        imgFull.setImageURI(imageFee);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
