package com.example.shopclothesapp.ui.cart;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopclothesapp.R;
import com.example.shopclothesapp.adapters.CartAdapter;
import com.example.shopclothesapp.data.models.CartItem;
import com.example.shopclothesapp.data.models.ProductCart;
import com.example.shopclothesapp.data.repositories.ProductsRepository;
import com.example.shopclothesapp.databinding.FragmentCartBinding;
import com.example.shopclothesapp.factories.CartViewModelFactory;
import com.example.shopclothesapp.ui.checkout.CheckoutActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    FragmentCartBinding cartBinding;
    CartViewModel cartViewModel;
    ProductsRepository productsRepository;
    CartAdapter cartAdapter;
    List<ProductCart> carts = new ArrayList<>();
    long totalBill = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        CartViewModelFactory factory = new CartViewModelFactory(getContext());
        cartViewModel = new ViewModelProvider(this, factory).get(CartViewModel.class);
        cartBinding.setCartViewModel(cartViewModel);
        cartBinding.executePendingBindings();
        productsRepository = new ProductsRepository();

        return cartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerViewCart();
        setUpObservers();
        addEvents();
    }

    private void setUpObservers() {
        cartViewModel.getItemCountLiveData().observe(getViewLifecycleOwner(), itemCart -> {
            if (itemCart == 0) {
                cartBinding.tvCartEmpty.setVisibility(View.VISIBLE);
                cartBinding.layoutCart.setVisibility(View.GONE);
            } else {
                cartBinding.tvCartEmpty.setVisibility(View.GONE);
                cartBinding.layoutCart.setVisibility(View.VISIBLE);
            }
            cartBinding.tvItemCount.setText(itemCart + " Item");
        });

        cartViewModel.getCartsLiveData().observe(getViewLifecycleOwner(), productCarts -> {
            carts = productCarts;

            productsRepository.getCartItem(productCarts, getContext(), cartItems -> {
                cartAdapter.submitList(cartItems);
                totalBill = 0;
                int itemQuantity = 0;

                for (CartItem item : cartItems) {
                    itemQuantity += item.getProductCart().getQuantity();
                    totalBill += (long) item.getProductCart().getQuantity() * item.getProduct().getPrice();
                }

                cartViewModel.setItemCount(itemQuantity);
                cartViewModel.setTotal(totalBill);
            });
        });

        cartViewModel.getTotalAmount().observe(getViewLifecycleOwner(), total -> {
            DecimalFormat format = new DecimalFormat("#,### đ");
            cartBinding.tvTotalAmountCart.setText(format.format(total));
        });
    }

    private void addEvents() {
        cartBinding.btnCartCheckout.setOnClickListener(viewButtonCart -> {
            if (totalBill > 0) {
                Intent intentToCheckout = new Intent(getContext(), CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("total", totalBill);
                bundle.putSerializable("carts", (Serializable) carts);
                intentToCheckout.putExtras(bundle);
                startActivity(intentToCheckout);
            } else {
                Toast.makeText(getContext(), "Cart is empty.\nPlease buy some item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpRecyclerViewCart() {
        if (getContext() != null) {
            cartAdapter = new CartAdapter(getContext());
            cartBinding.recyclerViewCart.setAdapter(cartAdapter);
            cartBinding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));
            DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            cartBinding.recyclerViewCart.addItemDecoration(itemDecoration);
        }
    }
}