package com.example.shopclothesapp.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.ui.payment.add.AddPaymentActivity;
import com.example.shopclothesapp.ui.payment.edit.EditPaymentActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment);

        findViewById(R.id.tvUpdatePayment).setOnClickListener(viewControl -> {
            startActivity(new Intent(PaymentActivity.this, EditPaymentActivity.class));
        });

        findViewById(R.id.fabAddPayment).setOnClickListener(viewControl -> {
            startActivity(new Intent(PaymentActivity.this, AddPaymentActivity.class));
        });
    }
}