package com.example.myapplication.login;

import android.text.TextUtils;
import android.widget.Button;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import com.example.myapplication.R;
import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.utils.MD5Utils;


public class RegisterActivity extends AppCompatActivity {
    private UserDBHelper userDBHelper;

    private Button btn_register;
    private EditText et_user_name;
    private EditText et_phone;
    private TextView tv_back;
    private EditText et_psw;
    private EditText et_psw_again;

    private String userName;
    private String psw;
    private String pswAgain;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDBHelper = new UserDBHelper(RegisterActivity.this);

        init();
        init_onclick();
    }

    private void init() {
        //从main_title_bar.xml页面布局中获取对应的UI控件
        TextView tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        RelativeLayout rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        tv_back = findViewById(R.id.tv_back);

        //从activity_register.xml页面布局中获取对应的UI控件
        btn_register = findViewById(R.id.btn_register);
        et_user_name = findViewById(R.id.et_user_name);
        et_phone = findViewById(R.id.et_phone);
        et_psw = findViewById(R.id.et_pwd);
        et_psw_again = findViewById(R.id.et_pwd_again);
    }

    private void init_onclick() {
        tv_back.setOnClickListener(view -> RegisterActivity.this.finish());
        btn_register.setOnClickListener(v -> {
            //获取输入在响应控件中的字符串
            userName = et_user_name.getText().toString().trim();
            phone = et_phone.getText().toString().trim();
            psw = et_psw.getText().toString().trim();
            pswAgain = et_psw_again.getText().toString().trim();

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(psw)) {
                Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(pswAgain)) {
                Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean isPhoneExist = userDBHelper.isPhoneExists(phone);
            if (isPhoneExist) {
                Toast.makeText(RegisterActivity.this, "此手机号已经存在", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!psw.equals(pswAgain)) {
                Toast.makeText(RegisterActivity.this, "输入的两次密码不一样", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User();
            newUser.setUsername(userName);
            newUser.setPassword(MD5Utils.md5(psw));
            newUser.setPhone(phone);
            boolean registerStatus = userDBHelper.addUser(newUser);
            if (!registerStatus) {
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent data = new Intent();
                data.putExtra("userName", userName);
                setResult(RESULT_OK, data);
                RegisterActivity.this.finish();
            }
        });
    }
}