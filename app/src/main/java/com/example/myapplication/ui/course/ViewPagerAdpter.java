package com.example.myapplication.ui.course;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdpter extends PagerAdapter {
    private List<PagerDataBean> pagerDataBeans;
    public ViewPagerAdpter(List<PagerDataBean> pagerDataBeans)
    {
        this.pagerDataBeans = pagerDataBeans;
    }

    @Override
    public int getCount() {
        return pagerDataBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
//加载页面
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = pagerDataBeans.get(position).getView();
        container.addView(view);
        return view;
    }
//销毁页面
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(pagerDataBeans.get(position).getView());
    }
}
