package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.model.Favorite;

public class FavoriteDBHelper extends MyDBHelper {
    public FavoriteDBHelper(Context context) {
        super(context);
    }

    public long addFavorite(Favorite favorite) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_FOREIGN_ID, favorite.getUserId());
        values.put(KEY_CONTENT_ID, favorite.getContentId());
        // ... 添加其他收藏相关字段

        long id = db.insert(TABLE_FAVORITES, null, values);
        db.close();
        return id;
    }

    // 获取用户的收藏列表
    public List<Favorite> getFavorites(int userId) {
        List<Favorite> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{
                KEY_FAVORITE_ID, KEY_USER_FOREIGN_ID, KEY_CONTENT_ID, KEY_FAVORITE_DATE
                // ... 其他字段
        }, KEY_USER_FOREIGN_ID + "=?", new String[]{String.valueOf(userId)}, null, null, KEY_FAVORITE_DATE + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite();
                favorite.setId(cursor.getInt(0));
                favorite.setUserId(cursor.getInt(1));
                favorite.setContentId(cursor.getString(2));
                // ... 设置其他字段

                favoriteList.add(favorite);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }

    // 检查某个内容是否已被用户收藏
    public boolean isContentFavorited(int userId, String contentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAVORITES, new String[]{KEY_FAVORITE_ID},
                KEY_USER_FOREIGN_ID + "=? AND " + KEY_CONTENT_ID + "=?",
                new String[]{String.valueOf(userId), contentId}, null, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // 取消收藏
    public void deleteFavorite(int userId, String contentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITES, KEY_USER_FOREIGN_ID + "=? AND " + KEY_CONTENT_ID + "=?",
                new String[]{String.valueOf(userId), contentId});
        db.close();
    }
}
