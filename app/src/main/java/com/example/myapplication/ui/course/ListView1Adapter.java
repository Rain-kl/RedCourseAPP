package com.example.myapplication.ui.course;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.example.myapplication.R;
import com.bumptech.glide.request.target.Target;

import java.util.List;

/**
 * 自定义适配器类，用于填充课程列表视图
 */
public class ListView1Adapter extends BaseAdapter {

    private final List<CourseBean> data;
    private final Context context;

    /**
     * 构造方法，初始化数据源和上下文
     *
     * @param context 应用程序上下文
     * @param data    课程数据列表
     */
    public ListView1Adapter(Context context, List<CourseBean> data) {
        this.context = context;
        this.data = data;
    }

    /**
     * 获取列表项的数量
     *
     * @return 列表项数量
     */
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * 根据索引获取指定位置的列表项
     *
     * @param position 列表项索引
     * @return 指定位置的列表项对象
     */
    @Override
    public Object getItem(int position) {
        return data == null ? 0 : data.get(position);
    }

    /**
     * 获取指定位置的列表项ID
     *
     * @param position 列表项索引
     * @return 列表项ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取指定位置的视图项，用于显示在ListView中
     *
     * @param position    列表项索引
     * @param convertView 可重用的视图项
     * @param parent      视图项的父容器
     * @return 完成绑定数据后的视图项
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        CourseBean courseBean = data.get(position);

        // 初始化或复用ViewHolder
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.course_list_item, null);
            holder.tv_title = convertView.findViewById(R.id.tv_item_title);
            holder.iv_img = convertView.findViewById(R.id.iv_item_img);
            holder.tv_loading = convertView.findViewById(R.id.tv_loading);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 显示加载中的文本
        holder.tv_loading.setVisibility(View.VISIBLE);

        // 构建图片URL地址
        @SuppressLint("DefaultLocale") String uriTest = String.format("http://159.75.231.207:9000/red/video/v_%d.png", (position + 1));

        // 设置标题文本
        holder.tv_title.setText(courseBean.getTitle());

        // 使用Glide加载图片并设置监听器
        Glide.with(context)
                .load(uriTest)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Load failed", e);
                        holder.tv_loading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("Glide", "Load successful");
                        holder.tv_loading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.iv_img);

        return convertView;
    }

    /**
     * 缓存列表项中的各个子控件引用
     */
    static class ViewHolder {
        TextView tv_title, tv_loading;
        ImageView iv_img;
    }
}
