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
        add(new Book(2, Book.Category.HISTORY, "全球通史 从史前史到21世纪", "https://img13.360buyimg.com/n1/jfs/t1/164529/6/24944/100360/61cbb77aE8e87cc5e/ca81185079f535fb.jpg", "[美] L. S. Stavrianos", "10076998", "《全球通史 从史前史到21世纪（上下册 第7版 修订版 中文版 赠地图 套装共2册）》分八个部分，四十四个章节，主要讲述了世界历史的进化，世界文明的发展及其对现代社会的影响。作者着眼于全球，侧重于那些有影响的、促进历史发展的历史事件，其中包括原始社会、欧亚大陆的古代文明、欧亚大陆的原始文明、欧亚以外的世界、地区分割后的世界、西方世界的崛起、西方人统治的世界、西方的衰落与成功等八个主要的部分。重点突出，主题鲜明，着重有三个主要的问题，即（1）欧洲扩张的根源；（2）欧洲扩张前的儒家文化、穆斯林和其它非欧洲的世界；（3）欧洲扩张的各个历史时期：伊比利亚时期，荷兰、法国、英国时期，沙俄时期。", 64.00));
        add(new Book(3, Book.Category.NOVEL, "红楼梦", "https://img10.360buyimg.com/n1/jfs/t1/63654/33/2304/258890/5d0a144cEbfdd8b73/b6a56c1a8095ab1d.jpg", "[清] 曹雪芹", "9787020002207", "《红楼梦》塑造了众多的人物形象，他们各自具有自己独特而鲜明的个性特征，成为不朽的艺术典型，在中国文学史和世界文学史上永远放射着奇光异彩。《红楼梦》是一部具有高度思想性和高度艺术性的伟大作品，从《红楼梦》反映的思想倾向来看，作者具有初步的民主主义思想，他对现实社会包括宫廷及官场的黑暗，封建贵族阶级及其家庭的腐朽，封建的科举制度、婚姻制度、奴婢制度、等级制度，以及与此相适应的社会统治思想即孔孟之道和程朱理学、社会道德观念等等，都进行了深刻的批判并且提出了朦胧的带有初步民主主义性质的理想和主张。这些理想和主张正是当时正在滋长的资本主义经济萌芽因素的曲折反映。", 39.0));
        add(new Book(4, Book.Category.COMPUTER, "算法导论", "https://upload-images.jianshu.io/upload_images/12795956-e4de57a955ce6ab9?imageMogr2/auto-orient/strip|imageView2/2/w/338", "[美] Thomas H. Cormen", "9787119124094", "本书提供了对当代计算机算法研究的一个全面、综合性的介绍。全书共八部分，内容涵盖基础知识、排序和顺序统计量、数据结构、高级设计和分析技术、高级数据结构、图算法、算法问题选编，以及数学基础知识。书中深入浅出地介绍了大量的算法及相关的数据结构，以及用于解决一些复杂计算问题的高级策略（如动态规划、贪心算法、摊还分析等），重点在于算法的分析与设计。对于每一个专题，作者都试图提供目前最新的研究成果及样例解答，并通过清晰的图示来说明算法的执行过程。", 83.20));
        add(new Book(5, Book.Category.COMPUTER, "深入理解计算机系统", "https://upload-images.jianshu.io/upload_images/12795956-634091270bae3f48?imageMogr2/auto-orient/strip|imageView2/2/w/587", "[美] Randal E.Bryant, David R.O'Hallaron", "9787111544937", "主要介绍了计算机系统的基本概念，包括最底层的内存中的数据表示、流水线指令的构成、虚拟存储器、编译系统、动态加载库，以及用户应用等。书中提供了大量实际操作，可以帮助读者更好地理解程序执行的方式，改进程序的执行效率。程序员的视角全面讲解了计算机系统，深入浅出地介绍了处理器、编译器、操作系统和网络环境，是这一领域的权威之作。", 139.0));
        add(new Book(6, Book.Category.HISTORY, "全球史学史", "https://img14.360buyimg.com/n1/jfs/t1/109041/13/29007/76908/627e28a6E1e672f71/e09959402fb38841.jpg", "[美] Georg G. Iggers 等", "9787301304891", "本书是一本采用比较眼光和全球视角的史学史著作，将18世纪中叶以来全球史学的发展置于政治、社会和文化的背景中加以讨论，不仅研究西方的史学流变，也考察亚洲、中东，及世界其他地区的史学发展，并提供了关于全球化时代历史书写现状的叙述。第二版更新了有关拉丁美洲史学、非洲史学、全球史、环境史、性别史，以及俄罗斯史学的内容。两个半世纪以来，全球的历史写作和学术研究是否经历了一个西方化的过程？基于自身的传统，其他文化背景下的历史学家如何对西方的影响进行采纳或予以抵制？本书试图解答上述两个问题。不仅研究了西方的史学流变，也考察了亚洲和中东悠久的史学传统，以及从拉丁美洲到撒哈拉以南非洲等非西方世界更地方化的史学发展。以考察18世纪末尚未受到西方影响的各种类型的历史思想和写作传统为起点，本书继而对通过贸易和帝国扩张而开始输出的西方历史观念产生的作用，以及19世纪和20世纪职业化历史学与“科学的”历史学在全球的兴起进行了剖析。", 107.50));
    }};

    public BookService(Context context) {
        super(context);
    }

    private static Book deserializer(Cursor cursor) {
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

    /**
     * @param cursor     cursor
     * @param idLocIndex 联合查询时Book的ID的位置
     * @return 反序列化后的Book
     */
    public static Book deserializer(Cursor cursor, int idLocIndex) {
        Book b = new Book();
        b.setId(cursor.getInt(idLocIndex));
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
        Cursor cursor = db.query("book", null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
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
