package com.example.myapplication.ui.me.Settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.utils.LoginUtils;
import com.example.myapplication.utils.SharedPreferencesLoadUser;
import com.google.android.material.button.MaterialButton;

public class DeleteAccountActivity extends AppCompatActivity {
    SharedPreferencesLoadUser sharedPreferencesLoadUser;
    SharedPreferences sp;
    UserDBHelper userDBHelper = new UserDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_delete_account), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sp = getSharedPreferences("data", MODE_PRIVATE);
        sharedPreferencesLoadUser = new SharedPreferencesLoadUser(sp);
        MaterialButton btnConfirmLogout = findViewById(R.id.btn_confirm_logout);
        btnConfirmLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("注销账号")
                .setMessage("注销后不可恢复，确定要继续吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginUtils loginUtils = new LoginUtils(userDBHelper, sp);
                        loginUtils.logout(sharedPreferencesLoadUser.getUser());
                        userDBHelper.deleteUser(sharedPreferencesLoadUser.getUser().getId());
                        // 添加注销逻辑，这里可以清除用户数据或者返回登录界面
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}