package com.example.myapplication.ui.me;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.databinding.FragmentMeBinding;
import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.model.User;

public class MeFragment extends Fragment {

    private FragmentMeBinding binding;
    private User user;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private UserDBHelper userDBHelper;
    private int userId;

    /**
     * 问题：在 MeFragment 的 onCreateView 中，getActivity() 可能返回 null。在Fragment生命周期的早期阶段，Fragment 还没有和 Activity 关联起来，因此 getActivity() 可能会返回 null。
     * 解决方案： 将数据库操作延迟到 onAttach() 或 onActivityCreated() 之后进行。这两个方法可以确保 Fragment 已经和 Activity 关联，可以安全地使用 getContext() 或 getActivity()。建议使用 onAttach()，因为它在生命周期中更早被调用，可以确保在 onCreateView() 之前数据库帮助类已经被正确初始化。
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 初始化 SharedPreferences 和 UserDBHelper
        sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", -1);
        userDBHelper = new UserDBHelper(context);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // 检查用户是否已登录
        if (userId != -1) {
            user = userDBHelper.getUserById(userId);
            // 检查 user 是否为 null
            if (user != null) {
                TextView tvUsername = binding.tvUserName;
                tvUsername.setText(user.getUsername());
                TextView tvUserID = binding.tvUserId;
                tvUserID.setText("UID: " + user.getId());

            } else {
                Toast.makeText(getContext(), "查询失败", Toast.LENGTH_SHORT).show();
                //可以根据自己实际情况处理
            }
        }
        ImageView ivSetting = binding.ivSetting;
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout llHistory = binding.llHistory;
        llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout llFavorite = binding.llFavorite;
        llFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FavoriteActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}