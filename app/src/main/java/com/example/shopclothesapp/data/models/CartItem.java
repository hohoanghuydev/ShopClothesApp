package com.example.shopclothesapp.data.models;

import android.content.Context;

import com.example.shopclothesapp.data.repositories.UserRepository;

import java.util.Objects;

public class CartItem {
    private UserRepository userRepository;
    private ProductCart productCart;
    private Products product;

    public CartItem() {
    }

    public CartItem(ProductCart productCart, Products product, Context applicationContext) {
        userRepository = new UserRepository(applicationContext);
        this.productCart = productCart;
        this.product = product;
    }

    public ProductCart getProductCart() {
        return productCart;
    }

    public void setProductCart(ProductCart productCart) {
        this.productCart = productCart;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(productCart, cartItem.productCart) && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCart, product);
    }

    public void addQuantityCart(String userId, ProductCart cart) {
        int quantity = cart.getQuantity();
        quantity++;
        cart.setQuantity(quantity);
        userRepository.updateQuantity(userId, cart);
    }

    public void subtractQuantityCart(String userId, ProductCart cart) {
        int quantity = cart.getQuantity();
        quantity--;
        cart.setQuantity(quantity);
        userRepository.updateQuantity(userId, cart);
    }

    public void removeProductCart(String userId, ProductCart cart) {
        userRepository.removeProductCart(userId, cart);
    }
}
