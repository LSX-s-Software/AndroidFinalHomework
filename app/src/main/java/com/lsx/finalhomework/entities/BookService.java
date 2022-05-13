package com.lsx.finalhomework.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lsx.finalhomework.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class BookService extends MyDBHelper {

    private static final List<Book> defaultData = new ArrayList<Book>() {{
        add(new Book(1, Book.Category.COMPUTER, "JavaScript 权威指南", "https://img11.360buyimg.com/n1/jfs/t1/168129/39/12442/160552/604f0770Ea958d39e/eb8ded5b1973f8dc.jpg", "[美] David Flanagan", "9787111677222", "本书介绍JavaScript语言和由浏览器与Node实现的JavaScript API。本书适合有一定编程经验、想学习JavaScript读者，也适合已经在使用JavaScript但希望更深入地理解进而真正掌握这门语言的程序员。\n本书的目标是全面地讲解JavaScript语言，对JavaScript程序中可能用到的重要的客户端API和服务器端API提供深入的介绍。本书篇幅较长，内容非常详尽，相信认真研究本书的读者都能获益良多。", 95.9));
        add(new Book(2, Book.Category.COMPUTER, "算法导论", "https://upload-images.jianshu.io/upload_images/12795956-e4de57a955ce6ab9?imageMogr2/auto-orient/strip|imageView2/2/w/338", "[美] Thomas H. Cormen", "9787119124094", "本书提供了对当代计算机算法研究的一个全面、综合性的介绍。全书共八部分，内容涵盖基础知识、排序和顺序统计量、数据结构、高级设计和分析技术、高级数据结构、图算法、算法问题选编，以及数学基础知识。书中深入浅出地介绍了大量的算法及相关的数据结构，以及用于解决一些复杂计算问题的高级策略（如动态规划、贪心算法、摊还分析等），重点在于算法的分析与设计。对于每一个专题，作者都试图提供目前最新的研究成果及样例解答，并通过清晰的图示来说明算法的执行过程。", 83.20));
        add(new Book(3, Book.Category.COMPUTER, "深入理解计算机系统", "https://upload-images.jianshu.io/upload_images/12795956-634091270bae3f48?imageMogr2/auto-orient/strip|imageView2/2/w/587", "[美] Randal E.Bryant, David R.O'Hallaron", "9787111544937", "主要介绍了计算机系统的基本概念，包括最底层的内存中的数据表示、流水线指令的构成、虚拟存储器、编译系统、动态加载库，以及用户应用等。书中提供了大量实际操作，可以帮助读者更好地理解程序执行的方式，改进程序的执行效率。程序员的视角全面讲解了计算机系统，深入浅出地介绍了处理器、编译器、操作系统和网络环境，是这一领域的权威之作。", 139.0));
    }};

    public BookService(Context context) {
        super(context);
    }

    private Book deserializer(Cursor cursor) {
        Book b = new Book();
        b.setId(cursor.getInt((int) cursor.getColumnIndex("id")));
        b.setName(cursor.getString((int) cursor.getColumnIndex("name")));
        b.setCategory(Book.Category.values()[cursor.getInt((int) cursor.getColumnIndex("category"))]);
        b.setImgUrl(cursor.getString((int) cursor.getColumnIndex("img_url")));
        b.setAuthor(cursor.getString((int) cursor.getColumnIndex("author")));
        b.setISBN(cursor.getString((int) cursor.getColumnIndex("isbn")));
        b.setDescription(cursor.getString((int) cursor.getColumnIndex("description")));
        b.setPrice(cursor.getDouble((int) cursor.getColumnIndex("price")));
        return b;
    }

    public List<Book> getList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("book", null, null, null, null, null, null);
        List<Book> result = new ArrayList<>();
        if (cursor.getCount() != 0)
            while (cursor.moveToNext())
                result.add(deserializer(cursor));
        cursor.close();
        db.close();
        return result;
    }

    public Book getBook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("book", null, "id=?", new String[] {String.valueOf(id)},null,null,null);
        cursor.moveToNext();
        Book b = deserializer(cursor);
        cursor.close();
        db.close();
        return b;
    }

    public void initData() {
        SQLiteDatabase db = this.getWritableDatabase();
        initData(db);
        db.close();
    }

    public static void initData(SQLiteDatabase db) {
        for (int i = 0; i < 3; i++) {
            for (Book b : defaultData) {
                ContentValues values = new ContentValues();
                values.put("name", b.getName());
                values.put("category", b.getCategory().ordinal());
                values.put("img_url", b.getImgUrl());
                values.put("author", b.getAuthor());
                values.put("isbn", b.getISBN());
                values.put("description", b.getDescription());
                values.put("price", b.getPrice());
                db.insert("book", null, values);
            }
        }
    }
}
