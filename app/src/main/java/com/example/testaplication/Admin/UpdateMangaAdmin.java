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

public class UpdateMangaAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_manga_admin);
        nameManga = findViewById(R.id.editTextTenTruyen);
        category = findViewById(R.id.editTextTheLoai);
        btnupdateManga = findViewById(R.id.buttonThemTruyen);
        addImage = findViewById(R.id.buttonChonAnh);
        image = findViewById(R.id.imageView);
        nameManga.setEnabled(false);
        db = new MangaSQLiteHelper(UpdateMangaAdmin.this);
        Intent intent = getIntent();
        nameManga.setText(intent.getStringExtra("name"));
        category.setText(intent.getStringExtra("category"));
        int getImage = intent.getIntExtra("image",0);
        image.setImageResource(intent.getIntExtra("image",0));
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateMangaAdmin.this);
                View custom = getLayoutInflater().inflate(R.layout.customaddmanga,null);
                builder.setView(custom);
                List<ImageList> list = new ArrayList<>();
                list.add(new ImageList(R.drawable.onepice));
                list.add(new ImageList(R.drawable.anime));
                list.add(new ImageList(R.drawable.op));
                list.add(new ImageList(R.drawable.romem));
                list.add(new ImageList(R.drawable.girl));
                list.add(new ImageList(R.drawable.kmys));
                list.add(new ImageList(R.drawable.aot2));
                list.add(new ImageList(R.drawable.blcv));
                ListView listView = custom.findViewById(R.id.listImage);
                AdapterStoreImage adapter = new AdapterStoreImage(UpdateMangaAdmin.this,R.layout.storeimage,list);
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
        btnupdateManga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameManga.getText().toString();
                String cate = category.getText().toString();
                if(name.isEmpty() || cate.isEmpty() || String.valueOf(idImage).isEmpty()){
                    Toast.makeText(UpdateMangaAdmin.this, "Thông Tin Không Được Bỏ Trống", Toast.LENGTH_SHORT).show();
                }
                else if(idImage == 0){
                    if(db.UpdateManga(name,cate,getImage)){
                        Toast.makeText(UpdateMangaAdmin.this, "Sửa Thông Tin Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(UpdateMangaAdmin.this, MangaManagement.class);
                        startActivity(intent1);
                    }
                }
                else if(db.UpdateManga(name,cate,idImage)){
                    Toast.makeText(UpdateMangaAdmin.this, "Sửa Thông Tin Thành Công", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(UpdateMangaAdmin.this,MangaManagement.class);
                    startActivity(intent1);
                }
                else{
                    Toast.makeText(UpdateMangaAdmin.this, "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private EditText nameManga;
    private EditText category;
    private Button btnupdateManga;
    private Button addImage;
    private ImageView image;
    private MangaSQLiteHelper db;
    private int idImage = 0;
}