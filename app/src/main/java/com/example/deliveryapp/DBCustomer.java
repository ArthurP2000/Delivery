package com.example.deliveryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBCustomer extends SQLiteOpenHelper {

    public static final String DBNAME = "Login DB";

    public DBCustomer(Context context) {
        super(context, "Login DB", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase CusDB) {
        CusDB.execSQL("create Table users(fullName TEXT primary key, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase CusDB, int i, int i1) {

        CusDB.execSQL("drop Table if exists users");

    }
    public Boolean insertData(String fullName, String password) {
        SQLiteDatabase CusDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullName", fullName);
        contentValues.put("password", password);
        long result = CusDB.insert("users", null, contentValues);
        if (result==-1) return false;
        else
            return true;
    }
    public Boolean checkusername(String fullName) {
        SQLiteDatabase CusDB = this.getWritableDatabase();
        Cursor cursor = CusDB.rawQuery("Select * from users where fullName = ?", new String[] {fullName});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checknamepassword(String fullName, String password) {
        SQLiteDatabase CusDB = this.getWritableDatabase();
        Cursor cursor = CusDB.rawQuery("Select * from users where fullName = ? and password = ?", new String[] {fullName,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }
}
