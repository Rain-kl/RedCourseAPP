package com.example.myapplication.ui.course;

public class CourseBean {
    private String title;
    private String id;
    private String desc;

    public CourseBean(String id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
