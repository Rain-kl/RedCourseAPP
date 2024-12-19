package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.model.WatchHistory;

public class FavoriteDBHelper extends MyDBHelper {
    public FavoriteDBHelper(Context context) {
        super(context);
    }

    // 添加观看历史
    public void addFavorite(WatchHistory watchHistory) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_FOREIGN_ID, watchHistory.getUserId());
        values.put(KEY_CONTENT_ID, watchHistory.getContentId());
        values.put(KEY_VIDEO_TITLE, watchHistory.getTitle());
        values.put(KEY_VIDEO_THUMBNAIL, watchHistory.getThumbnailUrl());
        values.put(KEY_VIDEO_DESC, watchHistory.getDesc());

        // ... 添加其他观看历史字段

        long id = db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }

    // 获取用户的观看历史
    public List<WatchHistory> getFavorite(int userId) {
        List<WatchHistory> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{
                KEY_FAVORITE_ID, KEY_USER_FOREIGN_ID, KEY_CONTENT_ID, KEY_FAVORITE_DATE, KEY_VIDEO_TITLE, KEY_VIDEO_THUMBNAIL, KEY_VIDEO_DESC
        }, KEY_USER_FOREIGN_ID + "=?", new String[]{String.valueOf(userId)}, null, null, KEY_FAVORITE_DATE + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                WatchHistory favorite = new WatchHistory();
                favorite.setId(cursor.getInt(0));
                favorite.setUserId(cursor.getInt(1));
                favorite.setContentId(cursor.getString(2));
                favorite.setWatchData(cursor.getString(3));
                favorite.setTitle(cursor.getString(4));
                favorite.setThumbnailUrl(cursor.getString(5));
                favorite.setDesc(cursor.getString(6));

                // ... 设置其他字段

                historyList.add(favorite);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return historyList;
    }

    public void deleteFavorite(int userId, String contentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_USER_FOREIGN_ID + " = ? AND " + KEY_CONTENT_ID + " = ?", new String[]{String.valueOf(userId), contentId});
        db.close();
    }


}
