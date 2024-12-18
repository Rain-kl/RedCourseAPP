package com.example.myapplication.ui.course;

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

public class ListView1Adapter extends BaseAdapter {
    private final List<CourseBean> data;
    private final Context context;

    public ListView1Adapter(Context context, List<CourseBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? 0 : data.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.course_list_item, null);
            holder.tv_title = convertView.findViewById(R.id.tv_item_title);
            holder.iv_img = convertView.findViewById(R.id.iv_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 直接获取当前位置的数据对象
        CourseBean bean = data.get(position);
        holder.tv_title.setText(bean.getTitle());

        Glide.with(context)
                .load(bean.getImageUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Load failed", e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("Glide", "Load successful");
                        return false;
                    }
                })
                .into(holder.iv_img);

        return convertView;
    }


    class ViewHolder {
        TextView tv_title;
        ImageView iv_img;
    }

}
