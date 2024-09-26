package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.temp.PaymentViewModel;


/** @noinspection unchecked*/
public class PaymentViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public PaymentViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PaymentViewModel.class)) {
            return (T) new PaymentViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
