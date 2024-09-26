package com.example.shopclothesapp.ui.profile;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Users;
import com.example.shopclothesapp.data.repositories.UserRepository;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<Users> usersLiveData = new MutableLiveData<>();
    private final UserRepository userRepository;

    public ProfileViewModel(Context context) {
        userRepository = new UserRepository(context.getApplicationContext());
        userRepository.getInfoUser(usersLiveData);

        Users user = new Users();
        user.setFullname(userRepository.getUserLogin("name"));
        user.setEmail(userRepository.getUserLogin("email"));

        usersLiveData.setValue(user);
    }

    public LiveData<Users> getUsersLiveData() {
        return usersLiveData;
    }

    public void signOut() {
        userRepository.signOutAccount();
        userRepository.deleteUserSignOut();
    }
}
