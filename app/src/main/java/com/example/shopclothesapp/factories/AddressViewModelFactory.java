package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.ui.address.AddressViewModel;

/** @noinspection unchecked*/
public class AddressViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public AddressViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AddressViewModel.class)) {
            return (T) new AddressViewModel(context);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
