package com.example.shopclothesapp.data.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.shopclothesapp.data.models.CartItem;
import com.example.shopclothesapp.data.models.ProductCart;
import com.example.shopclothesapp.data.models.ProductFavorites;
import com.example.shopclothesapp.data.models.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductsRepository {
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public interface DataResponse {
        void passDataCartItems(List<CartItem> cartItems);
    }

    public void getAllProduct(MutableLiveData<List<Products>> products) {
        DatabaseReference reference = database.getReference("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Products> arrayProducts = new ArrayList<>();
                for(DataSnapshot itemSnapshot : snapshot.getChildren()) {
                   Products products = itemSnapshot.getValue(Products.class);

                   if(products != null) {
                       arrayProducts.add(products);
                       Log.d("DATA", products.toString());
                   }
                }

                products.setValue(arrayProducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
            }
        });
    }

    public void filterProductsByCategory(String categoryId, MutableLiveData<List<Products>> productOfCategory) {
        DatabaseReference reference = database.getReference("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Products> arrayProducts = new ArrayList<>();
                for(DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Products products = itemSnapshot.getValue(Products.class);

                    if(products != null && products.getCategory().equals(categoryId)) {
                        arrayProducts.add(products);
                        Log.d("DATA", products.toString());
                    }
                }

                productOfCategory.setValue(arrayProducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
            }
        });
    }

    public void getCartItem(List<ProductCart> carts, Context context, DataResponse response) {
        DatabaseReference reference = database.getReference("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<CartItem> cartItems = new ArrayList<>();

                    for(DataSnapshot itemSnapshot: snapshot.getChildren()) {
                        Products product = itemSnapshot.getValue(Products.class);

                        for(ProductCart cart : carts) {
                            if(product.getProductId().equals(cart.getProductId())) {
                                cartItems.add(new CartItem(cart, product, context.getApplicationContext()));
                            }
                        }
                    }
                    response.passDataCartItems(cartItems);
                } else {
                    Log.e("Error Firebase", "Snapshot is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
            }
        });
    }

    public void getFavoriteItems(List<ProductFavorites> favorites, MutableLiveData<List<Products>> productFavorites) {
        if(favorites == null) {
            productFavorites.setValue(null);
            return;
        }

        DatabaseReference reference = database.getReference("products");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    List<Products> products = new ArrayList<>();

                    for(DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Products product = itemSnapshot.getValue(Products.class);

                        if(product != null && favorites.stream().anyMatch(r -> r.getProductId().equals(product.getProductId()))) {
                            products.add(product);
                        }
                    }

                    productFavorites.setValue(products);
                } else {
                    Log.e("Error Firebase", "Snapshot is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
            }
        });
    }
}
