package com.example.testaplication.Admin;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.example.testaplication.Sqlite.LoginSQLiteHelper;

import java.util.ArrayList;

public class AccountAdmin {
        private SQLiteDatabase database;
        private LoginSQLiteHelper dbHelper;
        private TextView usernameTv,TvPass;
//    public long insertAccount(String username,String password){
//        SQLiteDatabase db= this.getWritableDatabase();
//        ContentValues contentValues= new ContentValues();
//        contentValues.put(COLUMN_USERNAME,username);
//        contentValues.put(COLUMN_PASSWORD,password);
//        long id=db.insert(TABLE_NAME,null,contentValues);
//        db.close();
//        return id;
//    }
public AccountAdmin(Context context) {
    dbHelper = new LoginSQLiteHelper(context);
}
    public  void deleteAcc(String id){
        String deleteQuery="DELETE FROM "+LoginSQLiteHelper.TABLE_ACCOUNT+" WHERE "+LoginSQLiteHelper.COLUMN_ID+" = "+id;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(deleteQuery);
        db.close();
    }

    public void DeleteAllAcc(){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+LoginSQLiteHelper.TABLE_ACCOUNT);
    }
    public ArrayList<MyAccount> getAllData(){
        ArrayList<MyAccount> arrayList = new ArrayList<>();
        String selectQuery= "select * from "+LoginSQLiteHelper.TABLE_ACCOUNT;
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
                MyAccount myAccount= new MyAccount(""+cursor.getString(cursor.getColumnIndexOrThrow(LoginSQLiteHelper.COLUMN_USERNAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(LoginSQLiteHelper.COLUMN_PASSWORD)),""+cursor.getInt(cursor.getColumnIndexOrThrow(LoginSQLiteHelper.COLUMN_ID))
                );
                arrayList.add(myAccount);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }
    public ArrayList<MyAccount> getSearchData(String query){
        ArrayList<MyAccount> Acclist= new ArrayList<>();
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        String queryToSearch=" SELECT * FROM "+LoginSQLiteHelper.TABLE_ACCOUNT+" WHERE "+LoginSQLiteHelper.COLUMN_USERNAME+" LIKE '%" +query+"%'";
        Cursor cursor= db.rawQuery(queryToSearch,null);

        if(cursor.moveToFirst()){
            do {
                MyAccount myAccount= new MyAccount(""+cursor.getString(cursor.getColumnIndexOrThrow(LoginSQLiteHelper.COLUMN_USERNAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(LoginSQLiteHelper.COLUMN_PASSWORD)),""+cursor.getInt(cursor.getColumnIndexOrThrow(LoginSQLiteHelper.COLUMN_ID))
                );
                Acclist.add(myAccount);
            }while (cursor.moveToNext());
        }
        return Acclist;
    }
}
