package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.temp.MainViewModel;


/** @noinspection unchecked*/
public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public MainViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
