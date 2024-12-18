package com.example.myapplication.utils;

import android.content.SharedPreferences;

import com.example.myapplication.model.User;

public class SharedPreferencesLoadUser {
    SharedPreferences sharedPreferences;

    public SharedPreferencesLoadUser(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public User getUser() {
        User user = new User();
        user.setId(sharedPreferences.getInt("user_id", -1));
        user.setUsername(sharedPreferences.getString("username", ""));
        user.setPassword(sharedPreferences.getString("password", ""));
        user.setPhone(sharedPreferences.getString("phone", ""));
        user.setRegisterDate(sharedPreferences.getString("register_date", ""));
        return user;
    }

    public void setUser(User user) {
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putInt("user_id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("phone", user.getPhone());
        editor.putString("register_date", user.getRegisterDate());
        editor.putString("password", user.getPassword());
        editor.apply();

    }
}
