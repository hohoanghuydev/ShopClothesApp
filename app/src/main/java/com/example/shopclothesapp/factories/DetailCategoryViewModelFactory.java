package com.example.shopclothesapp.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopclothesapp.data.models.Category;
import com.example.shopclothesapp.temp.DetailCategoryViewModel;

public class DetailCategoryViewModelFactory implements ViewModelProvider.Factory {
    private final Category category;

    public DetailCategoryViewModelFactory(Category category) {
        this.category = category;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(DetailCategoryViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailCategoryViewModel(category);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
