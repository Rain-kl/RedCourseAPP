package com.example.myapplication.utils;

import android.content.SharedPreferences;

import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.model.User;

public class LoginUtils {
    public final int LOGIN_SUCCESS = 1;
    public final int LOGIN_FAIL = 0;

    private final SharedPreferences sharedPreferences;
    private final UserDBHelper db;


    public LoginUtils(UserDBHelper db, SharedPreferences sharedPreferences) {
        this.db = db;
        this.sharedPreferences = sharedPreferences;
    }

    public int login(String phone, String password) {
        User user = db.getUserByPhone(phone);
        if (user == null) {
            return LOGIN_FAIL;
        } else if (!user.getPassword().equals(MD5Util.md5(password))) {
            return LOGIN_FAIL;
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id", user.getId());
            editor.apply();
            return LOGIN_SUCCESS;
        }
    }

    public void logout(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id");
        editor.apply();
    }
}
