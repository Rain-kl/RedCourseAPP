package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ScoreDBHelper extends MyDBHelper {
    public ScoreDBHelper(Context context) {
        super(context);
    }


    public int getScore(int userId) {
        int score = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SCORE, new String[]{
                KEY_SCORE_ID, KEY_USER_FOREIGN_ID, KEY_SCORE
        }, KEY_USER_FOREIGN_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            score = cursor.getInt(2);
        }
        cursor.close();
        return score;
    }

    public void addScore(int userId, int score) {
        int originalScore = getScore(userId);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_FOREIGN_ID, userId);
        values.put(KEY_SCORE, originalScore + score);

        db.update(TABLE_SCORE, values, KEY_USER_FOREIGN_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
    }

    public void reduceScore(int userId, int score) {
        int originalScore = getScore(userId);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_FOREIGN_ID, userId);
        values.put(KEY_SCORE, originalScore - score);

        db.update(TABLE_SCORE, values, KEY_USER_FOREIGN_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
    }


}
