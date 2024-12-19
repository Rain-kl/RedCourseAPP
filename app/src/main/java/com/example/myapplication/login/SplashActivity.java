package com.example.myapplication.login;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Timer;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.example.myapplication.R;

import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        init();
    }
    private void init(){
        TextView tv_version=(TextView)findViewById(R.id.tv_version);
        try{
            PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
            tv_version.setText("V"+info.versionName);
        }
        catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
            tv_version.setText("V");
        }
        //利用timer让此界面延迟3秒后跳转，timer有一个线程，这个线程不断执行task
        Timer timer=new Timer();
        //timer task实现runnable接口，TimeTask类表示在一个指定时间内执行的task
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,500);//设置这个task在延迟3秒后自动执行
    }
}
