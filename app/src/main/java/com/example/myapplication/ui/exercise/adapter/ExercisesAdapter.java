package com.example.myapplication.ui.exercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ui.exercise.bean.ExercisesBean;

import java.util.List;

public class ExercisesAdapter extends BaseAdapter {
    private List<ExercisesBean>eb1;
    private Context mContext;

    public ExercisesAdapter(Context mContext){
        this.mContext=mContext;
    }
    public void setData(List<ExercisesBean>  eb1){
        this.eb1=eb1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eb1 == null?0:eb1.size();
    }

    @Override
    public Object getItem(int position) {
        return eb1 == null?null:eb1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.exericises_list_item,null);
            vh.title=convertView.findViewById(R.id.tv_title);
            vh.content=convertView.findViewById(R.id.tv_content);
            vh.order=convertView.findViewById(R.id.tv_order);
            convertView.setTag(vh);
        }else {
            vh=(ViewHolder) convertView.getTag();
        }
        ExercisesBean bean=(ExercisesBean) getItem(position);
        if(bean!=null){
            vh.order.setText(position+1+"");
            vh.title.setText(bean.title);
            vh.content.setText(bean.content);
            vh.order.setBackgroundResource(bean.background);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean==null){
                    return;
                }
            }
        });
        return convertView;
    }
    class ViewHolder{
        public TextView title,content;
        public TextView order;
    }
}
