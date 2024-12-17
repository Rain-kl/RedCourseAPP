package com.example.myapplication.ui.course;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCourseBinding;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {
    private final List<PagerDataBean> pagerData = new ArrayList<>();
    private final int[] res = new int[]{
            R.drawable.ad_1,
            R.drawable.ad_2,
            R.drawable.ad_3};
    private final List<CourseBean> listData = new ArrayList<>();

    private FragmentCourseBinding binding;

    @SuppressLint("ShowToast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCourseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ViewPager viewPager = binding.vpViewPager;
        initView();
        ViewPagerAdpter adapter = new ViewPagerAdpter(pagerData);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        ListView listView = binding.lvCourse;
        ListView1Adapter listAdapter = new ListView1Adapter(getContext(), listData);
        listView.setAdapter(listAdapter);

        // 填充 listData 数据
        fillListData();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getContext(), "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
        });
        return root;
    }
    private void fillListData() {
        // 示例：向 listData 添加一些 CourseBean 实例
        listData.add(new CourseBean( R.drawable.ad_1,"课程1"));
        listData.add(new CourseBean( R.drawable.ad_2,"课程2"));
        listData.add(new CourseBean( R.drawable.ad_3,"课程3"));
        // 根据实际需求添加更多课程
    }

    private void initView() {
        for (int i = 0; i < res.length; i++) {
            View view = LayoutInflater.from(getContext()).inflate(
                    R.layout.ad_list, null, false
            );
            ImageView imageView = view.findViewById(R.id.iv_ad_img);
            imageView.setBackgroundResource(res[i]);
            PagerDataBean bean = new PagerDataBean();
            bean.setView(view);
            pagerData.add(bean);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}