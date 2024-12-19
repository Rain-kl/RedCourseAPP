package com.example.myapplication.db.course;

public class CourseBean {
    private static String title;
    private String imgUrl;
    private static String desc; // 新增属性

    public CourseBean(String imgUrl, String title, String desc) {
        this.imgUrl = imgUrl;
        CourseBean.title = title;
        CourseBean.desc = desc;
    }
    public static String getTitle() {
        return title;
    }
    public String getImageUrl() {
        return imgUrl;
    }
    public static String getDesc() {
        return desc;
    }
}
