package com.example.shopclothesapp.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.adapters.CategoryAdapter;
import com.example.shopclothesapp.databinding.ActivityCategoryBinding;
import com.example.shopclothesapp.ui.detailcategory.DetailCategoryActivity;
import com.example.shopclothesapp.utils.TypeViewHolder;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding categoryBinding;
    CategoryViewModel categoryViewModel;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUpDataBinding();
        setUpRecyclerViewCategories();
        setUpObservers();
        addEvents();
    }

    private void setUpRecyclerViewCategories() {
        RecyclerView rvCategory = categoryBinding.layoutCategory.findViewById(R.id.rvCategory);
        categoryAdapter = new CategoryAdapter(TypeViewHolder.MEDIUM_VERTICAL);
        categoryAdapter.setItemClickListener(category -> {
            Intent intentToDetailCategory= new Intent(CategoryActivity.this, DetailCategoryActivity.class);
            intentToDetailCategory.putExtra("category", category);
            startActivity(intentToDetailCategory);
        });
        rvCategory.setAdapter(categoryAdapter);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpObservers() {
        categoryViewModel.getCategoriesLiveData().observe(this, categories -> categoryAdapter.submitList(categories));
    }

    private void addEvents() {
        categoryBinding.btnBackCategory.setOnClickListener(viewButtonBack -> finish());
    }

    private void setUpDataBinding() {
        categoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryBinding.setCategoryViewModel(categoryViewModel);
        categoryBinding.executePendingBindings();
    }
}