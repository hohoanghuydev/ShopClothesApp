package com.example.shopclothesapp.ui.detailcategory;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.adapters.ProductsAdapter;
import com.example.shopclothesapp.data.models.Category;
import com.example.shopclothesapp.databinding.ActivityDetailCategoryBinding;
import com.example.shopclothesapp.factories.DetailCategoryViewModelFactory;
import com.example.shopclothesapp.ui.detail.DetailActivity;

public class DetailCategoryActivity extends AppCompatActivity {

    DetailCategoryViewModel detailCategoryViewModel;
    ActivityDetailCategoryBinding detailCategoryBinding;
    ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Category category = getIntent().getParcelableExtra("category");

        if(category == null) {
            finish();
        }

        setUpDataBinding(category);
        setUpRecyclerViewProducts();
        setUpObservers();
        addEvents();
    }

    private void setUpDataBinding(Category category) {
        detailCategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_category);
        DetailCategoryViewModelFactory factory = new DetailCategoryViewModelFactory(category);
        detailCategoryViewModel = new ViewModelProvider(this, factory).get(DetailCategoryViewModel.class);
        detailCategoryBinding.setDetailCategoryViewModel(detailCategoryViewModel);
        detailCategoryBinding.executePendingBindings();
    }

    private void setUpRecyclerViewProducts() {
        productsAdapter = new ProductsAdapter(product -> {
            Intent intentToDetail = new Intent(this, DetailActivity.class);
            intentToDetail.putExtra("product", product);
            startActivity(intentToDetail);
        });
        detailCategoryBinding.rvProductOfCategory.setAdapter(productsAdapter);
        detailCategoryBinding.rvProductOfCategory.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void addEvents() {
        detailCategoryBinding.btnBackDetailCategory.setOnClickListener(viewButton -> finish());
    }

    private void setUpObservers() {
        detailCategoryViewModel.getProductsLiveData().observe(this, products -> productsAdapter.submitProducts(products));
    }
}