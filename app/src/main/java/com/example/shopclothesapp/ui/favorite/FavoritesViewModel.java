package com.example.shopclothesapp.ui.favorite;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.ProductFavorites;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.data.repositories.ProductsRepository;
import com.example.shopclothesapp.data.repositories.UserRepository;

import java.util.List;

public class FavoritesViewModel extends ViewModel {
    private final MutableLiveData<List<ProductFavorites>> favoritesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Products>> productFavoritesLiveData = new MutableLiveData<>();
    private final UserRepository userRepository;
    private final ProductsRepository productsRepository;
    private String currentUserId = "";

    public FavoritesViewModel(Context context) {
        userRepository = new UserRepository(context.getApplicationContext());
        productsRepository = new ProductsRepository();

        currentUserId = userRepository.getUserLogin("uid");
        userRepository.getProductFavorites(currentUserId, favoritesLiveData);
    }

    public LiveData<List<ProductFavorites>> getFavoritesLiveData() {
        return favoritesLiveData;
    }

    public LiveData<List<Products>> getProductFavoritesLiveData() {
        return productFavoritesLiveData;
    }

    public void removeProductFavorite() {

    }

    public void getByProductId(List<ProductFavorites> productFavorites) {
        productsRepository.getFavoriteItems(productFavorites, productFavoritesLiveData);
    }
}
