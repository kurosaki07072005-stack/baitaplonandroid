package com.example.testaplication.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.testaplication.Adapter.AdapterCustom;
import com.example.testaplication.Adapter.MangaInformation;
import com.example.testaplication.Display.HomeFragment;
import com.example.testaplication.MainActivity;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.MangaSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class MangaManagement extends AppCompatActivity {
    private MangaSQLiteHelper db = new MangaSQLiteHelper(MangaManagement.this);
    private List<MangaInformation> list;
    private GridView gridView;
    private int index;
    private AdapterCustom adapter;
    private Button addManga;
    private Button btnRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_management);
        gridView = findViewById(R.id.gridView);
        addManga = findViewById(R.id.btnAdd);
        list = new ArrayList<>();
        list = db.get_data();
        btnRes = findViewById(R.id.btnRes);
         adapter = new AdapterCustom(MangaManagement.this,R.layout.custom_list_view,list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                showPopupMenu(view);
            }
        });
        addManga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangaManagement.this, ManagementAddManga.class);
                startActivity(intent);
            }
        });
        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MangaManagement.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showPopupMenu(View view){
        PopupMenu popupMenu = new PopupMenu(MangaManagement.this,view);
        popupMenu.getMenuInflater().inflate(R.menu.crudmanga,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.deletemanga){
                    String manga = list.get(index).getNameAuthors();
                    db.DeleteManga(manga);
                    Toast.makeText(MangaManagement.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    list.remove(index);
                    adapter.notifyDataSetChanged();
                }
                if(menuItem.getItemId() == R.id.updatemanga){
                    Intent intent = new Intent(MangaManagement.this, UpdateMangaAdmin.class);
                    intent.putExtra("name",list.get(index).getNameAuthors());
                    intent.putExtra("category",list.get(index).getDescription());
                    intent.putExtra("image",list.get(index).getSource());
                    startActivity(intent);
                }
                return true;
            }

        }
        );
    popupMenu.show();

    }
}