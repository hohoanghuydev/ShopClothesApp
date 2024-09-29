package com.example.shopclothesapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.adapters.CategoryAdapter;
import com.example.shopclothesapp.adapters.ProductsAdapter;
import com.example.shopclothesapp.data.models.Category;
import com.example.shopclothesapp.data.models.Products;
import com.example.shopclothesapp.databinding.FragmentHomeBinding;
import com.example.shopclothesapp.ui.category.CategoryActivity;
import com.example.shopclothesapp.ui.detail.DetailActivity;
import com.example.shopclothesapp.ui.detailcategory.DetailCategoryActivity;
import com.example.shopclothesapp.ui.search.SearchActivity;
import com.example.shopclothesapp.utils.TypeViewHolder;

import java.util.List;

public class HomeFragment extends Fragment implements ProductsAdapter.ItemProductClickListener {

    FragmentHomeBinding homeBinding;
    HomeViewModel homeViewModel;
    ProductsAdapter productsAdapter;
    CategoryAdapter categoryAdapterHome;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeBinding.setHomeViewModel(homeViewModel);
        homeBinding.setLifecycleOwner(getViewLifecycleOwner());

        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpObservers();
        setUpRecyclerViewProductsAndCategories();
        addEvents();
    }

    private void setUpRecyclerViewProductsAndCategories() {
        productsAdapter = new ProductsAdapter(this);
        productsAdapter.setFilterCallback(this::checkEmptyProduct);
        homeBinding.recyclerViewProductsHome.setAdapter(productsAdapter);
        homeBinding.recyclerViewProductsHome.setLayoutManager(new GridLayoutManager(getContext(), 2));

        categoryAdapterHome = new CategoryAdapter(TypeViewHolder.SMALL_HORIZONTAL);
        categoryAdapterHome.setItemClickListener(this::showProductsByCategory);
        homeBinding.rvCategoryHome.setAdapter(categoryAdapterHome);
        homeBinding.rvCategoryHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setUpObservers() {
        homeViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), products -> productsAdapter.submitProducts(products));
        homeViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> categoryAdapterHome.submitList(categories));
    }

    private void addEvents() {
        homeBinding.searchViewProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                productsAdapter.getFilter().filter(query);
                return true;
            }
        });

        homeBinding.tvSeeAllCategory.setOnClickListener(view -> startActivity(new Intent(getActivity(), CategoryActivity.class)));

        homeBinding.searchViewProduct.setOnClickListener(view -> startActivity(new Intent(getActivity(), SearchActivity.class)));
    }

    public void checkEmptyProduct(List<Products> products) {
        if(products.isEmpty()) {
            homeBinding.recyclerViewProductsHome.setVisibility(View.GONE);
            homeBinding.tvEmptyProducts.setVisibility(View.VISIBLE);
        } else {
            homeBinding.recyclerViewProductsHome.setVisibility(View.VISIBLE);
            homeBinding.tvEmptyProducts.setVisibility(View.GONE);
        }
    }

    @Override
    public void sendDataClick(Products product) {
        Intent intentToDetail = new Intent(getActivity(), DetailActivity.class);
        intentToDetail.putExtra("product", product);
        startActivity(intentToDetail);
    }

    private void showProductsByCategory(Category category) {
        Intent intentToDetailCategory= new Intent(getActivity(), DetailCategoryActivity.class);
        intentToDetailCategory.putExtra("category", category);
        startActivity(intentToDetailCategory);
    }
}