package com.example.shopclothesapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.ui.cart.CartFragment;
import com.example.shopclothesapp.ui.favorite.FavoriteFragment;
import com.example.shopclothesapp.ui.home.HomeFragment;
import com.example.shopclothesapp.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScreenMainFragment extends Fragment {

    BottomNavigationView bottomNavigationMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addControls(view);
        loadFragment(new HomeFragment());
        addActions();
    }

    private void addControls(View view) {
        bottomNavigationMenu = view.findViewById(R.id.bottomNavigationMenu);
    }

    private void addActions() {
        bottomNavigationMenu.setOnItemSelectedListener(item -> {
            int idItemMenu = item.getItemId();

            if (idItemMenu == R.id.itemHome) {
                loadFragment(new HomeFragment());
            } else if (idItemMenu == R.id.itemFavorites) {
                loadFragment(new FavoriteFragment());
            } else if (idItemMenu == R.id.itemCart) {
                loadFragment(new CartFragment());
            } else if (idItemMenu == R.id.itemProfile) {
                loadFragment(new ProfileFragment());
            } else {
                return false;
            }

            return true;
        });
    }

    private void loadFragment(Fragment currentFragment) {
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutFragments, currentFragment).commit();
    }
}