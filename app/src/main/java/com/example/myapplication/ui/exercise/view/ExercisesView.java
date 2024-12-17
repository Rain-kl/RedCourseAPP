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
                    bean.title="第1章 什么是马克思主义";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 1:
                    bean.title="第2章 什么是马克思主义";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 2:
                    bean.title="第3章 马克思主义基本原理";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 3:
                    bean.title="第4章 马克思主义的发展";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 4:
                    bean.title="第5章 马克思主义在中国的运用";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 5:
                    bean.title="第6章 马克思主义的再大展";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 6:
                    bean.title="第7章 马克思主义的意义";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 7:
                    bean.title="第8章 马克思主义的历史地位";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 8:
                    bean.title="第9章 马克思主义的再发展";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 9:
                    bean.title="第10章 坚持学习马克思主义";
                    bean.content="共计5道题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
            }
            eb1.add(bean);
        }
    }

}
