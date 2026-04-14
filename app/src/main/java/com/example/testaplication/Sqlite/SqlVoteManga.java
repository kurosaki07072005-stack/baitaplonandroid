package com.example.testaplication.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.testaplication.Adapter.DisplayVote;

import java.util.ArrayList;
import java.util.List;

public class SqlVoteManga extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sqldatane.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "TotalVote";
    private static final String COLUMN_MANGA = "manga";
    private static final String COLUMN_VOTE = "vote";
    private static final String COLUMN_USER = "username";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_MANGA + " TEXT, "
            + COLUMN_USER + " TEXT, "
            + COLUMN_VOTE + " DOUBLE "
            + ")";

    public SqlVoteManga(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    public void insertVote(String managa,double vote,String username){
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues.put(COLUMN_MANGA,managa);
        contentValues.put(COLUMN_VOTE,vote);
        contentValues.put(COLUMN_USER,username);
        db.insert(TABLE_NAME,null,contentValues);
    }
    public double totalVote(String manga) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT AVG(" + COLUMN_VOTE + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_MANGA + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{manga});
        double total = 0.0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        cursor.close();
        db.close();
        return total;
    }
    public boolean check(String username,String manga){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM  " + TABLE_NAME + " WHERE " + COLUMN_USER + " = ?" +  " AND " + COLUMN_MANGA + " = ?" ;
        Cursor cursor = db.rawQuery(query,new String[]{username,manga});
        boolean checkExist = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return checkExist;
    }
    public List<DisplayVote> getdata(String manga){
        List<DisplayVote> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MANGA + " = ?";
        Cursor cursor = db.rawQuery(query,new String[]{manga});
        while (cursor.moveToNext()){
            String user = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER));
            Double points = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VOTE));
            list.add(new DisplayVote(user,String.valueOf(points)));
        }
        cursor.close();
        db.close();
        return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
