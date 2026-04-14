package com.example.testaplication.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testaplication.R;
import com.example.testaplication.Sqlite.MangaSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class ManagementAddManga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manga);
        nameManga = findViewById(R.id.editTextTenTruyen);
        category = findViewById(R.id.editTextTheLoai);
        btnaddManga = findViewById(R.id.buttonThemTruyen);
        addImage = findViewById(R.id.buttonChonAnh);
        image = findViewById(R.id.imageView);
        db = new MangaSQLiteHelper(ManagementAddManga.this);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManagementAddManga.this);
                View custom = getLayoutInflater().inflate(R.layout.customaddmanga,null);
                builder.setView(custom);
                List<ImageList> list = new ArrayList<>();
                list.add(new ImageList(R.drawable.onepice));
                list.add(new ImageList(R.drawable.anime));
                list.add(new ImageList(R.drawable.op));
                list.add(new ImageList(R.drawable.romem));
                list.add(new ImageList(R.drawable.girl));
                ListView listView = custom.findViewById(R.id.listImage);
                AdapterStoreImage adapter = new AdapterStoreImage(ManagementAddManga.this,R.layout.storeimage,list);
                listView.setAdapter(adapter);
                builder.setCancelable(true);
                AlertDialog dialog =   builder.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        idImage = list.get(i).getId();
                        image.setImageResource(idImage);
                        dialog.dismiss();
                    }
                });

            }
        });

        btnaddManga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name = nameManga.getText().toString();
               String cate = category.getText().toString();
               if(db.check(name) == false){
                   Toast.makeText(ManagementAddManga.this, "Truyện Đã Tồn Tại Vui Lòng Thêm Truyện Khác", Toast.LENGTH_SHORT).show();
               }
               else if(idImage == 0 || name.isEmpty() || cate.isEmpty()){
                   Toast.makeText(ManagementAddManga.this, "Vui Lòng Không Để Nội Dung Trống", Toast.LENGTH_SHORT).show();
               }
               else{
                   db.insert_Value(idImage,name,cate);
                   Toast.makeText(ManagementAddManga.this, "Thêm Truyện Thành Công", Toast.LENGTH_SHORT).show();
                   Intent intent1 = new Intent(ManagementAddManga.this, MangaManagement.class);
                   startActivity(intent1);
               }
            }
        });


    }
    private EditText nameManga;
    private EditText category;
    private Button btnaddManga;
    private Button addImage;
    private ImageView image;
    private MangaSQLiteHelper db;
    private int idImage = 0;

}