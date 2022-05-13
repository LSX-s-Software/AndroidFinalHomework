package com.lsx.finalhomework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lsx.finalhomework.entities.BookService;

public class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context) {
        super(context, "bookstore_app.db", null, 3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS book");
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 账号表
        db.execSQL("CREATE TABLE account" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username VARCHAR(50)," +
                "password CHAR(32))");
        // 书本表
        db.execSQL("CREATE TABLE book" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category TINYINT," +
                "name VARCHAR(25)," +
                "img_url VARCHAR(100)," +
                "author VARCHAR(15)," +
                "isbn CHAR(13)," +
                "description TEXT," +
                "price DOUBLE)");
        // 购物车表
        db.execSQL("CREATE TABLE cart (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "account_id INTEGER NOT NULL," +
                "book_id INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "CONSTRAINT cart_book_id_book_id FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE," +
                "CONSTRAINT cart_account_id_account_id FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE)");
        // 添加初始数据
        BookService.initData(db);
    }

}