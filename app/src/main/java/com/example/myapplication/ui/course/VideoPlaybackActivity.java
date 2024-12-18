package com.example.myapplication.ui.course;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

import java.io.File;


public class VideoPlaybackActivity extends AppCompatActivity {

    private StyledPlayerView playerView;
    private ExoPlayer player;
    private SimpleCache simpleCache;
    private String[] videoUrls = {
            "http://159.75.231.207:9000/red/video/v_1.mp4",
            "http://159.75.231.207:9000/red/video/v_2.mp4",
            "http://159.75.231.207:9000/red/video/v_3.mp4"
    };
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_playback);

        playerView = findViewById(R.id.video_view);

        // 初始化缓存
        File downloadDirectory = new File(getExternalCacheDir(), "media");
        LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(1024 * 1024 * 100); // 100MB cache
        simpleCache = new SimpleCache(downloadDirectory, evictor);

        // 获取从 CourseFragment 传递过来的位置
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0); // 默认位置为0
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

    private void initializePlayer() {
        if (player == null) {
            player = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(player);

            // 根据位置获取视频URL
            String videoUrl = videoUrls[position];
            Uri videoUri = Uri.parse(videoUrl);

            // 创建带有缓存功能的数据源
            DataSource.Factory upstreamFactory = new DefaultHttpDataSource.Factory();
            CacheDataSource.Factory cacheDataSourceFactory =
                    new CacheDataSource.Factory()
                            .setCache(simpleCache)
                            .setUpstreamDataSourceFactory(upstreamFactory);

            MediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUri));

            player.setMediaSource(mediaSource);
            player.prepare();
            player.setPlayWhenReady(true);
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
                e.printStackTrace();
            }
        }
    }
}