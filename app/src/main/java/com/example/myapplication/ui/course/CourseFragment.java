package com.example.myapplication.ui.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentCourseBinding;
import com.example.myapplication.db.UserDBHelper;
import com.example.myapplication.db.WatchHistoryDBHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.model.WatchHistory;
import com.example.myapplication.utils.SharedPreferencesLoadUser;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    // 成员变量声明
    private ListView1Adapter listAdapter;
    private final List<CourseBean> listData = new ArrayList<>();
    private FragmentCourseBinding binding;
    private CourseBean courseBean;
    private User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 初始化 SharedPreferences 和 UserDBHelper
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferencesLoadUser sharedPreferencesLoadUser = new SharedPreferencesLoadUser(sharedPreferences);
        user = sharedPreferencesLoadUser.getUser();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // 初始化绑定对象
        binding = FragmentCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListView listView = binding.lvCourse;

        // 初始化 ViewModel
        CourseViewModel viewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        // 观察 LiveData 并在数据变化时更新 UI
        viewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
            listData.clear();
            listData.addAll(courses);
            listAdapter = new ListView1Adapter(getContext(), listData);
            listView.setAdapter(listAdapter);
            setupListViewClickListener();
        });

        // 加载课程数据（这应该在后台线程中进行）
        viewModel.loadCourses();

        return root;
    }

    /**
     * 设置 ListView 的点击监听器
     */
    private void setupListViewClickListener() {
        binding.lvCourse.setOnItemClickListener((parent, view, position, id) -> {
            // 动态获取选中的 CourseBean 对象
            CourseBean selectedCourseBean = listData.get(position);

            // 构建 WatchHistory 对象
            @SuppressLint("DefaultLocale")
            String uriText = String.format("http://159.75.231.207:9000/red/video/v_%d.png", (position + 1));
            WatchHistoryDBHelper watchHistoryDBHelper = new WatchHistoryDBHelper(getContext());
            WatchHistory watchHistory = new WatchHistory(
                    user.getId(),
                    selectedCourseBean.getId(),
                    selectedCourseBean.getTitle(),
                    selectedCourseBean.getDesc(),
                    uriText
            );
            watchHistoryDBHelper.addWatchHistory(watchHistory);

            // 创建 Intent 并设置要传递的数据
            Intent intent = new Intent(getContext(), VideoPlaybackActivity.class);
            intent.putExtra("position", position + 1); // 传递位置（如果需要）
            intent.putExtra("id", String.valueOf(selectedCourseBean.getId())); // 传递 ID
            intent.putExtra("title", selectedCourseBean.getTitle()); // 传递标题
            intent.putExtra("desc", selectedCourseBean.getDesc()); // 传递描述

            // 启动新 Activity
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
