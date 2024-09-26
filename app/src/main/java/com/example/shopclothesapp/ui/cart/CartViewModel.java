package com.example.shopclothesapp.ui.cart;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.shopclothesapp.data.models.ProductCart;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.data.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private MutableLiveData<List<ProductCart>> cartsLiveData = new MutableLiveData<>();
    private List<ProductCart> carts = new ArrayList<>();
    private List<Products> products = new ArrayList<>();
    private final UserRepository userRepository;
    private MutableLiveData<List<Products>> productsLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> totalAmount = new MutableLiveData<>(Long.valueOf(0));
    private long total = 0;

    private MutableLiveData<Integer> itemCountLiveData = new MutableLiveData<>(0);
    private Integer itemCount = 0;

    public CartViewModel(Context context) {
        this.userRepository = new UserRepository(context.getApplicationContext());

        this.userRepository.getProductsOrder(cartsLiveData);
    }

    public LiveData<Integer> getItemCountLiveData() {
        return itemCountLiveData;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
        itemCountLiveData.setValue(itemCount);
    }

    public LiveData<Long> getTotalAmount() {
        return totalAmount;
    }

    public void setTotal(long total) {
        this.total = total;
        totalAmount.setValue(total);
    }

    public LiveData<List<ProductCart>> getCartsLiveData() {
        return cartsLiveData;
    }

    public void setCarts(List<ProductCart> carts) {
        this.carts = carts;
        cartsLiveData.setValue(carts);
    }
}
