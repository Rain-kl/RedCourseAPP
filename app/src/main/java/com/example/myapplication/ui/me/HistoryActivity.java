package com.example.myapplication.ui.me;

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
import com.example.myapplication.db.WatchHistoryDBHelper;
import com.example.myapplication.model.User;
import com.example.myapplication.model.WatchHistory;
import com.example.myapplication.utils.SharedPreferencesLoadUser;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView historyRecyclerView;
    private VideoAdapter videoAdapter;
    private List<WatchHistory> videoItemList;

    SharedPreferences sharedPreferences;
    SharedPreferencesLoadUser sharedPreferencesLoadUser;
    WatchHistoryDBHelper watchHistoryDBHelper;
    User user;

    public void loadUserInfo() {
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        sharedPreferencesLoadUser = new SharedPreferencesLoadUser(sharedPreferences);
        user = sharedPreferencesLoadUser.getUser();

    }

    public void createTestWatchHistory() {
        watchHistoryDBHelper = new WatchHistoryDBHelper(this);
        WatchHistory watchHistory = new WatchHistory(user.getId(),"1", "锻刀大赛", "http://159.75.231.207:9000/red/video/v_1.png");
        watchHistoryDBHelper.addWatchHistory(watchHistory);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.history_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Handle back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity
            }
        });
        loadUserInfo();
        // Initialize the list and adapter
        historyRecyclerView = findViewById(R.id.history_recycler_view);
        videoItemList = new ArrayList<>();
        videoAdapter = new VideoAdapter(this, videoItemList);

        // Set up RecyclerView
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(videoAdapter);

        // Add dummy data
        populateVideoList();
    }


    private void populateVideoList() {
        watchHistoryDBHelper = new WatchHistoryDBHelper(this);
        List<WatchHistory> historyList = watchHistoryDBHelper.getWatchHistory(user.getId());
        videoItemList.addAll(historyList);

        videoAdapter.notifyDataSetChanged();
    }
}