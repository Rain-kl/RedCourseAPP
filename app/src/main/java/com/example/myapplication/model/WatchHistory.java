package com.example.myapplication.model;

public class WatchHistory {
    private int id;
    private int userId;
    private String contentId;
    private String title;
    private String desc;
    private String thumbnailUrl;
    private String watchData;


    // ... 其他观看历史字段
    public WatchHistory() {
    }

    // 构造函数
    public WatchHistory(int userId, String contentId, String title,String desc, String thumbnailUrl) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.userId = userId;
        this.contentId = contentId;
        this.desc = desc;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getWatchData() {
        return watchData;
    }

    public void setWatchData(String watchData) {
        this.watchData = watchData;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}