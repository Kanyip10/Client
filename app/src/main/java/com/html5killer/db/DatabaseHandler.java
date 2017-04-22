package com.html5killer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_SCORE = "create table SCORE (_id integer primary key, updateDate TEXT,duration INTEGER,errors INTEGER);";
    public static final String DATABASE_NAME = "FindMe.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close() {
        try {
            super.close();
        } catch (Exception e) {
        }
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SCORE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SCORE;");
        onCreate(db);
    }
}
