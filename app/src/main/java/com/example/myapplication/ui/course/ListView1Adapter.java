package com.example.myapplication.ui.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

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
        holder.iv_img.setImageResource(bean.getImgResId());

        return convertView;
    }

    private void setImage(int imgResId, ImageView imageView) {
        switch (imgResId) {
            case 1:
                imageView.setImageResource(R.drawable.ad_1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ad_2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.ad_3);
        }
    }

    class ViewHolder {
        TextView tv_title;
        ImageView iv_img;
    }

}
