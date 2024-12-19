package com.example.myapplication.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.utils.LoginUtils;
import com.example.myapplication.utils.SharedPreferencesLoadUser;

public class LoginActivity extends AppCompatActivity {
    private TextView tv_register;
    private TextView tv_find_psw;
    private TextView btn_login;
    private EditText et_phone;
    private EditText et_psw;

    private UserDBHelper userDBHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferencesLoadUser sharedPreferencesLoadUser;
    private LoginUtils loginUtils;

    private String phone;
    private String psw;

    private boolean checkLogin() {
//        Toast.makeText(this, "用户已登录", Toast.LENGTH_SHORT).show();
        int userId = sharedPreferences.getInt("user_id", -1);
        return userId != -1;
    }

    private void LoginPass() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        userDBHelper = new UserDBHelper(LoginActivity.this);
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        sharedPreferencesLoadUser = new SharedPreferencesLoadUser(sharedPreferences);
        if (checkLogin()) {
            LoginPass();
        } else {
            init();
            init_onclick();
        }
    }

    private void init() {
        TextView tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_register = findViewById(R.id.tv_register);
        tv_find_psw = findViewById(R.id.tv_find_psw);
        btn_login = findViewById(R.id.btn_login);
        et_phone = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
    }

    private void init_onclick() {
        //立即注册按钮的点击事件
        tv_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, 1);
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(v -> {
            //跳转到找回密码界面
        });
        //登录按钮点击事件
        btn_login.setOnClickListener(v -> {
            phone = et_phone.getText().toString().trim();
            psw = et_psw.getText().toString().trim();
            sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            loginUtils = new LoginUtils(userDBHelper, sharedPreferences);
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(psw)) {
                Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            int loginStatus = loginUtils.login(phone, psw);
            if (loginStatus == loginUtils.LOGIN_SUCCESS) {
                LoginPass();
            } else if (loginStatus == loginUtils.LOGIN_FAIL) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}