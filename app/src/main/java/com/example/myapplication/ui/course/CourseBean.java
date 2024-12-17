package com.example.myapplication.ui.course;
public class CourseBean {
    private String title;
    private int imgResId;

    public CourseBean(int imgResId, String title) {
        this.imgResId = imgResId;
        this.title = title;
    }
// 构造函数、getter 和 setter 方法...

    public String getTitle() {
        return title;
    }

    public int getImgResId() {
        return imgResId;
    }
}
