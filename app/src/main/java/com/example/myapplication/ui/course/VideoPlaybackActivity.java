package com.example.myapplication.ui.course;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

import java.io.File;
import java.util.Objects;

public class VideoPlaybackActivity extends AppCompatActivity {

    private static final String TAG = "VideoPlaybackActivity";
    private static final String PREFS_NAME = "favorite_prefs";
    private static final String PREF_KEY_IS_FAVORITE = "is_favorite";

    private StyledPlayerView playerView;
    private ExoPlayer player;
    private SimpleCache simpleCache;
    private String uriTest;
    private User user;
    private CourseBean courseBean;
    private String desc;
    private String title;
    private int id;
    private String strId;
    private LinearLayout detailsLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_playback);

        // 创建一个全屏的遮罩层
        View overlay = new View(this);
        overlay.setBackgroundColor(Color.WHITE);
        overlay.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        // 将遮罩层添加到根布局
        ((RelativeLayout) findViewById(R.id.root_layout)).addView(overlay);

        // 立即初始化视图和数据
        initViewsAndData();

        // 使用 Handler 实现 0.5 秒的延迟
        new Handler().postDelayed(() -> {
            // 延迟结束，移除遮罩层
            ((RelativeLayout) findViewById(R.id.root_layout)).removeView(overlay);
        }, 650); // 500 毫秒即 0.5 秒
    }



    private void initViewsAndData() {
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

        detailsLayout = findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.VISIBLE);
        playerView = findViewById(R.id.video_view);

        // 获取从 CourseFragment 传递过来的位置和其他信息
        Intent intent = getIntent();
        id = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("id")));
        strId = String.valueOf(id);
        desc = intent.getStringExtra("desc");
        title = intent.getStringExtra("title");

        TextView tvTitle = findViewById(R.id.tv_video_title);
        TextView tvDesc = findViewById(R.id.tv_video_desc);
        ImageView ivVideoStar = findViewById(R.id.iv_video_star);

        // 设置视频标题和描述
        tvTitle.setText(title);
        tvDesc.setText(desc);

        // 从 SharedPreferences 加载收藏状态
        boolean isFavorite = loadFavoriteStatus(id);
        ivVideoStar.setImageResource(isFavorite ? R.drawable.baseline_star_rate_24 : R.drawable.baseline_star_outline_24);

        // 设置收藏按钮点击监听器
        ivVideoStar.setOnClickListener(v -> toggleFavorite());

//        // 根据当前屏幕方向调整 UI
//        adjustUIForOrientation(getResources().getConfiguration().orientation);
//        hideStatusBarOnLandscape(getResources().getConfiguration().orientation);

        // 初始化播放器
        initializePlayer();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // initializePlayer(); // 移除这里的调用
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
        adjustUIForOrientation(newConfig.orientation);
        hideStatusBarOnLandscape(newConfig.orientation);
    }

    /**
     * 根据屏幕方向调整 UI
     */
    private void adjustUIForOrientation(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            detailsLayout.setVisibility(View.GONE);
        } else {
            detailsLayout.setVisibility(View.VISIBLE);
        }
    }

    private void hideStatusBarOnLandscape(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void toggleFavorite() {
        FavoriteDBHelper favoriteDBHelper = new FavoriteDBHelper(this);
        @SuppressLint("DefaultLocale") String uriImgTest = String.format("http://159.75.231.207:9000/red/video/v_%d.png", id);

        WatchHistory watchHistory = new WatchHistory(user.getId(), strId, title, desc, uriImgTest);

        // 从 SharedPreferences 读取最新的收藏状态
        boolean isFavorite = loadFavoriteStatus(id);
        ImageView ivVideoStar = findViewById(R.id.iv_video_star);

        if (!isFavorite) {
            ivVideoStar.setImageResource(R.drawable.baseline_star_rate_24); // 已收藏图标
            favoriteDBHelper.addFavorite(watchHistory);
            saveFavoriteStatus(id, true);
            Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
        } else {
            ivVideoStar.setImageResource(R.drawable.baseline_star_outline_24); // 未收藏图标
            favoriteDBHelper.deleteFavorite(user.getId(), strId);
            saveFavoriteStatus(id, false);
            Toast.makeText(this, "取消收藏", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("DefaultLocale")
    private void initializePlayer() {
        try {
            if (player == null) {
                player = new ExoPlayer.Builder(this).build();
                playerView.setPlayer(player);
                uriTest = String.format("http://159.75.231.207:9000/red/video/v_%d.mp4", id);

                String videoUrl = uriTest;
                Uri videoUri = Uri.parse(videoUrl);

                DataSource.Factory upstreamFactory = new DefaultHttpDataSource.Factory();
                CacheDataSource.Factory cacheDataSourceFactory =
                        new CacheDataSource.Factory()
                                .setCache(simpleCache)
                                .setUpstreamDataSourceFactory(upstreamFactory);

                ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(videoUri));

                player.setMediaSource(mediaSource);
                // 监听准备状态
                player.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int playbackState) {
                        // 当播放器准备好后，让视频控件显示
                        if (playbackState == Player.STATE_READY) {
                            playerView.setVisibility(View.VISIBLE);
                        }
                    }
                });

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

    private String generatePreferenceKey(int position) {
        return "favorite_" + position;
    }

    /**
     * 保存收藏状态
     */
    private void saveFavoriteStatus(int position, boolean isFavorite) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String key = generatePreferenceKey(position);
        editor.putBoolean(key, isFavorite);
        editor.apply();
    }

    /**
     * 加载收藏状态
     */
    private boolean loadFavoriteStatus(int position) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String key = generatePreferenceKey(position);
        return prefs.getBoolean(key, false);
    }
}