package com.example.myapplication.model;

public class WatchHistory {
    private int id;
    private int userId;
    private String contentId;
    private String watchDate;
    // ... 其他观看历史字段

    // 构造函数
    public WatchHistory() {
    }

    public WatchHistory(int userId, String contentId) {
        this.userId = userId;
        this.contentId = contentId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(String watchDate) {
        this.watchDate = watchDate;
    }

    // ... (为其他字段添加 getters 和 setters)
}