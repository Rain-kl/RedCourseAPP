package com.example.myapplication.utils;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.model.User;

public class LoginUtils {
    public final int LOGIN_SUCCESS = 1;
    public final int LOGIN_FAIL = 0;

    private final SharedPreferences sharedPreferences;
    private final UserDBHelper db;
    private final SharedPreferencesLoadUser sharedPreferencesLoadUser;


    public LoginUtils(UserDBHelper db, SharedPreferences sharedPreferences) {
        this.db = db;
        this.sharedPreferences = sharedPreferences;
        sharedPreferencesLoadUser = new SharedPreferencesLoadUser(sharedPreferences);
    }

    public int login(String phone, String password) {
        User user = db.getUserByPhone(phone);
        if (user == null) {
            Log.d("LoginUtils", "User not found");
            return LOGIN_FAIL;
        } else if (!user.getPassword().equals(MD5Util.md5(password))) {
            Log.d("LoginUtils", "Password incorrect");
            return LOGIN_FAIL;
        } else {
            sharedPreferencesLoadUser.setUser(user);
            return LOGIN_SUCCESS;
        }
    }

    public void logout(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id");
        editor.apply();
    }
}
