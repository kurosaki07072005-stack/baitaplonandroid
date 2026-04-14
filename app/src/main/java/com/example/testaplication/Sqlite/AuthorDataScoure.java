package com.example.testaplication.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.testaplication.Manga.Author;
import com.example.testaplication.Manga.Category;

import java.util.ArrayList;
import java.util.List;

public class AuthorDataScoure {
    private SQLiteDatabase database;
    private AuthorSQLiteHelper dbHelper;
    private String[] allColumns = {
            AuthorSQLiteHelper.COLUMN_ID,
            AuthorSQLiteHelper.COLUMN_NAME,
            AuthorSQLiteHelper.COLUMN_GENDER,
            AuthorSQLiteHelper.COLUMN_COUNTRY
    };

    public AuthorDataScoure(Context context) {
        dbHelper = new AuthorSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();

        Cursor cursor = database.query(AuthorSQLiteHelper.TABLE_AUTHOR,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Author author = cursorToAuthor(cursor);
            authors.add(author);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return authors;
    }

    public int getID(String name, String gender, String country) {
        String selection = AuthorSQLiteHelper.COLUMN_NAME + " = ? AND " + AuthorSQLiteHelper.COLUMN_GENDER + " = ? AND " + AuthorSQLiteHelper.COLUMN_COUNTRY + " = ?";
        String[] selectionArgs = {name, gender, country};
        Cursor cursor = database.query(AuthorSQLiteHelper.TABLE_AUTHOR, allColumns, selection, selectionArgs, null, null, null);

        int id = 0; // Mặc định gán id bằng 0

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(AuthorSQLiteHelper.COLUMN_ID));
        }
        cursor.close();
        return id;
    }

    public boolean insert(String name, String gender, String country) {
        if(getID(name, gender, country) > 0)
            return false;

        ContentValues values = new ContentValues();
        values.put(AuthorSQLiteHelper.COLUMN_NAME, name);
        values.put(AuthorSQLiteHelper.COLUMN_GENDER, gender);
        values.put(AuthorSQLiteHelper.COLUMN_COUNTRY, country);

        long newRowId = database.insert(AuthorSQLiteHelper.TABLE_AUTHOR, null, values);
        return newRowId != -1;
    }

    public boolean update(int id, String name, String gender, String country) {
        ContentValues values = new ContentValues();
        values.put(AuthorSQLiteHelper.COLUMN_NAME, name);
        values.put(AuthorSQLiteHelper.COLUMN_GENDER, gender);
        values.put(AuthorSQLiteHelper.COLUMN_COUNTRY, country);

        String selection = AuthorSQLiteHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = database.update(
                AuthorSQLiteHelper.TABLE_AUTHOR,
                values,
                selection,
                selectionArgs);

        return count > 0;
    }

    public boolean delete(int id) {
        String selection = AuthorSQLiteHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = database.delete(
                AuthorSQLiteHelper.TABLE_AUTHOR,
                selection,
                selectionArgs);

        return count > 0;
    }

    private Author cursorToAuthor(Cursor cursor) {
        Author author = new Author();
        author.setId(cursor.getInt(0));
        author.setName(cursor.getString(1));
        author.setGender(cursor.getString(2));
        author.setCountry(cursor.getString(3));
        return author;
    }

}
