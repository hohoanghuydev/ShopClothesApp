package com.example.shopclothesapp.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopclothesapp.data.models.Category;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.data.repositories.CategoryRepository;
import com.example.shopclothesapp.data.repositories.ProductsRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Products>> productsLiveData = new MutableLiveData<>();
    private final CategoryRepository categoryRepository;
    private final ProductsRepository productsRepository;

    public SearchViewModel() {
        categoryRepository = new CategoryRepository();
        productsRepository = new ProductsRepository();

        categoryRepository.getAllCategory(categoriesLiveData);
        productsRepository.getAllProduct(productsLiveData);
    }

    public LiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public LiveData<List<Products>> getProductsLiveData() {
        return productsLiveData;
    }
}
