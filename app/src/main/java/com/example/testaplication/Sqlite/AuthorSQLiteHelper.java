package com.example.testaplication.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AuthorSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_AUTHOR = "author";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_COUNTRY = "country";
    public static final String DATABASE_NAME = "app_manga_king2.db";
    private static final int DATABASE_VERSION = 2;

    // Câu lệnh khởi tạo Database.
    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_AUTHOR + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " nvarchar(100) not null, "
            + COLUMN_GENDER + " nvarchar(100), "
            + COLUMN_COUNTRY + " nvarchar(100))";

    public AuthorSQLiteHelper(Context context) {
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        onCreate(sqLiteDatabase);
    }
}
