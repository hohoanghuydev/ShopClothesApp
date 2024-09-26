package com.example.shopclothesapp.ui.payment.edit;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopclothesapp.R;

public class EditPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_payment);

        findViewById(R.id.btnBackAddPayment).setOnClickListener(viewControl -> {
            finish();
        });
    }
}