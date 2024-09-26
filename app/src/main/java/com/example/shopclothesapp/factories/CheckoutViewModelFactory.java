package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.ui.checkout.CheckoutViewModel;

/** @noinspection unchecked*/
public class CheckoutViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public CheckoutViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CheckoutViewModel.class)) {
            return (T) new CheckoutViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
