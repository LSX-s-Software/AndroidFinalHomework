package com.lsx.finalhomework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context) {
        super(context, "bookstore_app.db", null, 2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE account" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username VARCHAR(50)," +
                "password CHAR(32))");
        db.execSQL("CREATE TABLE book" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category TINYINT," +
                "name VARCHAR(25)," +
                "img_url VARCHAR(100)," +
                "author VARCHAR(15)," +
                "isbn CHAR(13)," +
                "description TEXT," +
                "price DOUBLE)");
    }

}