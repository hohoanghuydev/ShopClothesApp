package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.temp.OrderViewModel;


/** @noinspection unchecked*/
public class OrderViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public OrderViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(OrderViewModel.class)) {
            return (T) new OrderViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
