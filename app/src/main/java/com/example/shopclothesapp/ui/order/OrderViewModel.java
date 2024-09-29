package com.example.shopclothesapp.ui.order;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Orders;
import com.example.shopclothesapp.data.repositories.UserRepository;

import java.util.List;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<List<Orders>> ordersLiveData = new MutableLiveData<>();
    private UserRepository userRepository;

    public OrderViewModel(Context context) {
        userRepository = new UserRepository(context);
        String currentUser = userRepository.getUserLogin("uid");
        userRepository.getOrderOfUser(currentUser, ordersLiveData);
    }

    public LiveData<List<Orders>> getOrdersLiveData() {
        return ordersLiveData;
    }
}
