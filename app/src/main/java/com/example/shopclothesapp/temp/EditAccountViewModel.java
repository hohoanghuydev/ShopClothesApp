package com.example.shopclothesapp.temp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Users;
import com.example.shopclothesapp.data.repositories.UserRepository;


public class EditAccountViewModel extends ViewModel {
    MutableLiveData<Users> usersMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> stateUpdate = new MutableLiveData<>();
    UserRepository userRepository;

    public EditAccountViewModel(Context context, Users user) {
        userRepository = new UserRepository(context);
        usersMutableLiveData.setValue(user);
    }

    public LiveData<Users> getUsersMutableLiveData() {
        return usersMutableLiveData;
    }

    public LiveData<Boolean> getStateUpdate() {
        return stateUpdate;
    }

    public void updateProfile() {
        Users user = usersMutableLiveData.getValue();

        if(user != null) {
            userRepository.updateProfile(user, stateUpdate);
        }
    }
}
