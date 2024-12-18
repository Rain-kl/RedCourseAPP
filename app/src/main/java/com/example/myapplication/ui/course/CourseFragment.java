package com.example.myapplication.ui.course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentCourseBinding;
import com.example.myapplication.db.WatchHistoryDBHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.model.WatchHistory;


import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {
    private ListView1Adapter listAdapter;
    private List<CourseBean> listData = new ArrayList<>();
    private FragmentCourseBinding binding;

    @SuppressLint("ShowToast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListView listView = binding.lvCourse;

        // 初始化ViewModel
        CourseViewModel viewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        // 观察LiveData并在数据变化时更新UI
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

    private void setupListViewClickListener() {
        binding.lvCourse.setOnItemClickListener((parent, view, position, id) -> {
            // 获取选中项的数据
            User user = new User();
            @SuppressLint("DefaultLocale") String uriText = String.format("http://159.75.231.207:9000/red/video/v_%d.png",(position+1));
            WatchHistoryDBHelper watchHistoryDBHelper = new WatchHistoryDBHelper(getContext());
            WatchHistory watchHistory = new WatchHistory(user.getId(),CourseBean.getId(),CourseBean.getTitle(),CourseBean.getDesc(),
                    uriText);
            watchHistoryDBHelper.addWatchHistory(watchHistory);
            // 创建Intent并设置要传递的数据
            Intent intent = new Intent(getContext(), VideoPlaybackActivity.class);
            intent.putExtra("position", position); // 传递位置（如果需要）
            intent.putExtra("id", CourseBean.getId());
            intent.putExtra("title", CourseBean.getTitle()); // 传递标题
            intent.putExtra("desc", CourseBean.getDesc()); // 传递描述

            // 启动新Activity
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}