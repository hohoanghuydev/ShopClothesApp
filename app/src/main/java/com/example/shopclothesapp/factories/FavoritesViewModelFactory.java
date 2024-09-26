package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.ui.favorite.FavoritesViewModel;


/** @noinspection unchecked*/
public class FavoritesViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public FavoritesViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(FavoritesViewModel.class)) {
            return (T) new FavoritesViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
