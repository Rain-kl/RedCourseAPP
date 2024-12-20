package com.example.myapplication.model;

public class Score {
    private int id;
    private int userId;
    private int score;

    // 构造函数
    public Score() {
    }

    public Score(int userId, int score) {
        this.userId = userId;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
