package com.example.shopclothesapp.ui.payment.edit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopclothesapp.R;

public class EditPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payment);

        findViewById(R.id.btnBackAddPayment).setOnClickListener(viewControl -> {
            finish();
        });
    }
}