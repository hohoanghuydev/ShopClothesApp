package com.example.shopclothesapp.ui.address.edit;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopclothesapp.R;

public class EditAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_address);

        findViewById(R.id.btnUpdateAddress).setOnClickListener(viewControl -> {
            finish();
        });

        findViewById(R.id.btnBackEditAddress).setOnClickListener(viewControl -> {
            finish();
        });
    }
}