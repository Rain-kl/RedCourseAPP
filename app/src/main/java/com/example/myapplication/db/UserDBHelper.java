package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.User;

public class UserDBHelper extends MyDBHelper {

    public UserDBHelper(Context context) {
        super(context);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_NAME, user.getName());
        // ... 添加其他个人信息

        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    // 根据用户ID获取用户
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USER_ID, KEY_USERNAME, KEY_PHONE, KEY_NAME},
                KEY_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(KEY_USER_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            user.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            // ... 设置其他字段
        }

        cursor.close();
        db.close();
        return user;
    }

    // 获取用户 (根据用户手机号获取用户)
    public User getUserByPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USER_ID, KEY_USERNAME, KEY_PASSWORD, KEY_PHONE, KEY_NAME},
                KEY_PHONE + "=?", new String[]{phone}, null, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setPhone(cursor.getString(3));
            user.setName(cursor.getString(4));
            // ... 设置其他字段
            cursor.close();
        }
        db.close();
        return user;
    }


    //检查电话是否存在
    public boolean isPhoneExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USER_ID},
                KEY_PHONE + "=?", new String[]{email}, null, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // 更新用户信息
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_NAME, user.getName());
        // ... 更新其他个人信息

        return db.update(TABLE_USERS, values, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    // 删除用户
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }
}
