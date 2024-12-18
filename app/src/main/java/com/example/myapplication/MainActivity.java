package com.example.myapplication;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.db.MyDBHelper;
import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.utils.LoginUtils;
import com.example.myapplication.utils.MD5Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public UserDBHelper db = new UserDBHelper(this);
    private LoginUtils loginUtils;

    public void createTestUser() {
        loginUtils = new LoginUtils(db, getSharedPreferences("data", MODE_PRIVATE));

        // 注册新用户
        User testUser = new User("testuser", "password123", "123456789");
        if (db.isPhoneExists(testUser.getPhone())) {
            Toast.makeText(this, "用户已存在，开始登录", Toast.LENGTH_SHORT).show();
            testUser = db.getUserByPhone(testUser.getPhone());

            int status = loginUtils.login(testUser.getPhone(), testUser.getPassword());
            if (status == loginUtils.LOGIN_SUCCESS) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                Log.d("createTestUser", "登录失败");
            }
        } else {
            Toast.makeText(this, "用户不存在，开始注册", Toast.LENGTH_SHORT).show();
            testUser.setPassword(MD5Util.md5(testUser.getPassword()));
            long userId = db.addUser(testUser);
            if (userId != -1) {
                Toast.makeText(this, "注册成功，开始登录", Toast.LENGTH_SHORT).show();
                int status = loginUtils.login(testUser.getPhone(), testUser.getPassword());
                if (status == loginUtils.LOGIN_SUCCESS) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
                    Log.d("createTestUser", "登录失败");
                }
            } else {
                Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                Log.d("createTestUser", "注册失败");
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createTestUser();  // 创建测试用户


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}