package com.example.myapplication.ui.exercise.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.ui.exercise.adapter.ExercisesAdapter;
import com.example.myapplication.ui.exercise.bean.ExercisesBean;

import java.util.ArrayList;
import java.util.List;

public class ExercisesView {
    private Activity mContent;
    private final LayoutInflater mInflater;
    private View mCurrentView;
    private List<ExercisesBean> eb1;

    public ExercisesView(Activity context){
        mContent=context;
        mInflater=LayoutInflater.from(mContent);
    }
    public View getView(){
        if(mCurrentView==null){
            createView();
        }
        return mCurrentView;
    }

    public void showView(){
        if(mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
    private void createView(){
        initView();
    }
    private void initView(){
        mCurrentView =mInflater.inflate(R.layout.fragment_exercise,null);
        ListView lv_list =mCurrentView.findViewById(R.id.lv_list);
        ExercisesAdapter adapter=new ExercisesAdapter(mContent);
        initData();
        adapter.setData(eb1);
        lv_list.setAdapter(adapter);
    }
    private void initData(){
        eb1=new ArrayList<>();
        for(int i=0;i<10;i++){
            ExercisesBean bean=new ExercisesBean();
            bean.id=i+1;
            switch (i){
                case 0:
                    bean.title="第1章 马克思列宁主义";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 1:
                    bean.title="第2章 毛泽东思想";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                case 2:
                    bean.title="第3章 邓小平理论";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_3);
                    break;
                case 3:
                    bean.title="第4章 三个代表重要思想";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_4);
                    break;
                case 4:
                    bean.title="第5章 科学发展观";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 5:
                    bean.title="第6章 习近平新时代中国特色社会主义思想";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
                case 6:
                    bean.title="第7章 中国近代史";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_3);
                    break;
                case 7:
                    bean.title="第8章 时事政治";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_4);
                    break;
                case 8:
                    bean.title="第9章 中华民族伟大复兴";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 9:
                    bean.title="第10章 人类命运共同体";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_2);
                    break;
            }
            eb1.add(bean);
        }
    }

}
