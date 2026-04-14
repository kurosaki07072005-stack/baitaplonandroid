package com.example.testaplication.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testaplication.R;
import com.example.testaplication.Sqlite.LoginSQLiteHelper;

public class AccDetails extends AppCompatActivity {
    private TextView usernameTv,TvPass;
    private String id;
    private AccountAdmin acc;
     private  LoginSQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc_details);
        dbHelper = new LoginSQLiteHelper(this); // Khởi tạo dbHelper với Context hiện tại
        acc = new AccountAdmin(this); // Truyền Context hiện tại cho đối tượng Account

        Intent intent = getIntent();
        id = intent.getStringExtra("accId");
        usernameTv = findViewById(R.id.usernameTv);
        TvPass = findViewById(R.id.TvPass);
        loadDataById();
    }
    public void loadDataById() {
        String SelectQuery=  "SELECT * FROM "+ LoginSQLiteHelper.TABLE_ACCOUNT + " WHERE " + LoginSQLiteHelper.COLUMN_ID + " =\"" + id + "\"" ;
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        Cursor cursor= db.rawQuery(SelectQuery,null);
        if(cursor.moveToNext()){
            do {
                String username=""+cursor.getString(cursor.getColumnIndexOrThrow(LoginSQLiteHelper.COLUMN_USERNAME));
                String password=""+cursor.getString(cursor.getColumnIndexOrThrow(LoginSQLiteHelper.COLUMN_PASSWORD));
                usernameTv.setText(username);
                TvPass.setText(password);
            }while (cursor.moveToNext());
        }
        db.close();
    }

}