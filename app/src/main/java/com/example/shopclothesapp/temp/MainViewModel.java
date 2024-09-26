package com.example.shopclothesapp.temp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Users;
import com.example.shopclothesapp.data.repositories.UserRepository;


public class MainViewModel extends ViewModel {
    private final MutableLiveData<Users> userLiveData = new MutableLiveData<>();
    private final Users user = new Users();
    private final UserRepository userRepository;

    public MainViewModel(Context context) {
        this.userRepository = new UserRepository(context.getApplicationContext());
    }

    public LiveData<Users> getUserLiveData() {
        return userLiveData;
    }

    public void getInfoUser() {
        String email = userRepository.getUserLogin("email");
        String name = userRepository.getUserLogin("name");
        user.setEmail(email);
        user.setFullname(name);
        userLiveData.setValue(user);
    }
}
