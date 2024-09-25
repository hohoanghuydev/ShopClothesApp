package com.example.shopclothesapp.utils;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.shopclothesapp.data.models.Users;

import java.io.IOException;
import java.security.GeneralSecurityException;

public final class DataSharedPreferences {
    private SharedPreferences sharedPreferences;

    public DataSharedPreferences(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(context,
                    "secure_prefs_user", masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserLogin(Users user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("email", user.getEmail());
        editor.putString("uid", user.getUserId());
        editor.putString("name", user.getFullname());
        editor.apply();
    }

    public void deleteUserSignOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("email");
        editor.remove("uid");
        editor.remove("name");
        editor.apply();
    }

    public String getUserLogin(String key) {
        return sharedPreferences.getString(key, null);
    }
}
