package com.example.shopclothesapp.ui.favorite;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.adapters.ProductsAdapter;
import com.example.shopclothesapp.databinding.FragmentFavoriteBinding;
import com.example.shopclothesapp.factories.FavoritesViewModelFactory;
import com.example.shopclothesapp.ui.detail.DetailActivity;

public class FavoriteFragment extends Fragment {

    FragmentFavoriteBinding favoriteBinding;
    FavoritesViewModel favoriteViewModel;
    ProductsAdapter productsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        favoriteBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        FavoritesViewModelFactory factory = new FavoritesViewModelFactory(getContext());
        favoriteViewModel = new ViewModelProvider(this, factory).get(FavoritesViewModel.class);
        favoriteBinding.setFavoriteViewModel(favoriteViewModel);

        return favoriteBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addEvents();
        setUpRecyclerViewFavorites();
        setUpObservers();
    }

    private void addEvents() {
        productsAdapter = new ProductsAdapter(product -> {
            Intent intentToDetail = new Intent(getActivity(), DetailActivity.class);
            intentToDetail.putExtra("product", product);
            startActivity(intentToDetail);
        });
    }

    private void setUpObservers() {
        favoriteViewModel.getFavoritesLiveData().observe(getViewLifecycleOwner(), productFavorites -> favoriteViewModel.getByProductId(productFavorites));

        favoriteViewModel.getProductFavoritesLiveData().observe(getViewLifecycleOwner(), products -> productsAdapter.submitList(products));
    }

    private void setUpRecyclerViewFavorites() {
        favoriteBinding.rvProductFavorites.setAdapter(productsAdapter);
        favoriteBinding.rvProductFavorites.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}