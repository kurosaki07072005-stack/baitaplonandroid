package com.example.testaplication.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.testaplication.Account.Account;
import com.example.testaplication.Manga.Category;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CategoryDataScoure {
    private SQLiteDatabase database;
    private CategorySQLiteHelper dbHelper;
    private String[] allColumns = {
            CategorySQLiteHelper.COLUMN_ID,
            CategorySQLiteHelper.COLUMN_NAME,
            CategorySQLiteHelper.COLUMN_DESCRIPTION
    };

    public CategoryDataScoure(Context context) {
        dbHelper = new CategorySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Category> search(String name) {
        List<Category> categories = new ArrayList<>();

        String nameLower = name.toLowerCase(); // Chuyển đổi chuỗi thành chữ thường
        Cursor cursor = database.query(CategorySQLiteHelper.TABLE_CATEGORY,
                allColumns, "lower(" + CategorySQLiteHelper.COLUMN_NAME + ") like ?", new String[] {"%" + nameLower + "%"}, null, null, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = cursorToCategory(cursor);
            categories.add(category);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return categories;
    }
    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();

        Cursor cursor = database.query(CategorySQLiteHelper.TABLE_CATEGORY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = cursorToCategory(cursor);
            categories.add(category);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return categories;
    }

    public int getID(String name, String description) {
        String selection = CategorySQLiteHelper.COLUMN_NAME + " = ? AND " + CategorySQLiteHelper.COLUMN_DESCRIPTION + " = ?";
        String[] selectionArgs = {name, description};
        Cursor cursor = database.query(CategorySQLiteHelper.TABLE_CATEGORY, allColumns, selection, selectionArgs, null, null, null);

        int id = 0; // Mặc định gán id bằng 0

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(CategorySQLiteHelper.COLUMN_ID));
        }

        cursor.close();
        return id;
    }

    public boolean insert(String name, String description) {
        if(getID(name, description) > 0)
            return false;

        ContentValues values = new ContentValues();
        values.put(CategorySQLiteHelper.COLUMN_NAME, name);
        values.put(CategorySQLiteHelper.COLUMN_DESCRIPTION, description);

        long newRowId = database.insert(CategorySQLiteHelper.TABLE_CATEGORY, null, values);
        return newRowId != -1;
    }

    public boolean update(int id, String name, String description) {
        ContentValues values = new ContentValues();
        values.put(CategorySQLiteHelper.COLUMN_NAME, name);
        values.put(CategorySQLiteHelper.COLUMN_DESCRIPTION, description);

        String selection = CategorySQLiteHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = database.update(
                CategorySQLiteHelper.TABLE_CATEGORY,
                values,
                selection,
                selectionArgs);

        return count > 0;
    }

    public boolean delete(int id) {
        String selection = CategorySQLiteHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        int count = database.delete(
                CategorySQLiteHelper.TABLE_CATEGORY,
                selection,
                selectionArgs);

        return count > 0;
    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setId(cursor.getInt(0));
        category.setName(cursor.getString(1));
        category.setDescription(cursor.getString(2));
        return category;
    }
}