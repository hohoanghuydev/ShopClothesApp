package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.data.models.Address;
import com.example.shopclothesapp.ui.address.add.AddAddressViewModel;


/** @noinspection unchecked*/
public class AddAddressViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final Address address;

    public AddAddressViewModelFactory(Context context, Address address) {
        this.context = context;
        this.address = address;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AddAddressViewModel.class)) {
            return (T) new AddAddressViewModel(context, address);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
