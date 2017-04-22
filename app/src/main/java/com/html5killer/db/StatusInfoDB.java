package com.html5killer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StatusInfoDB {
    private static final String DATABASE_TABLE = "SCORE";
    public static final String LAST_UPDATE = "updateDate";
    public static final String MIN_DURATION = "duration";
    public static final String NUM_ERRORS = "errors";
    public static final String ROW_ID = "_id";
    private final Context mCtx;
    private SQLiteDatabase mDb;
    private DatabaseHandler mDbHandler;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DatabaseHandler.DATABASE_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public StatusInfoDB(Context ctx) {
        this.mCtx = ctx;
    }

    public StatusInfoDB open() throws SQLException {
        this.mDbHandler = new DatabaseHandler(this.mCtx);
        this.mDb = this.mDbHandler.getWritableDatabase();
        return this;
    }

    public void close() {
        this.mDbHandler.close();
    }

    public long createScore(int id, String update, int duration, int errors) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(ROW_ID, Integer.valueOf(id));
        initialValues.put(LAST_UPDATE, update);
        initialValues.put(MIN_DURATION, Integer.valueOf(duration));
        initialValues.put(NUM_ERRORS, Integer.valueOf(errors));
        return this.mDb.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteScore(long rowId) {
        return this.mDb.delete(DATABASE_TABLE, new StringBuilder("_id=").append(rowId).toString(), null) > 0;
    }

    public Cursor getAllScores() {
        return this.mDb.query(DATABASE_TABLE, new String[]{ROW_ID, LAST_UPDATE, MIN_DURATION, NUM_ERRORS}, null, null, null, null, null);
    }

    public Cursor getScore(int rowId) throws SQLException {
        Cursor mCursor = this.mDb.rawQuery("SELECT _id, updateDate, duration, errors FROM SCORE WHERE _id = " + rowId, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateScore(int id, String update, int duration, int errors) {
        ContentValues args = new ContentValues();
        args.put(ROW_ID, Integer.valueOf(id));
        args.put(LAST_UPDATE, update);
        args.put(MIN_DURATION, Integer.valueOf(duration));
        args.put(NUM_ERRORS, Integer.valueOf(errors));
        return this.mDb.update(DATABASE_TABLE, args, new StringBuilder("_id=").append(id).toString(), null) > 0;
    }
}
