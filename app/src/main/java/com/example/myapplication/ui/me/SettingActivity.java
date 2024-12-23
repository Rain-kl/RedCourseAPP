package com.example.myapplication.ui.me;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.login.LoginActivity;
import com.example.myapplication.model.User;
import com.example.myapplication.ui.me.Settings.ChangeUserInfoActivity;
import com.example.myapplication.ui.me.Settings.DeleteAccountActivity;
import com.example.myapplication.utils.MD5Utils;
import com.example.myapplication.utils.SharedPreferencesLoadUser;

public class SettingActivity extends AppCompatActivity {
    SharedPreferences sp;
    UserDBHelper userDBHelper = new UserDBHelper(this);
    SharedPreferencesLoadUser sharedPreferencesLoadUser;
    User user;

    private void loadUserInfo() {
        sp = getSharedPreferences("data", MODE_PRIVATE);
        sharedPreferencesLoadUser = new SharedPreferencesLoadUser(sp);
        user = sharedPreferencesLoadUser.getUser();
    }

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
        loadUserInfo();

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
                editPsw("修改密码");
            }
        });


        changeSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                Toast.makeText(SettingActivity.this, "账号与安全", Toast.LENGTH_SHORT).show();
            }
        });
        countDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, DeleteAccountActivity.class);
                startActivity(intent);
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("隐私信息")
                        .setMessage("balabalabala...\n")
                        .setPositiveButton("确定", null)
                        .show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("关于")
                        .setMessage("版本号：1.0.0\n")
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理账号与安全点击事件
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("退出登录")
                        .setMessage("确定要退出登录吗？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            sharedPreferencesLoadUser.clearUser();
                            Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                            // 清除任务栈并创建新任务
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

    }

    private void editPsw(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_password, null);
        final EditText etOldPassword = dialogView.findViewById(R.id.et_old_password);
        final EditText etNewPassword = dialogView.findViewById(R.id.et_new_password);
        final EditText etConfirmPassword = dialogView.findViewById(R.id.et_confirm_password);

        builder.setView(dialogView)
                .setTitle(title)
                .setPositiveButton("确定", (dialog, which) -> {
                    String oldPassword = etOldPassword.getText().toString();
                    String newPassword = etNewPassword.getText().toString();
                    String confirmPassword = etConfirmPassword.getText().toString();
                    if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    } else if (!newPassword.equals(confirmPassword)) {
                        Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    } else if (!MD5Utils.md5(oldPassword).equals(user.getPassword())) {
                        Toast.makeText(this, "原密码错误", Toast.LENGTH_SHORT).show();
                    } else {
                        user.setPassword(MD5Utils.md5(newPassword));
                        userDBHelper.updateUser(user);
                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}