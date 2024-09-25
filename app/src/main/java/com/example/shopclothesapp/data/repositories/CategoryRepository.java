package com.example.shopclothesapp.data.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.shopclothesapp.data.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public CategoryRepository() {

    }

    public void getAllCategory(MutableLiveData<List<Category>> categoriesLiveData) {
        DatabaseReference databaseReference = database.getReference("categories");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Category> categories = new ArrayList<>();

                if (snapshot.exists()) {
                    for(DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        Category category = itemSnapshot.getValue(Category.class);

                        if(category != null) {
                            categories.add(category);
                        }
                    }

                    categoriesLiveData.setValue(categories);
                } else {
                    Log.e("Category", "Get data failed");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Category", "Get data failed");
            }
        });
    }
}
