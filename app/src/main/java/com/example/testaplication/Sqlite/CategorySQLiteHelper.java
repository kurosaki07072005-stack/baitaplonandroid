package com.example.testaplication.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CategorySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_CATEGORY = "Category";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String DATABASE_NAME = "app_manga_king.db";
    private static final int DATABASE_VERSION = 2;

    // Câu lệnh khởi tạo Database.
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_CATEGORY + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " nvarchar(100) not null, "
            + COLUMN_DESCRIPTION + " nvarchar(100))";

    public CategorySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(CategorySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(sqLiteDatabase);
    }
}
