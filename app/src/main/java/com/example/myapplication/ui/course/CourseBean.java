package com.example.myapplication.ui.course;

public class CourseBean {
    private static String title;
    private static String id;
    private static String desc;

    public CourseBean(String id, String title, String desc) {
        CourseBean.id = id;
        CourseBean.title = title;
        CourseBean.desc = desc;
    }

    public static String getTitle() {
        return title;
    }

    public static String getId() {
        return id;
    }

    public static String getDesc() {
        return desc;
    }

}
