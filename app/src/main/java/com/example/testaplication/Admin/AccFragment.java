package com.example.testaplication.Admin;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testaplication.R;
import com.example.testaplication.Sqlite.LoginSQLiteHelper;

public class AccFragment extends AppCompatActivity {

    private EditText search;
    //    private FloatingActionButton fab;
    private RecyclerView RvAcc;
    private AccountAdmin acc;
    private AdapterAcc adapterAcc;
    private AdapterAcc adapterAcc_clone;
    private ActionBar actionBar;
    private  SearchView searchView;
    private  LoginSQLiteHelper dbHelper;
    private TextView soluong;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

//        actionBar= getSupportActionBar();
        acc= new AccountAdmin(this);
//        fab= findViewById(R.id.fab);
        soluong= findViewById(R.id.soluong);
        RvAcc=findViewById(R.id.RvAcc);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,AddAccount.class);
//                startActivity(intent);
//            }
//        });

        loadData();

    }


    private void loadData() {
        adapterAcc = new AdapterAcc(this,acc.getAllData());
        RvAcc.setAdapter(adapterAcc);
        loadCount();
//        totalAccounts = adapterAcc.getItemCount();
//        updateActionBarTitle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main_top_menu,menu);
//        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
////        searchView= (SearchView) menu.findItem(R.id.searchAcction);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchAcc(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                searchAcc(newText);
//                return true;
//            }
//        });
//
//
//        return true;
//    }
//
//    private void searchAcc(String query) {
//        adapterAcc = new AdapterAcc(this,acc.getSearchData(query));
//        RvAcc.setAdapter(adapterAcc);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        acc.DeleteAllAcc();
//        onResume();
//
//
//        return true;
//    }
//    private void updateActionBarTitle() {
//        if (actionBar != null) {
//            actionBar.setTitle("Total Accounts: " + totalAccounts);
//        }
//    }
public void loadCount() {
    dbHelper = new LoginSQLiteHelper(this);
    String SelectQuery = "SELECT COUNT(*) FROM " + LoginSQLiteHelper.TABLE_ACCOUNT;
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery(SelectQuery, null);
    if (cursor.moveToFirst()) {
        int count = cursor.getInt(0);
        soluong.setText("Số lượng tài khoản: " + count);
    }
    cursor.close();
    db.close();
}

}