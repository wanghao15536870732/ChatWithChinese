package com.example.lab.android.nuc.chat.Translation.Glossary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ljb on 2018/4/19.
 */

public class MyHistory extends SQLiteOpenHelper {


    public MyHistory(Context context) {
        super(context, "history.db", null,4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table info(_id integer primary key autoincrement,word varchar(200),result varchar(200))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("alter table info add result varchar(200)");
    }
}
