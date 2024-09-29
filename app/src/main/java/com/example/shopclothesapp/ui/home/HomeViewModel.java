package com.example.shopclothesapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.shopclothesapp.data.models.Category;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.data.repositories.CategoryRepository;
import com.example.shopclothesapp.data.repositories.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Products>> productsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private List<Products> products = new ArrayList<>();
    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;

    public HomeViewModel() {
        productsRepository = new ProductsRepository();
        categoryRepository = new CategoryRepository();

        categoryRepository.getAllCategory(categoriesLiveData);

        productsRepository.getAllProduct(productsLiveData);
    }

    public LiveData<List<Products>> getProductsLiveData() {
        return productsLiveData;
    }

    public LiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }
}
