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
import android.widget.Toast;


/**
 * Created by assafbt on 22/12/2015.
 */
public class DAL {
    DBHelper dbHelper;
    final int maxLevel = 10;
    final int maxComplex = 4;


    // do something

    public DAL(Context c){
        dbHelper = new DBHelper(c);


    }//DAL

    public void initRecords(int level, int complex){
        //get db
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //set data
        ContentValues values;

        //init -> record=empty
        int place, i,j;
        for (i=1;i<=maxLevel;i++) {
            for (j = 0; j <= maxComplex; j++) {
                values = new ContentValues();
                place = getLvlCmpx(level, complex);
                values.put(BestTime.TimeEntry.LVL_CMPX, place);
                values.put(BestTime.TimeEntry.RECORD, "0");
                //values.putNull(BestTime.TimeEntry.RECORD);
                //insert database
                db.insert(BestTime.TimeEntry.TABLE_NAME, null, values);
                



                Log.e("INIT", "palce " + place);
            }
        }
        Toast.makeText(MainActivity.getContext()," INIT FINISH", Toast.LENGTH_SHORT).show();
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
        if ((timeDB) > Integer.parseInt(time1) && (Integer.parseInt(time1) != 0)){
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



    public boolean isBDEmpty(){
        //get db
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //set data
        ContentValues values = new ContentValues();
        boolean empty=true;

        int place,record;
        int i=1,j=0;
        for (i=1;i<=maxLevel;i++)
            for (j=0;j<=maxComplex;j++){
                place=getLvlCmpx(i, j);
                record = getRecord(place);

                if ((record > 0 )){
                    empty= false;
                }


                Log.e("exist", "palce " + place);
            }
        db.close();
        if (((i+j)== 50) && (!empty)) {
            return false;
        }
        return false;
    }

}
