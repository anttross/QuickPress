/*
 * *
 *  * Created by assafbt on <Date>
 *
 */

package com.jce.ant.quickpress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by assafbt on 22/12/2015.
 */
public class DAL {
    DBHelper dbHelper;

    // do something

    public DAL(Context c){
        dbHelper = new DBHelper(c);


    }//DAL

    public void initRecords(int level, int complex){
        //get db
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //set data
        ContentValues values = new ContentValues();

        //init -> record=empty
        int place, i,j;
        for (i=1;i<=level;i++)
            for (j=0;j<=complex;j++){
                place=getLvlCmpx(level, complex);
                values.put(BestTime.TimeEntry.LVL_CMPX, place);
                values.put(BestTime.TimeEntry.RECORD, "empty");
                //insert database
                db.insert(BestTime.TimeEntry.TABLE_NAME, null, values);
            }
        db.close();
    }


    public void updateRecord(int lvl_cmpx, String time1){
        //get db
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //set data
        ContentValues values = new ContentValues();
        values.put(BestTime.TimeEntry.LVL_CMPX, lvl_cmpx);
        values.put(BestTime.TimeEntry.RECORD, time1);

        String where = BestTime.TimeEntry.LVL_CMPX + "=?";
        String[] whereArgs = {lvl_cmpx +""};


        // check for best record
        int timeDB =getRecord(lvl_cmpx);

        //int time = Integer.parseInt(time1);
        if ((timeDB) > Integer.parseInt(time1)){
            //insert data
            db.insert(BestTime.TimeEntry.TABLE_NAME, null, values);
            db.close();
        }
        else{
            // do nothing
        }

        db.update(BestTime.TimeEntry.TABLE_NAME, values, where, whereArgs);
        db.close();



    }


    public int getRecord(int lvl_cmpx){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // get record from
        Cursor crs= db.rawQuery("SELECT * FROM " + BestTime.TimeEntry.TABLE_NAME, null);

        int idIndex = crs.getColumnIndex(BestTime.TimeEntry.LVL_CMPX);
        //String timeDB = crs.getString(lvl_cmpx);
        crs.close();

        return idIndex;

    }


    public int getLvlCmpx(int level,int complex){

        return level*10 + complex;
    }



    public void addRecords(int level, int complex){
        //get db
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //set data
        ContentValues values = new ContentValues();

        //init -> record=empty
        int place, i,j;
        for (i=1;i<=level;i++)
            for (j=0;j<=complex;j++){
                place=getLvlCmpx(level, complex);
                values.put(BestTime.TimeEntry.LVL_CMPX, place);
                values.put(BestTime.TimeEntry.RECORD, (j*i)+"");
                //insert database
                db.insert(BestTime.TimeEntry.TABLE_NAME, null, values);
            }
        db.close();
    }

}
