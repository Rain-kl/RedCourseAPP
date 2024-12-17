package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.WatchHistory;

import java.util.ArrayList;
import java.util.List;

public class WatchHistoryDBHelper extends MyDBHelper{

    public WatchHistoryDBHelper(Context context) {
        super(context);
    }
    // 添加观看历史
    public long addWatchHistory(WatchHistory watchHistory) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_FOREIGN_ID, watchHistory.getUserId());
        values.put(KEY_CONTENT_ID, watchHistory.getContentId());
        // ... 添加其他观看历史字段

        long id = db.insert(TABLE_WATCH_HISTORY, null, values);
        db.close();
        return id;
    }

    // 获取用户的观看历史
    public List<WatchHistory> getWatchHistory(int userId) {
        List<WatchHistory> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WATCH_HISTORY, new String[] { KEY_HISTORY_ID, KEY_USER_FOREIGN_ID, KEY_CONTENT_ID, KEY_WATCH_DATE
                // ... 其他字段
        }, KEY_USER_FOREIGN_ID + "=?", new String[] { String.valueOf(userId) }, null, null, KEY_WATCH_DATE + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                WatchHistory history = new WatchHistory();
                history.setId(cursor.getInt(0));
                history.setUserId(cursor.getInt(1));
                history.setContentId(cursor.getString(2));
                history.setWatchDate(cursor.getString(3));
                // ... 设置其他字段

                historyList.add(history);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return historyList;
    }



}
