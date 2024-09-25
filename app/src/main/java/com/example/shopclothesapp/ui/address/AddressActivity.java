package com.example.shopclothesapp.ui.address;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.ui.address.add.AddAddressActivity;
import com.example.shopclothesapp.ui.address.edit.EditAddressActivity;

public class AddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        this.findViewById(R.id.fabAddAddress).setOnClickListener(viewControl -> {
            startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
        });

        this.findViewById(R.id.tvUpdateAddress).setOnClickListener(viewControl -> {
            startActivity(new Intent(AddressActivity.this, EditAddressActivity.class));
        });

        this.findViewById(R.id.tvUpdateAddress).setOnLongClickListener(viewControl -> {
            //Hieen thi dialog xoa
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}