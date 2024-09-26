package com.example.shopclothesapp.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.ui.signup.SignupViewModel;

/** @noinspection unchecked*/
public class SignupViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public SignupViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SignupViewModel.class)) {
            return (T) new SignupViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
