package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    // 数据库版本
    private static final int DATABASE_VERSION = 1;

    // 数据库名称
    private static final String DATABASE_NAME = "AppDatabase";

    //VideoOBJ
    public static final String KEY_VIDEO_TITLE = "video_title";
    public static final String KEY_VIDEO_DESC = "video_desc";
    public static final String KEY_VIDEO_THUMBNAIL = "video_thumbnail";
    public static final String KEY_CONTENT_ID = "content_id"; // 假设您有一个内容ID


    // 用户信息表名
    public static final String TABLE_USERS = "users";
    // 用户信息表列名
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_REGISTER_DATE = "register_date"; // 添加注册时间字段
    public static final String KEY_USER_FOREIGN_ID = "user_id"; // 外键关联到用户表


    // ... 其他个人信息字段

    // 观看历史表名
    public static final String TABLE_WATCH_HISTORY = "watch_history";
    // 观看历史表列名
    public static final String KEY_HISTORY_ID = "id";
    public static final String KEY_WATCH_DATE = "watch_date";


    // 收藏表名
    public static final String TABLE_FAVORITES = "favorites";
    // 收藏表列名
    public static final String KEY_FAVORITE_ID = "id";
    public static final String KEY_FAVORITE_DATE = "favorite_date";

    // 创建用户表的 SQL 语句
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USERNAME + " TEXT NOT NULL,"
            + KEY_PASSWORD + " TEXT NOT NULL,"
            + KEY_REGISTER_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + KEY_PHONE + " TEXT UNIQUE)";

    // 创建观看历史表的 SQL 语句
    private static final String CREATE_TABLE_WATCH_HISTORY = "CREATE TABLE " + TABLE_WATCH_HISTORY + "("
            + KEY_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_FOREIGN_ID + " INTEGER,"
            + KEY_CONTENT_ID + " TEXT,"
            + KEY_WATCH_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," // 观看时间
            + KEY_VIDEO_TITLE + " TEXT,"
            + KEY_VIDEO_DESC + " TEXT,"
            + KEY_VIDEO_THUMBNAIL + " TEXT,"
            + "FOREIGN KEY(" + KEY_USER_FOREIGN_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_USER_ID + ")"
            + ")";

    // 创建收藏表的 SQL 语句
    private static final String CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES + "("
            + KEY_FAVORITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_FOREIGN_ID + " INTEGER,"
            + KEY_CONTENT_ID + " TEXT,"
            + KEY_FAVORITE_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," // 收藏时间
            + KEY_VIDEO_TITLE + " TEXT,"
            + KEY_VIDEO_DESC + " TEXT,"
            + KEY_VIDEO_THUMBNAIL + " TEXT,"
            + "FOREIGN KEY(" + KEY_USER_FOREIGN_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_USER_ID + ")"
            + ")";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建表
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_WATCH_HISTORY);
        db.execSQL(CREATE_TABLE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果需要升级数据库，可以在这里处理
        // 通常是删除旧表，然后创建新表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCH_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

}
