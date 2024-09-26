package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.ui.detail.ProductViewModel;


/** @noinspection unchecked*/
public class ProductViewModelFactory implements ViewModelProvider.Factory {
    private final Products product;
    private final Context context;

    public ProductViewModelFactory(Products product, Context context) {
        this.product = product;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ProductViewModel.class)) {
            return (T) new ProductViewModel(product, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
