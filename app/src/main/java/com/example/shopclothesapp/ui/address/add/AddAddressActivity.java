package com.example.shopclothesapp.ui.address.add;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopclothesapp.R;

public class AddAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_address);

        findViewById(R.id.btnSavePayment).setOnClickListener(viewControl -> {
            finish();
        });

        findViewById(R.id.btnBackAddPayment).setOnClickListener(viewControl -> {
            finish();
        });
    }
}