package com.example.myapplication.ui.me.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.model.User;

public class ChangeUserInfoActivity extends AppCompatActivity {
    private User user;
    private SharedPreferences sharedPreferences;
    private UserDBHelper userDBHelper;

    public void loadUserInfo() {
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        userDBHelper = new UserDBHelper(this);
        user = userDBHelper.getUserById(sharedPreferences.getInt("user_id", -1));

        TextView tvUsername = findViewById(R.id.tv_username);
        TextView tvUserID = findViewById(R.id.tv_user_id);
        TextView tvPhone = findViewById(R.id.tv_phone);
        TextView rvRegisterTime = findViewById(R.id.tv_register_time);


        if (user != null) {
            tvUsername.setText(user.getUsername());
            tvUserID.setText(String.valueOf(user.getId()));
            tvPhone.setText(user.getPhone());
            rvRegisterTime.setText(user.getRegisterDate());
        }
        Toast.makeText(this, "User ID: " + sharedPreferences.getInt("user_id", -1), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_user_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_userinfo), (v, insets) -> {
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
        loadUserInfo();

    }
}