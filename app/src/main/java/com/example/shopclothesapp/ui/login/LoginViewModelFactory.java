package com.example.shopclothesapp.ui.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/** @noinspection unchecked*/
public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public LoginViewModelFactory(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
