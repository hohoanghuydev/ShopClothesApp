package com.example.shopclothesapp.temp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Orders;
import com.example.shopclothesapp.data.repositories.UserRepository;

public class PaymentViewModel extends ViewModel {
    private final MutableLiveData<Orders> ordersLiveData = new MutableLiveData<>();
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> stateCheckout = new MutableLiveData<>();

    public PaymentViewModel(Context context) {
        this.userRepository = new UserRepository(context.getApplicationContext());
    }

    public void updateInfoPayment(Orders order) {
        ordersLiveData.setValue(order);
    }

    public void checkoutOrder() {
        Orders order = ordersLiveData.getValue();

        if (order != null) {
            String currentUser = userRepository.getUserLogin("uid");
            userRepository.checkoutOrder(currentUser, order, stateCheckout);
        }
    }

    public LiveData<Boolean> getStateCheckout() {
        return stateCheckout;
    }

    public LiveData<Orders> getOrdersLiveData() {
        return ordersLiveData;
    }

    public void clearCart() {
        String currentUser = userRepository.getUserLogin("uid");
        userRepository.removeAllProductCart(currentUser);
    }

    public String getAddress() {
        return userRepository.getUserLogin("address");
    }

    public String getPhoneContact() {
        return userRepository.getUserLogin("phone");
    }
}
