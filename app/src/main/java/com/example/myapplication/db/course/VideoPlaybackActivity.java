package com.example.myapplication.db.course;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

import java.io.File;

public class VideoPlaybackActivity extends AppCompatActivity {

    private static final String TAG = "VideoPlaybackActivity";
    private StyledPlayerView playerView;
    private ExoPlayer player;
    private SimpleCache simpleCache;
    private boolean isLandscape = false;

    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_playback);

        // 初始化缓存（如果尚未初始化）
        if (simpleCache == null) {
            File downloadDirectory = new File(getExternalCacheDir(), "media");
            try {
                simpleCache = new SimpleCache(downloadDirectory, new LeastRecentlyUsedCacheEvictor(1024 * 1024 * 100)); // 100MB cache
            } catch (Exception e) {
                Log.e(TAG, "Error initializing cache", e);
            }
        }

        playerView = findViewById(R.id.video_view);

        // 获取从 CourseFragment 传递过来的位置和其他信息
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        String desc = intent.getStringExtra("desc");
        String title = intent.getStringExtra("title");

        ImageView iv_back = findViewById(R.id.iv_video_back);
        ImageView iv_handoff = findViewById(R.id.iv_video_handoff);

        // 设置按钮点击事件
        iv_back.setOnClickListener(v -> onBackPressed());
        iv_handoff.setOnClickListener(v -> toggleScreenOrientation());

        TextView tv_title = findViewById(R.id.tv_video_title);
        tv_title.setText(title);
        TextView tv_desc = findViewById(R.id.tv_video_desc);
        tv_desc.setText(desc);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void toggleScreenOrientation() {
        if (!isLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        isLandscape = !isLandscape;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            isLandscape = false;
        }
    }

    private void initializePlayer() {
        try {
            if (player == null) {
                player = new ExoPlayer.Builder(this).build();
                playerView.setPlayer(player);
                String URL = "http://159.75.231.207:9000/red/video/v_";
                String videoUrl = String.format("%s%d.mp4", URL, position + 1);
                Uri videoUri = Uri.parse(videoUrl);

                DataSource.Factory upstreamFactory = new DefaultHttpDataSource.Factory();
                CacheDataSource.Factory cacheDataSourceFactory =
                        new CacheDataSource.Factory()
                                .setCache(simpleCache)
                                .setUpstreamDataSourceFactory(upstreamFactory);

                ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(videoUri));

                player.setMediaSource(mediaSource);
                player.prepare();
                player.setPlayWhenReady(true);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing player", e);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (simpleCache != null) {
            try {
                simpleCache.release();
            } catch (Exception e) {
                Log.e(TAG, "Error releasing cache", e);
            }
        }
    }
}