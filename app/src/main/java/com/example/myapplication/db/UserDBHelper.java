package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.User;

public class UserDBHelper extends MyDBHelper {

    public UserDBHelper(Context context) {
        super(context);
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
    }

    public boolean addUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, user.getUsername());
            values.put(KEY_PASSWORD, user.getPassword());
            values.put(KEY_PHONE, user.getPhone());
            // ... 添加其他个人信息

            long id = db.insert(TABLE_USERS, null, values);
            db.close();
            return id != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据用户ID获取用户
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USER_ID, KEY_USERNAME, KEY_PASSWORD, KEY_PHONE, KEY_REGISTER_DATE},
                KEY_USER_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setPhone(cursor.getString(3));
            user.setRegisterDate(cursor.getString(4));
            // ... 设置其他字段
        }

        cursor.close();
        db.close();
        return user;
    }

    // 获取用户 (根据用户手机号获取用户)
    public User getUserByPhone(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USER_ID, KEY_USERNAME, KEY_PASSWORD, KEY_PHONE, KEY_REGISTER_DATE},
                KEY_PHONE + "=?", new String[]{phone}, null, null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setPhone(cursor.getString(3));
            user.setRegisterDate(cursor.getString(4));
            cursor.close();
        }
        db.close();
        return user;
    }


    //检查电话是否存在
    public boolean isPhoneExists(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USER_ID},
                KEY_PHONE + "=?", new String[]{phone}, null, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // 更新用户信息
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PHONE, user.getPhone());
        // ... 更新其他个人信息

        db.update(TABLE_USERS, values, KEY_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    // 删除用户
    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
    }
}
