package com.example.testaplication.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.testaplication.Adapter.Review;
import java.util.ArrayList;
import java.util.List;

public class SqlCommentManga extends SQLiteOpenHelper {
    private static final String DATABASE = "lastchange234.db";
    private static final String DATANAME = "table_review";
    private static final String USERNAME = "username";
    private static final String COMMENT = "comment";
    private static final String NAME_MANGA = "name";

    public SqlCommentManga(@Nullable Context context) {
        super(context, DATABASE, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + DATANAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0, " +
                USERNAME + " TEXT, " +
                NAME_MANGA + " TEXT, " +
                COMMENT + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }



    public void InsertComment(String username, String name_manga, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, username);
        contentValues.put(NAME_MANGA, name_manga);
        contentValues.put(COMMENT, comment);
        db.insert(DATANAME, null, contentValues);
        db.close();
    }


    public List<Review> getData(String name_manga) {
        List<Review> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", USERNAME, NAME_MANGA, COMMENT};

        String selection = NAME_MANGA + " = ?";
        String[] selectionArgs = {name_manga};

        Cursor cursor = db.query(DATANAME, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
            String manga = cursor.getString(cursor.getColumnIndexOrThrow(NAME_MANGA));
            String comment = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT));
            list.add(new Review(id, username, manga, comment));
        }

        cursor.close();
        db.close();
        return list;
    }

    public boolean updateComment(String comment, String username, String nameManga, int pos) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues.put(COMMENT, comment);
        String query = "SELECT id FROM " + DATANAME + " WHERE " +NAME_MANGA+ "= ? " + " LIMIT 1 OFFSET " + pos;
        Cursor cursor = db.rawQuery(query,new String[]{nameManga});
        int rowsAffected = 0;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(id)};
            rowsAffected = db.update(DATANAME, contentValues, whereClause, whereArgs);
        }
        cursor.close();
        db.close();
        return rowsAffected > 0;
    }

    public void deleteRow(int position,String nameManga) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String selectQuery = "SELECT id FROM " + DATANAME + " WHERE " +NAME_MANGA+ "= ? " + " LIMIT 1 OFFSET " + position;
            Cursor cursor = db.rawQuery(selectQuery,new String[]{nameManga});
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String deleteQuery = "DELETE FROM " + DATANAME + " WHERE id = " + id;
                db.execSQL(deleteQuery);
            }
            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
