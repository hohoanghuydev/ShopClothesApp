package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.data.models.Users;
import com.example.shopclothesapp.temp.EditAccountViewModel;


/** @noinspection unchecked*/
public class EditAccountViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    private final Users user;

    public EditAccountViewModelFactory(Context context, Users user) {
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(EditAccountViewModel.class)) {
            return (T) new EditAccountViewModel(context, user);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
