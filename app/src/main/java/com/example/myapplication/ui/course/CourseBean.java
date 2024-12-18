package com.example.myapplication.ui.course;

public class CourseBean {
    private String title;
    private String imgUrl;


    public CourseBean(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imgUrl;
    }
}
