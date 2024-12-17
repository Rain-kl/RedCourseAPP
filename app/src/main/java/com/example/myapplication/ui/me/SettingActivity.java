package com.example.myapplication.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.ui.me.Settings.ChangeUserInfoActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_setting), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RelativeLayout changeUserInfo = findViewById(R.id.rl_change_user_info);
        RelativeLayout changePsw = findViewById(R.id.rl_change_psw);
        RelativeLayout changePhone = findViewById(R.id.rl_change_phone);
        RelativeLayout changeSecurity = findViewById(R.id.rl_change_security);
        RelativeLayout privacy = findViewById(R.id.rl_privacy);
        RelativeLayout about = findViewById(R.id.rl_about);
        RelativeLayout countDelete = findViewById(R.id.rl_account_delete);

        Button logoutButton = findViewById(R.id.bt_logout);

        changeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Intent intent = new Intent(SettingActivity.this, ChangeUserInfoActivity.class);
                startActivity(intent);
            }
        });
        changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Toast.makeText(SettingActivity.this, "账号与安全", Toast.LENGTH_SHORT).show();
            }
        });
        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Toast.makeText(SettingActivity.this, "账号与安全", Toast.LENGTH_SHORT).show();
            }
        });

        changeSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Toast.makeText(SettingActivity.this, "账号与安全", Toast.LENGTH_SHORT).show();
            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Toast.makeText(SettingActivity.this, "账号与安全", Toast.LENGTH_SHORT).show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Toast.makeText(SettingActivity.this, "账号与安全", Toast.LENGTH_SHORT).show();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Toast.makeText(SettingActivity.this, "账号与安全", Toast.LENGTH_SHORT).show();
            }
        });
        countDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Toast.makeText(SettingActivity.this, "账号与安全", Toast.LENGTH_SHORT).show();
            }
        });
    }
}