package com.example.myapplication.ui.course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.db.FavoriteDBHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.model.WatchHistory;
import com.example.myapplication.utils.SharedPreferencesLoadUser;
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
    private boolean isFavorite = false;
    String uriTest;

    private int position;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_playback);
        SharedPreferencesLoadUser sharedPreferencesLoadUser = new SharedPreferencesLoadUser(getSharedPreferences("data", MODE_PRIVATE));
        user = sharedPreferencesLoadUser.getUser();

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
        TextView tv_title = findViewById(R.id.tv_video_title);
        tv_title.setText(title);
        TextView tv_desc = findViewById(R.id.tv_video_desc);
        ImageView iv_video_star = findViewById(R.id.iv_video_star);
        iv_video_star.setOnClickListener(v -> toggleFavorite());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean isLandscape = false;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscape = true;
            // 确保在横屏时，控制面板和其他控件也能正确显示和隐藏
            // 这里可以根据需要调整UI逻辑
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            isLandscape = false;
            // 同上，确保在竖屏时，UI能正确调整
        }
    }

    private void toggleFavorite() {
        FavoriteDBHelper favoriteDBHelper = new FavoriteDBHelper(this);

        WatchHistory watchHistory = new WatchHistory(user.getId(), CourseBean.getId(), CourseBean.getTitle(), CourseBean.getDesc(), uriTest);

        isFavorite = !isFavorite;
        ImageView iv_video_star = findViewById(R.id.iv_video_star);
        if (isFavorite) {
            iv_video_star.setImageResource(R.drawable.baseline_star_rate_24); // 已收藏图标
            favoriteDBHelper.addFavorite(watchHistory);
            Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
        } else {
            iv_video_star.setImageResource(R.drawable.baseline_star_outline_24); // 未收藏图标
            favoriteDBHelper.deleteFavorite(user.getId(), CourseBean.getId());
            Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("DefaultLocale")
    private void initializePlayer() {
        try {
            if (player == null) {
                player = new ExoPlayer.Builder(this).build();
                playerView.setPlayer(player);
                uriTest = String.format("http://159.75.231.207:9000/red/video/v_%d.mp4", (position + 1));

                String videoUrl = String.format(uriTest);
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