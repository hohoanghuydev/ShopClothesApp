package com.example.shopclothesapp.ui.address;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Address;
import com.example.shopclothesapp.data.repositories.UserRepository;

import java.util.List;

public class AddressViewModel extends ViewModel {
    private final MutableLiveData<List<Address>> addressLiveData = new MutableLiveData<>();
    private final UserRepository userRepository;

    public AddressViewModel(Context context) {
        userRepository = new UserRepository(context.getApplicationContext());
        userRepository.getAddressShipping(addressLiveData);
    }

    public LiveData<List<Address>> getAddressLiveData() {
        return addressLiveData;
    }
}
