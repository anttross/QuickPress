/*
 * *
 *  * Created by assafbt on <Date>
 *
 */

package com.jce.ant.quickpress;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by assafbt on 22/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME ="bestTimeDB.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "+BestTime.TimeEntry.TABLE_NAME+" ( "+
                        BestTime.TimeEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        BestTime.TimeEntry.LVL_CMPX+" INTEGER"+
                        BestTime.TimeEntry.RECORD+" INTEGER DEFAULT 0);"
        );
        //int time = Integer.parseInt(record);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+BestTime.TimeEntry.TABLE_NAME);
        onCreate(db);
    }
}
