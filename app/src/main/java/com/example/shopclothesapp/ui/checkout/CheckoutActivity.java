package com.example.shopclothesapp.ui.checkout;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.data.models.Orders;
import com.example.shopclothesapp.data.models.ProductCart;
import com.example.shopclothesapp.databinding.ActivityCheckoutBinding;
import com.example.shopclothesapp.factories.CheckoutViewModelFactory;

import java.sql.Date;
import java.util.List;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding checkoutBinding;
    CheckoutViewModel checkoutViewModel;
    Orders orders;
    List<ProductCart> productCarts;
    long totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();

        if(bundle == null) {
            Log.e("Bundle error", "Intent is null");
            finish();
            return;
        }

        totalAmount = bundle.getLong("total", 0);
        productCarts = (List<ProductCart>) bundle.getSerializable("carts");

        if(productCarts == null) {
            Log.e("Cart", "Carts is null");
            finish();
            return;
        }

        setUpDataBinding();
        init();
        setupObserver();
        addEvents();
    }

    private void addEvents() {
        checkoutBinding.btnPaymentBack.setOnClickListener(view -> finish());

        checkoutBinding.btnConfirmCheckout.setOnClickListener(view -> {
            String phoneContact = checkoutBinding.edtPhoneNumberPayment.getText().toString();
            String addressShipping = checkoutBinding.edtShippingAddressPayment.getText().toString();

            if (TextUtils.isEmpty(phoneContact) || TextUtils.isEmpty(addressShipping)) {
                Toast.makeText(this, "Please enter address and phone number for delivery", Toast.LENGTH_SHORT).show();
                return;
            }

            checkoutViewModel.checkoutOrder();
            startActivity(new Intent(CheckoutActivity.this, CheckoutSuccessActivity.class));
            finish();
        });
    }

    private void init() {
        Date date = new Date(System.currentTimeMillis());
        String orderId = date.getTime() + "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(date);
        String phoneNumber = checkoutViewModel.getPhoneContact();
        String address = checkoutViewModel.getAddress();

        if (phoneNumber != null) {
            checkoutBinding.edtPhoneNumberPayment.setText(phoneNumber);
        }

        if (address != null) {
            checkoutBinding.edtShippingAddressPayment.setText(address);
        }

        orders = new Orders(orderId, productCarts, "pending", totalAmount, formattedDate, phoneNumber, address);
        checkoutViewModel.updateInfoPayment(orders);
    }

    private void setupObserver() {
        checkoutViewModel.getStateCheckout().observe(this, stateCheckout -> {
            if(stateCheckout) {
                checkoutViewModel.clearCart();
            } else {
                Toast.makeText(this, "Payment failure", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setUpDataBinding() {
        checkoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        CheckoutViewModelFactory factory = new CheckoutViewModelFactory(this);
        checkoutViewModel = new ViewModelProvider(this, factory).get(CheckoutViewModel.class);
        checkoutBinding.setCheckoutViewModel(checkoutViewModel);
        checkoutBinding.setLifecycleOwner(this);
        checkoutBinding.executePendingBindings();
    }
}