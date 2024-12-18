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

import com.example.myapplication.databinding.FragmentCourseBinding;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {
    ListView1Adapter listAdapter;
    private final List<CourseBean> listData = new ArrayList<>();

    private FragmentCourseBinding binding;

    @SuppressLint("ShowToast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListView listView = binding.lvCourse;
         listAdapter = new ListView1Adapter(getContext(), listData);
        listView.setAdapter(listAdapter);

        // 填充 listData 数据
        fillListData();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), VideoPlaybackActivity.class);
            intent.putExtra("position", position); // 将点击的位置传递给 VideoPlaybackActivity
            startActivity(intent);
        });
        return root;
    }
    private void fillListData() {
        String[] imageUrls = {
                "http://159.75.231.207:9000/red/image/carousel/carousel_1.jpg",
                "http://159.75.231.207:9000/red/image/carousel/carousel_2.jpg", // 修正了文件名错误
                "http://159.75.231.207:9000/red/image/carousel/carousel_3.jpg"
        };


        for (int i = 0; i < imageUrls.length; i++) {
            // 假设CourseBean有一个接受String类型的构造函数，用于存储图片URL
            listData.add(new CourseBean(imageUrls[i], "课程" + (i + 1)));
        }



        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}