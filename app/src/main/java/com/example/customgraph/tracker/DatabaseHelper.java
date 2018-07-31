package com.example.customgraph.tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 20-06-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="data.db";
    public static final String TABLE_NAME="info";
    public static final String TABLE1_NAME="time";
    public static final Integer DATABASE_VERSION=3;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info ( ID INTEGER PRIMARY KEY AUTOINCREMENT,day INTEGER,month INTEGER,year INTEGER,exp TEXT)");
        db.execSQL("create table time (ID INTEGER PRIMARY KEY AUTOINCREMENT, hour INTEGER,min INTEGER )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS info");
        db.execSQL("DROP TABLE IF EXISTS time");
        onCreate(db);
    }
    public void addData(int day,int month,int year,String exp){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("day",day);
        contentValues.put("month",month);
        contentValues.put("year",year);
        contentValues.put("exp",exp);
        db.insert(TABLE_NAME,null,contentValues);

    }
    public Cursor SingleData(int day,int month,int year){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from info where day="+day+" AND month="+month+" AND year="+year,null);
        return res;
    }
    public void updateData(int id,String exp){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("exp",exp);
        db.update(TABLE_NAME,contentValues,"ID="+id,null);
    }
    public Cursor getMonthAnalysis(int month,int year){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from info where month="+month+" AND year="+year,null);
        return res;
    }

    public void addTime(int hour,int min){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("hour",hour);
        contentValues.put("min",min);
        db.insert(TABLE1_NAME,null,contentValues);
    }
    public Cursor getTime(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from time",null);
        return res;
    }
    public void updateTime(int id,int hour,int min){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("hour",hour);
        contentValues.put("min",min);
        db.update(TABLE1_NAME,contentValues,"ID="+id,null);
    }
}
