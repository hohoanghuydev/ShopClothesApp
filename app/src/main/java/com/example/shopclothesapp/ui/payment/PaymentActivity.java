package com.example.shopclothesapp.ui.payment;

import android.content.Intent;
import android.os.Bundle;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        findViewById(R.id.tvUpdatePayment).setOnClickListener(viewControl -> {
            startActivity(new Intent(PaymentActivity.this, EditPaymentActivity.class));
        });

        findViewById(R.id.fabAddPayment).setOnClickListener(viewControl -> {
            startActivity(new Intent(PaymentActivity.this, AddPaymentActivity.class));
        });
    }
}