package com.example.shopclothesapp.ui.detailcategory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.shopclothesapp.data.models.Category;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.data.repositories.ProductsRepository;

import java.util.List;

public class DetailCategoryViewModel extends ViewModel {
    private final MutableLiveData<List<Products>> productsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> titleCategory = new MutableLiveData<>();
    private final ProductsRepository productsRepository;

    public DetailCategoryViewModel(Category category) {
        productsRepository = new ProductsRepository();
        productsRepository.filterProductsByCategory(category.getCategoryId(), productsLiveData);
        titleCategory.setValue(category.getCategoryName());
    }

    public LiveData<List<Products>> getProductsLiveData() {
        return productsLiveData;
    }

    public LiveData<String> getTitleCategory() {
        return titleCategory;
    }
}
