package com.example.myapplication.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String phone;
    private String name;
    // ... 其他个人信息字段

    // 构造函数
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(String username, String password, String phone, String name) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.name = name;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ... (为其他字段添加 getters 和 setters)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}