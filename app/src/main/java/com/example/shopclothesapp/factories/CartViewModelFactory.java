package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.ui.cart.CartViewModel;


/** @noinspection unchecked*/
public class CartViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public CartViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CartViewModel.class)) {
            return (T) new CartViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
