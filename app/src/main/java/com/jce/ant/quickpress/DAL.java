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
import android.util.Log;



/**
 * Created by assafbt on 22/12/2015.
 */
public class DAL {
    SQLiteDatabase db;
    DBHelper dbHelper;
    final int minLevel=1;
    final int minComplex = 0;
    final int maxLevel = 10;
    final int maxComplex = 4;
    Cursor crs;




    public DAL(Context c){
        dbHelper = new DBHelper(c);


    }//DAL

    // first init
    public void initRecords(){
        //get db
        db = dbHelper.getWritableDatabase();

        //set data
        ContentValues values;

        int place, l,c;
        int record=0;

        for (l=minLevel;l<=maxLevel;l++) {
            for (c = minComplex; c <= maxComplex; c++) {
                place = getIndex(l,c);

                values = new ContentValues();
                place = getIndex(l, c);
                values.put(BestTime.TimeEntry.LVL_CMPX, place);
                values.put(BestTime.TimeEntry.RECORD, record);

                //insert database
                db.insert(BestTime.TimeEntry.TABLE_NAME, null, values);



                Log.e("INIT", "init record " + record + " to index " + place);

            }
        }
        Log.e("INIT", "init finish ");
        db.close();
        Log.e("INIT", "after close ");

    }

        // update the value of record
    public void updateRecord(int place, int time1)  {
        int timeDB =getRecord(place);
        db = dbHelper.getWritableDatabase();

        if ((timeDB >= time1) || (timeDB == 0)){

           //set data
            ContentValues values = new ContentValues();
            values.put(BestTime.TimeEntry.LVL_CMPX, place);
            values.put(BestTime.TimeEntry.RECORD, time1);

            String where = BestTime.TimeEntry.LVL_CMPX + "=?";
            String[] whereArgs = {place +""};
            db.update(BestTime.TimeEntry.TABLE_NAME, values, where, whereArgs);

            Log.e("updateRecord", "record on " + place + " was update to " + time1);


        }
        else{
            Log.e("updateRecord", "record on " + place + " was not update");
            // do nothing
        }


        db.close();



    }

    // reset the value on place
    public void resetRecord(int place){
        db = dbHelper.getWritableDatabase();

        //set data
        int time1 = 0;
        ContentValues values = new ContentValues();
        values.put(BestTime.TimeEntry.LVL_CMPX, place);
        values.put(BestTime.TimeEntry.RECORD, time1);

        String where = BestTime.TimeEntry.LVL_CMPX + "=?";
        String[] whereArgs = {place +""};
        db.update(BestTime.TimeEntry.TABLE_NAME, values, where, whereArgs);
        db.close();
    }//resetRecord

    // get the Record value at place
    public int getRecord(int place){
        db = dbHelper.getReadableDatabase();

        int recordReturn = 0;
        int idIndex,idRecord,temp;
        String table = BestTime.TimeEntry.TABLE_NAME;
        crs = db.rawQuery("SELECT * FROM " + table, null);

        idRecord = crs.getColumnIndex(BestTime.TimeEntry.RECORD);
        idIndex = crs.getColumnIndex(BestTime.TimeEntry.LVL_CMPX);


        while (crs.moveToNext()) {
               temp = crs.getInt(idIndex);
            if (temp == place) {
                   recordReturn = crs.getInt(idRecord);
                   break;
               }
               Log.e("getRecord", "get record " + recordReturn + " from index " + place);
           }


        db.close();
        return recordReturn;
    }//getRecord

    //Convert To Time  Format
    public String convertToTimeStringFormat(int dbRecord ){
        int secs,milliseconds;
        secs = (int) (dbRecord / 1000);
        secs = secs % 60;
        milliseconds = (int) (dbRecord % 1000);

        String dBT = String.format("%02d", secs) + ":" + String.format("%03d", milliseconds);
        Log.e("convertTime", "convert " + dbRecord);
        return dBT;

    }//convertToTimeStringFormat


    public int getIndex(int level,int complex){

        return level*10 + complex;
    }


    // check if the DB is empty
    public boolean isBDEmpty() {
        //get db
        db = dbHelper.getReadableDatabase();
        crs = db.rawQuery("SELECT * FROM " + BestTime.TimeEntry.TABLE_NAME, null);
        if (crs.getCount() > 0) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }//isBDEmpty
}
