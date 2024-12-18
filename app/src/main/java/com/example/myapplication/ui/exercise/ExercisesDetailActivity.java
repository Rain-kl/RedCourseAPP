package com.example.myapplication.ui.exercise;

import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;
import com.example.myapplication.ui.exercise.adapter.ExercisesDetailAdapter;
import com.example.myapplication.ui.exercise.bean.ExercisesBean;
import com.example.myapplication.ui.exercise.utils.AnalysisUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExercisesDetailActivity extends AppCompatActivity {
    private Integer id;
    private List<ExercisesBean> eb1;
    private String title;
    private ExercisesDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercises_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        title=getIntent().getStringExtra("title");
        id=getIntent().getIntExtra("id",0);
        eb1=new ArrayList<>();
        initData();
        init();
    }
    private void init(){
        TextView tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        TextView tv_back=(TextView) findViewById(R.id.tv_back);
        RelativeLayout r1_title_bar=(RelativeLayout) findViewById(R.id.title_bar);
        r1_title_bar.setBackgroundColor(Color.parseColor("#F0382C"));
        ListView lv_list =(ListView) findViewById(R.id.lv_list);
        TextView tv=new TextView(this);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setTextSize(16.0f);
//        tv.setText("一，选择题");
        tv.setPadding(10,15,0,0);
        lv_list.addFooterView(tv);
        tv_main_title.setText(title);
        tv_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ExercisesDetailActivity.this.finish();
            }
        });
        adapter=new ExercisesDetailAdapter(ExercisesDetailActivity.this,new ExercisesDetailAdapter.OnSelectListener(){
            @Override
            public void onSelectA(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                if(eb1.get(position).answer!=1){
                    eb1.get(position).select=1;
                }else {
                    eb1.get(position).select=0;
                }
                switch (eb1.get(position).answer){
                    case 1:
                        iv_a.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 2:
                        iv_a.setImageResource(R.drawable.exercises_error_icon);
                        iv_b.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 3:
                        iv_a.setImageResource(R.drawable.exercises_error_icon);
                        iv_c.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 4:
                        iv_a.setImageResource(R.drawable.exercises_error_icon);
                        iv_d.setImageResource(R.drawable.exercises_right_icon);
                        break;
                }
                AnalysisUtils.setABCDEnable(false ,iv_a,iv_b,iv_c,iv_d);
            }

            @Override
            public void onSelectB(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                if(eb1.get(position).answer!=2){
                    eb1.get(position).select=2;
                }else {
                    eb1.get(position).select=0;
                }
                switch (eb1.get(position).answer){
                    case 1:
                        iv_a.setImageResource(R.drawable.exercises_right_icon);
                        iv_b.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 2:
                        iv_b.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 3:
                        iv_b.setImageResource(R.drawable.exercises_error_icon);
                        iv_c.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 4:
                        iv_b.setImageResource(R.drawable.exercises_error_icon);
                        iv_d.setImageResource(R.drawable.exercises_right_icon);
                        break;
                }
                AnalysisUtils.setABCDEnable(false ,iv_a,iv_b,iv_c,iv_d);
            }

            @Override
            public void onSelectC(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                if(eb1.get(position).answer!=3){
                    eb1.get(position).select=3;
                }else {
                    eb1.get(position).select=0;
                }
                switch (eb1.get(position).answer){
                    case 1:
                        iv_a.setImageResource(R.drawable.exercises_right_icon);
                        iv_c.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 2:
                        iv_b.setImageResource(R.drawable.exercises_right_icon);
                        iv_c.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 3:
                        iv_c.setImageResource(R.drawable.exercises_right_icon);
                        break;
                    case 4:
                        iv_c.setImageResource(R.drawable.exercises_error_icon);
                        iv_d.setImageResource(R.drawable.exercises_right_icon);
                        break;
                }
                AnalysisUtils.setABCDEnable(false ,iv_a,iv_b,iv_c,iv_d);
            }

            @Override
            public void onSelectD(int position, ImageView iv_a, ImageView iv_b, ImageView iv_c, ImageView iv_d) {
                if(eb1.get(position).answer!=4){
                    eb1.get(position).select=4;
                }else {
                    eb1.get(position).select=0;
                }
                switch (eb1.get(position).answer){
                    case 1:
                        iv_a.setImageResource(R.drawable.exercises_right_icon);
                        iv_d.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 2:
                        iv_b.setImageResource(R.drawable.exercises_right_icon);
                        iv_d.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 3:
                        iv_c.setImageResource(R.drawable.exercises_right_icon);
                        iv_d.setImageResource(R.drawable.exercises_error_icon);
                        break;
                    case 4:
                        iv_d.setImageResource(R.drawable.exercises_right_icon);
                        break;
                }
                AnalysisUtils.setABCDEnable(false ,iv_a,iv_b,iv_c,iv_d);
            }
        });
        adapter.setData(eb1);
        lv_list.setAdapter(adapter);
    }
    private void initData(){
        try {
            InputStream is=getResources().getAssets().open("chapter" + id + ".xml");
            eb1 =AnalysisUtils.getExercisesInfos(is);
        }catch (Exception e){
//            Log.d("initData",e.toString());
            e.printStackTrace();
        }
    }
}