package com.example.shopclothesapp.ui.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.shopclothesapp.data.models.Category;
import com.example.shopclothesapp.data.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private final MutableLiveData<List<Category>> categoriesLiveData = new MutableLiveData<>();
    private final CategoryRepository categoryRepository;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();

        categoryRepository.getAllCategory(categoriesLiveData);
    }

    public LiveData<List<Category>> getCategoriesLiveData() {
        return categoriesLiveData;
    }
}
