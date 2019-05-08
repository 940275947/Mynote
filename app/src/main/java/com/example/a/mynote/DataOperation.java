package com.example.a.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataOperation extends SQLiteOpenHelper{
    public DataOperation(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库sql语句 并 执行
        String sql = "create table note(name varchar(20),content text,CreatedTime varchar(20))";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion==1 && newVersion==2) {//升级判断,如果再升级就要再加两个判断,从1到3,从2到3
            db.execSQL("ALTER TABLE restaurants ADD phone TEXT;");
        }
    }
    public Cursor getAll() {//返回表中的数据,where是调用时候传进来的搜索内容,orderby是设置中传进来的列表排序类型
        StringBuilder buf=new StringBuilder("SELECT  name, content, CreatedTime FROM note");



        return(getReadableDatabase().rawQuery(buf.toString(), null));
    }
    public Cursor getById(String id) {//根据点击事件获取id,查询数据库
        String[] args={id};

        return(getReadableDatabase()
                .rawQuery("SELECT  name, content, CreatedTime FROM note WHERE name=?",
                        args));
    }
    public Cursor getById2(String id) {//根据点击事件获取id,查询数据库
        String[] args={"%"+id+"%"};
        System.out.println();
        return(getReadableDatabase()

                .rawQuery("SELECT  name, CreatedTime FROM note WHERE name like ?",
                        args));
    }
    public void insert(String name, String content, String CreatedTime) {
        ContentValues cv=new ContentValues();

        cv.put("name", name);
        cv.put("content", content);
        cv.put("CreatedTime", CreatedTime);


        getWritableDatabase().insert("note", "name", cv);
    }
    public void delete(String name2) {


        String[] args={name2};
        System.out.println("nametitledelete:"+name2);
        getWritableDatabase().delete("note", "name=?",args);
    }
    public void update(String name,String newname, String content, String CreatedTime) {
        ContentValues cv=new ContentValues();
        String[] args={name};
        cv.put("name", newname);
        cv.put("content", content);
        cv.put("CreatedTime", CreatedTime);


        getWritableDatabase().update("note", cv, "name=?",
                args);
    }
    public String getName(Cursor c) {
        return(c.getString(1));
    }

    public String getAddress(Cursor c) {
        return(c.getString(2));
    }

    public String getType(Cursor c) {
        return(c.getString(3));
    }
}
