package com.html5killer.db;

import android.content.Context;
import android.database.Cursor;

public class DBHelper {
    public static void setScore(Context context, int id, String updateDate, int duration, int errors) {
        try {
            StatusInfoDB siDB = new StatusInfoDB(context);
            siDB.open();
            Cursor dbScoreCursor = siDB.getScore(id);
            if (dbScoreCursor != null) {
                if (dbScoreCursor.getCount() > 0) {
                    siDB.updateScore(id, updateDate, duration, errors);
                } else {
                    siDB.createScore(id, updateDate, duration, errors);
                }
                dbScoreCursor.close();
            }
            siDB.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
