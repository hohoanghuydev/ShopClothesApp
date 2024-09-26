package com.example.shopclothesapp.ui.login;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Users;
import com.example.shopclothesapp.data.repositories.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<Users> userLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> stateSendEmail = new MutableLiveData<>();

    public LoginViewModel(Context context) {
        this.userRepository = new UserRepository(context.getApplicationContext());
    }

    public void loginAccount(String email, String password) {
        userRepository.signInAccount(email, password, userLiveData);
    }

    public void getPasswordForgot(String email) {
        userRepository.changePasswordForgot(email, stateSendEmail);
    }

    public LiveData<Users> getUserLiveData() {
        return userLiveData;
    }

    public void saveDataLogin(Users user) {
        userRepository.saveUserLogin(user);
    }

    public LiveData<Boolean> getStateSendEmail() {
        return stateSendEmail;
    }
}
