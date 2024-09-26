package com.example.shopclothesapp.ui.address.add;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Address;
import com.example.shopclothesapp.data.repositories.UserRepository;


public class AddAddressViewModel extends ViewModel {
    MutableLiveData<Address> addressLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> stateUpdate = new MutableLiveData<>();
    UserRepository userRepository;

    public AddAddressViewModel(Context context, Address address) {
        userRepository = new UserRepository(context.getApplicationContext());

        if(address != null) {
            addressLiveData.setValue(address);
        }
    }

    public LiveData<Address> getAddressLiveData() {
        return addressLiveData;
    }

    public LiveData<Boolean> getStateUpdate() {
        return stateUpdate;
    }

    public void updateAddress(Address address) {
        if(address == null) {
            return;
        }

        if (address.getAddressId() == null) {
            address.setAddressId(address.hashCode() + "");
        }

        userRepository.updateAddressShipping(address, stateUpdate);
    }
}
