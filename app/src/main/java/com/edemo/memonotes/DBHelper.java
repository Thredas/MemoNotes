package com.edemo.memonotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "NotesDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table notes ("
                + "id integer primary key autoincrement,"
                + "note text,"
                + "name text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
