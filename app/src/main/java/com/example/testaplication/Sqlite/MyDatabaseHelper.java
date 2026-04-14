package com.example.testaplication.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.testaplication.Adapter.ListFavoriteConstructor;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public  static  final  String DATABASE_NAME = "new_manga.db";
    private static final String TABLE_NAME = "favorite";
    private static final String COLUMN_IMAGE = "Image";
    private static final String COLUMN_NAME = "name";
    private static final String TABLE_NAME2 = "history";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_USERNAME = "username";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_IMAGE + " TEXT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_USERNAME + " TEXT)";
        String createTableSQL2 = "CREATE TABLE " + TABLE_NAME2 + " (" +
                COLUMN_IMAGE + " TEXT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_USERNAME + " TEXT)";
        sqLiteDatabase.execSQL(createTableSQL);
        sqLiteDatabase.execSQL(createTableSQL2);
    }


    public void insertData(String img, String name, String des, String username) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, img);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, des);
        values.put(COLUMN_USERNAME, username);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void insertData2(String img, String name, String des, String username) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE, img);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, des);
        values.put(COLUMN_USERNAME, username);
        db.insert(TABLE_NAME2, null, values);
        db.close();
    }

    public List<ListFavoriteConstructor> getResource(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?";
        List<ListFavoriteConstructor> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[]{username});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            String des = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            ListFavoriteConstructor lt = new ListFavoriteConstructor(image, name, des);
            list.add(lt);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<ListFavoriteConstructor> getResource2(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_USERNAME + " = ?";
        List<ListFavoriteConstructor> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, new String[]{username});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            String des = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            ListFavoriteConstructor lt = new ListFavoriteConstructor(image, name, des);
            list.add(lt);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean exists(int key, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_IMAGE + " = ? AND " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(key), username});

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return exists;
    }

    public boolean exists2(String key, String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + COLUMN_NAME + " = ? AND " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{key, username});

        boolean exists = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return exists;
    }
    public void delete(String image, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME2 + " WHERE " + COLUMN_IMAGE + " = '" + image + "' AND " + COLUMN_USERNAME + " = '" + username + "'";
        db.execSQL(query);
        db.close();
    }
    public void deleteWishList(String username, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_IMAGE + " = '" + image + "' AND " + COLUMN_USERNAME + " = '" + username + "'";
        db.execSQL(query);
        db.close();
    }




}
