package com.example.shopclothesapp.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.adapters.CategoryAdapter;
import com.example.shopclothesapp.adapters.ProductsAdapter;
import com.example.shopclothesapp.databinding.ActivitySearchBinding;
import com.example.shopclothesapp.ui.detail.DetailActivity;
import com.example.shopclothesapp.ui.detailcategory.DetailCategoryActivity;
import com.example.shopclothesapp.utils.TypeViewHolder;

public class SearchActivity extends AppCompatActivity {

    SearchViewModel searchViewModel;
    ActivitySearchBinding searchBinding;
    CategoryAdapter categoryAdapter;
    ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUpDataBinding();
        setUpRecyclerViewProducts();
        setUpRecyclerViewCategories();
        setUpObservers();
        addEvents();
    }

    private void addEvents() {
        searchBinding.searchViewProductFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    searchBinding.recyclerViewProductsHome.setVisibility(View.GONE);
                    searchBinding.tvEmptyProducts.setVisibility(View.GONE);
                    searchBinding.layoutCategory.setVisibility(View.VISIBLE);
                } else {
                    productsAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        searchBinding.btnBackSearch.setOnClickListener(viewButtonBack -> finish());
    }

    private void setUpObservers() {
        searchViewModel.getCategoriesLiveData().observe(this, categories -> categoryAdapter.submitList(categories));

        searchViewModel.getProductsLiveData().observe(this, products -> productsAdapter.submitProducts(products));
    }

    private void setUpRecyclerViewCategories() {
        categoryAdapter = new CategoryAdapter(TypeViewHolder.MEDIUM_VERTICAL);
        RecyclerView rvCategory = searchBinding.layoutCategory.findViewById(R.id.rvCategory);
        rvCategory.setAdapter(categoryAdapter);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));

        categoryAdapter.setItemClickListener(category -> {
            Intent intentToDetailCategory= new Intent(SearchActivity.this, DetailCategoryActivity.class);
            intentToDetailCategory.putExtra("category", category);
            startActivity(intentToDetailCategory);
        });
    }

    private void setUpRecyclerViewProducts() {
        productsAdapter = new ProductsAdapter(product -> {
            Intent intentToDetail = new Intent(SearchActivity.this, DetailActivity.class);
            intentToDetail.putExtra("product", product);
            startActivity(intentToDetail);
        });

        searchBinding.recyclerViewProductsHome.setAdapter(productsAdapter);
        searchBinding.recyclerViewProductsHome.setLayoutManager(new GridLayoutManager(this, 2));

        productsAdapter.setFilterCallback(products -> {
            if (products.isEmpty()) {
                searchBinding.recyclerViewProductsHome.setVisibility(View.GONE);
                searchBinding.tvEmptyProducts.setVisibility(View.VISIBLE);
                searchBinding.layoutCategory.setVisibility(View.GONE);
            } else {
                searchBinding.recyclerViewProductsHome.setVisibility(View.VISIBLE);
                searchBinding.tvEmptyProducts.setVisibility(View.GONE);
                searchBinding.layoutCategory.setVisibility(View.GONE);
            }
        });
    }

    private void setUpDataBinding() {
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchBinding.setSearchViewModel(searchViewModel);
        searchBinding.executePendingBindings();
    }
}