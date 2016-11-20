package com.example.guo.quizlock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Guo on 11/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "general.db";
    public static final String TABLE_NAME = "cardset";
    public static final String ID = "ID";
    public static final String TERM = "Term";
    public static final String DEFINITION = "Definition";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                " (ID Integer primary key autoincrement, TERM text, DEFINITION, text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String term, String def){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TERM, term);
        values.put(DEFINITION, def);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    public void clearAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table " + TABLE_NAME);
        db.execSQL("create table " + TABLE_NAME +
                " (ID Integer primary key autoincrement, TERM text, DEFINITION, text)");
    }

    /*public String[] getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where id = ?", new String[] {id});

        String term = cursor.getString(1);
        String def = cursor.getString(2);

        cursor.close();
        return new String[] {term, def};
    }*/
}
