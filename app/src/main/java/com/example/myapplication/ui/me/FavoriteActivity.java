package com.example.myapplication.ui.me;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.FavoriteDBHelper;
import com.example.myapplication.db.WatchHistoryDBHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.model.WatchHistory;
import com.example.myapplication.utils.SharedPreferencesLoadUser;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView favoriteRecyclerView;
    private VideoAdapter videoAdapter;
    private List<WatchHistory> videoItemList;

    SharedPreferences sharedPreferences;
    SharedPreferencesLoadUser sharedPreferencesLoadUser;
    FavoriteDBHelper favoriteDBHelper;
    User user;

    public void loadUserInfo() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        sharedPreferencesLoadUser = new SharedPreferencesLoadUser(sharedPreferences);
        user = sharedPreferencesLoadUser.getUser();

    }

    public void createTestFavorite() {
        favoriteDBHelper = new FavoriteDBHelper(this);
        WatchHistory watchHistory = new WatchHistory(user.getId(), "1", "锻刀大赛2","胡大狗", "http://159.75.231.207:9000/red/video/v_2.png");
        favoriteDBHelper.addFavorite(watchHistory);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_video_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUserInfo();
//        createTestFavorite();

        Toolbar toolbar = findViewById(R.id.video_list_toolbar);
        toolbar.setTitle("收藏");
        setSupportActionBar(toolbar);

        // Handle back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity
            }
        });

        // Initialize the list and adapter
        favoriteRecyclerView = findViewById(R.id.history_recycler_view);
        videoItemList = new ArrayList<>();
        videoAdapter = new VideoAdapter(this, videoItemList);

        // Set up RecyclerView
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoriteRecyclerView.setAdapter(videoAdapter);

        // Add dummy data
        populateVideoList();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void populateVideoList() {
        favoriteDBHelper = new FavoriteDBHelper(this);
        List<WatchHistory> favoriteList = favoriteDBHelper.getFavorite(user.getId());
        videoItemList.addAll(favoriteList);

        videoAdapter.notifyDataSetChanged();
    }
}