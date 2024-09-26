package com.example.shopclothesapp.ui.detail;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.ProductCart;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.data.repositories.UserRepository;

public class ProductViewModel extends ViewModel {
    private final MutableLiveData<Products> productLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> flagFavoriteLiveData = new MutableLiveData<>();
    private Boolean flagFavorite = false;

    private MutableLiveData<String> quantityLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> stateOrder = new MutableLiveData<>();

    UserRepository userRepository;
    String currentUser;

    public ProductViewModel(Products product, Context context) {
        this.userRepository = new UserRepository(context.getApplicationContext());
        currentUser = userRepository.getUserLogin("uid");
        productLiveData.setValue(product);
        userRepository.getStateFavorite(currentUser, product.getProductId(), flagFavoriteLiveData);
        quantityLiveData.setValue("1");
    }

    public LiveData<Boolean> getStateOrder() {
        return stateOrder;
    }

    public LiveData<Products> getProductLiveData() {
        return productLiveData;
    }

    public LiveData<Boolean> getFlagFavoriteLiveData() {
        return flagFavoriteLiveData;
    }

    public MutableLiveData<String> getQuantityLiveData() {
        return quantityLiveData;
    }

    public void setFlagFavorite(Boolean flagFavorite) {
        this.flagFavorite = flagFavorite;
        flagFavoriteLiveData.setValue(flagFavorite);
    }

    public Boolean getFlagFavorite() {
        return flagFavoriteLiveData.getValue();
    }

    public void addQuantity(View view) {
        int number = Integer.parseInt(quantityLiveData.getValue());
        number++;
        quantityLiveData.setValue(String.valueOf(number));
    }

    public void subtractQuantity(View view) {
        int number = Integer.parseInt(quantityLiveData.getValue());
        number--;

        if(number == 0) {
            number = 1;
        }
        quantityLiveData.setValue(String.valueOf(number));
    }

    public void addToCart(String size) {
        String productId = productLiveData.getValue().getProductId();
        int quantity = Integer.parseInt(quantityLiveData.getValue());
        ProductCart cart = new ProductCart(productId, quantity, size);
        userRepository.addToCart(currentUser, cart, stateOrder);
    }

    public void addToFavorite() {
        userRepository.addToFavorite(currentUser, productLiveData.getValue().getProductId());
    }

    public void removeFromFavorite() {
        userRepository.removeFromFavorite(currentUser, productLiveData.getValue().getProductId());
    }
}
