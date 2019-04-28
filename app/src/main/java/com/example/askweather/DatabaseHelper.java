package com.example.askweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="Weather_Table";
    private static final String TABLE_NAME="Weather";
    private static final String COL_1="CITY";
    private static final String COL_2="TEMPERATURE";
    private static final String COL_3="DESCRIPTION";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+TABLE_NAME+"(CITY TEXT PRIMARY KEY,TEMPERATURE TEXT,DESCRIPTION TEXT )");
    }

    public boolean insertData(String city,String temperature,String description){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,city);
        contentValues.put(COL_2,temperature);
        contentValues.put(COL_3,description);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;

    }
    public Cursor getData(String city){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME+" where CITY=city ",null);
        return res;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


}
