package com.example.shopclothesapp.ui.signup;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Users;
import com.example.shopclothesapp.data.repositories.UserRepository;

public class SignupViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<Users> userLiveData = new MutableLiveData<>();

    public SignupViewModel(Context context) {
        this.userRepository = new UserRepository(context);
    }

    public void signUpAccount(String email, String password, String name) {
        userRepository.signUpAccount(email, password, name, userLiveData);
    }

    public LiveData<Users> getUserLiveData() {
        return userLiveData;
    }

    public void addInfoUser(Users user) {
        userRepository.addProfileUser(user);
    }
}
