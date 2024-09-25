package com.example.shopclothesapp.data.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.shopclothesapp.data.models.Address;
import com.example.shopclothesapp.data.models.Orders;
import com.example.shopclothesapp.data.models.ProductCart;
import com.example.shopclothesapp.data.models.ProductFavorites;
import com.example.shopclothesapp.data.models.Users;
import com.example.shopclothesapp.utils.DataSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    DataSharedPreferences dataSharedPreferences;
    Context applicationContext;
    DatabaseReference reference;
    String currentUser;

    public UserRepository(Context context) {
        applicationContext = context.getApplicationContext();
        dataSharedPreferences = new DataSharedPreferences(applicationContext);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        currentUser = getUserLogin("uid");
    }

    public void addProfileUser(Users user) {
        reference.child(user.getUserId()).child("info").setValue(user)
                .addOnCompleteListener(task -> Log.d("Add info", "Add successfully"))
                .addOnFailureListener(e -> Log.e("Error firebase", e.getMessage()));
    }

    public void updateProfile(Users dataUser, MutableLiveData<Boolean> stateUpdate) {
        reference.child(currentUser).child("info").setValue(dataUser)
                .addOnCompleteListener(task -> stateUpdate.setValue(true))
                .addOnFailureListener(e -> Log.e("Error firebase", e.getMessage()));
    }

    public void getInfoUser(MutableLiveData<Users> usersMutableLiveData) {
        reference.child(currentUser).child("info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);

                    if(user != null) {
                        usersMutableLiveData.setValue(user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
            }
        });
    }

    public void updateAddressShipping(Address address, MutableLiveData<Boolean> stateAdd) {
        reference.child(currentUser).child("address").child(address.getAddressId()).setValue(address)
                .addOnCompleteListener(task -> stateAdd.setValue(true))
                .addOnFailureListener(e -> Log.e("Error firebase", e.getMessage()));
    }

    public void getAddressShipping(MutableLiveData<List<Address>> addressLiveData) {
        DatabaseReference reference = database.getReference("users").child(currentUser).child("address");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Address> arrayAddress = new ArrayList<>();

                if(snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Address address = itemSnapshot.getValue(Address.class);

                        if (address != null) {
                            arrayAddress.add(address);
                        }
                    }

                    addressLiveData.setValue(arrayAddress);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
            }
        });
    }

    public void getProductFavorites(String userId, MutableLiveData<List<ProductFavorites>> favoritesLiveData) {
        DatabaseReference reference = database.getReference("users").child(userId).child("favorites");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    List<ProductFavorites> productFavorites = new ArrayList<>();

                    for(DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        ProductFavorites productFavorite = itemSnapshot.getValue(ProductFavorites.class);

                        if(productFavorite != null) {
                            productFavorites.add(productFavorite);
                        }
                    }

                    favoritesLiveData.setValue(productFavorites);
                } else {
                    favoritesLiveData.setValue(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
            }
        });
    }

    public void getStateFavorite(String userId, String productId, MutableLiveData<Boolean> stateFavorite) {
        DatabaseReference reference = database.getReference("users").child(userId).child("favorites").child(productId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    stateFavorite.setValue(true);
                } else {
                    stateFavorite.setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
                stateFavorite.setValue(false);
            }
        });
    }

    public void removeFromFavorite(String userId, String productId) {
        DatabaseReference reference = database.getReference("users");
        reference.child(userId)
                .child("favorites").child(productId).removeValue()
                .addOnCompleteListener(task -> Log.d("Favorites", "Remove successfully"))
                .addOnFailureListener(e -> Log.d("Favorites", "Remove Failure"));
    }

    public void addToCart(String userId, ProductCart cart, MutableLiveData<Boolean> stateOrder) {
        String cartId = cart.getProductId() + cart.getSize();
        DatabaseReference reference = database.getReference("users").child(userId)
                .child("cart").child(cartId);

        Map<String, Object> productCart = new HashMap<>();
        productCart.put("productId", cart.getProductId());
        productCart.put("quantity", ServerValue.increment(cart.getQuantity()));
        productCart.put("size", cart.getSize());

        reference.updateChildren(productCart).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                stateOrder.setValue(true);
            } else {
                stateOrder.setValue(false);
            }
        }).addOnFailureListener(e -> stateOrder.setValue(false));
    }

    public void addToFavorite(String userId, String productId) {
        DatabaseReference reference = database.getReference("users");
        reference.child(userId)
                .child("favorites").child(productId).setValue(new ProductFavorites(productId, true))
                .addOnCompleteListener(task -> Log.d("Favorites", "Add successfully"))
                .addOnFailureListener(e -> Log.d("Favorites", "Add Failure"));
    }

    public void updateQuantity(String userId, ProductCart cart) {
        String cartId = cart.getProductId() + cart.getSize();
        DatabaseReference reference = database.getReference("users");

        reference.child(userId).child("cart").child(cartId).setValue(cart)
                .addOnCompleteListener(task -> Log.d("Update", "Update success"))
                .addOnFailureListener(error -> Log.e("Update", "Update failed"));
    }

    public void removeProductCart(String userId, ProductCart cart) {
        String cartId = cart.getProductId() + cart.getSize();
        DatabaseReference reference = database.getReference("users");

        reference.child(userId).child("cart").child(cartId).removeValue()
                .addOnCompleteListener(task -> Log.d("Remove", "Remove success"))
                .addOnFailureListener(error -> Log.e("Remove", "Remove failed"));
    }

    public void getProductsOrder(MutableLiveData<List<ProductCart>> cartsLiveData) {
        DatabaseReference reference = database.getReference("users");
        String currentUser = getUserLogin("uid");
        reference.child(currentUser).child("cart").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ProductCart> productCarts = new ArrayList<>();

                for(DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    ProductCart productCart = itemSnapshot.getValue(ProductCart.class);

                    if(productCart != null) {
                        productCarts.add(productCart);
                    }
                }
                cartsLiveData.setValue(productCarts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Firebase", error.getMessage());
            }
        });
    }

    public void removeAllProductCart(String userId) {
        DatabaseReference reference = database.getReference("users");

        reference.child(userId).child("cart").removeValue()
                .addOnCompleteListener(task -> Log.d("Cart", "Remove Complete"))
                .addOnFailureListener(e -> Log.e("Cart", e.getMessage()));
    }

    public void checkoutOrder(String userId, Orders orders, MutableLiveData<Boolean> stateCheckout) {
        reference.child(userId).child("orders").child(orders.getOrderId()).setValue(orders).addOnCompleteListener(task -> {
            Log.d("Checkout", "Complete");
            stateCheckout.setValue(true);
        }).addOnFailureListener(e -> {
            Log.d("Checkout", "Failed");
            stateCheckout.setValue(false);
        });
    }

    public void getOrderOfUser(String userId, MutableLiveData<List<Orders>> ordersLiveData) {
        reference.child(userId).child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    List<Orders> orders = new ArrayList<>();

                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Orders order = itemSnapshot.getValue(Orders.class);

                        if(order != null) {
                            orders.add(order);
                        }
                    }

                    ordersLiveData.setValue(orders);
                }
                Log.e("Error firebase", "Empty order");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error firebase", error.getMessage());
            }
        });
    }

    public void signUpAccount(String email, String password, String name, MutableLiveData<Users> userLiveData) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && auth.getCurrentUser() != null) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();

                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                        firebaseUser.updateProfile(userProfileChangeRequest)
                                .addOnCompleteListener(taskUpdate -> {
                                    if (taskUpdate.isSuccessful()) {
                                        Users user = new Users();
                                        user.setUserId(firebaseUser.getUid());
                                        user.setEmail(firebaseUser.getEmail());
                                        user.setFullname(firebaseUser.getDisplayName());

                                        userLiveData.setValue(user);
                                    } else {
                                        userLiveData.setValue(null);
                                    }
                                });
                    } else {
                        userLiveData.setValue(null);
                    }
                });
    }

    public void signInAccount(String email, String password, MutableLiveData<Users> userLiveData) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful() && auth.getCurrentUser() != null) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();

                            Users user = new Users();
                            user.setUserId(firebaseUser.getUid());
                            user.setEmail(firebaseUser.getEmail());
                            user.setFullname(firebaseUser.getDisplayName());
                            userLiveData.setValue(user);
                        } else {
                            userLiveData.setValue(null);
                        }
                    }
                });
    }

    public void signOutAccount() {
        auth.signOut();
    }

    public void changePasswordForgot(String email, MutableLiveData<Boolean> stateSendEmail) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Log.d("Forgot Password", "Check your email");
                        stateSendEmail.setValue(true);
                    } else {
                        Log.d("Forgot Password", "Failure");
                        stateSendEmail.setValue(false);
                    }
                });
    }

    public void saveUserLogin(Users user) {
        dataSharedPreferences.saveUserLogin(user);
    }

    public void deleteUserSignOut() {
        dataSharedPreferences.deleteUserSignOut();
    }

    public String getUserLogin(String key) {
        return dataSharedPreferences.getUserLogin(key);
    }
}
