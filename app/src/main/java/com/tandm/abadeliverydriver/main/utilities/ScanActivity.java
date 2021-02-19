package com.tandm.abadeliverydriver.main.utilities;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.MainActivity;

public class ScanActivity extends AppCompatActivity implements ScanListener {


    private TextView tvPreStoreCode;
    private TextInputEditText edtStoreID;

    private View btnQRCode;

    private boolean isSetupScan;
    public boolean isClickedFromCamera;


    public void setUpScan() {
        isSetupScan = true;
        btnQRCode = findViewById(R.id.btnQRCode);
        tvPreStoreCode = findViewById(R.id.tvPreStoreCode);
        edtStoreID = findViewById(R.id.edtStoreID);
        edtStoreID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String contents = s.toString();
                if (contents.contains("\n")) {
                    onData(contents.replaceAll("\n", "").trim());
                }
            }
        });

        btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ScanActivity.this);
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setCaptureActivity(ScanCameraPortrait.class);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (requestCode == IntentIntegrator.REQUEST_CODE && isSetupScan) {
            if (resultCode == RESULT_OK) {
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                String data = result.getContents();
                onData(data.trim());
                isClickedFromCamera = true;
            } else {
                isClickedFromCamera = false;
            }
        }
    }

    @Override
    public void onData(String data) {
        if (edtStoreID != null) {
            edtStoreID.setText("");
            edtStoreID.requestFocus();
        }
        if (tvPreStoreCode != null) {
            tvPreStoreCode.setText(data);
        }
    }
}
