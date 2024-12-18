package com.example.myapplication.model;

public class Favorite {
    private int id;
    private int userId;
    private String contentId;
    // ... 其他收藏相关字段，例如收藏时间等

    // 构造函数
    public Favorite() {
    }

    public Favorite(int userId, String contentId) {
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

}