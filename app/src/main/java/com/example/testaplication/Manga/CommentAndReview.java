package com.example.testaplication.Manga;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.testaplication.Adapter.AdapterContent;
import com.example.testaplication.Adapter.Review;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.SqlCommentManga;

import java.util.ArrayList;
import java.util.List;

public class CommentAndReview extends AppCompatActivity {
    private ListView listView;
    private Button buttonSend;
    private EditText text;
    private AdapterContent adapter;
    private int index = -1;
    private SqlCommentManga db = new SqlCommentManga(CommentAndReview.this);
    private   List<Review> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_and_review);
        listView = findViewById(R.id.listViewComment);
        buttonSend = findViewById(R.id.textSend);

        String manga = getIntent().getStringExtra("Name");
        text = findViewById(R.id.textComment);
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserName", Context.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("email", "");
         list = db.getData(manga);
         adapter = new AdapterContent(CommentAndReview.this,list, R.layout.custom_comment);
        listView.setAdapter(adapter);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String getText = text.getText().toString();
                    if(getText.isEmpty()){
                        Toast.makeText(CommentAndReview.this, "Nội Dung Không Được Để Trống", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        db.InsertComment(user_name,manga,getText);
                        list.add(new Review(0,user_name,manga,getText));
                        adapter.notifyDataSetChanged();
                        text.setText("");
                    }


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                index = i;
                if(list.get(index).getUsername().equals(user_name)){
                    showPopupMenu(view);

                }
                else{
                    Toast.makeText(CommentAndReview.this, "Bạn Không Thể Sửa Bình Luận Của Người Khác", Toast.LENGTH_SHORT).show();
                    buttonSend.setEnabled(true);
                    text.setText(" ");
                }

            }

        });
    }
    private void showPopupMenu(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserName", Context.MODE_PRIVATE);
        String manga = getIntent().getStringExtra("Name");
        String user_name = sharedPreferences.getString("email", "");
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menu_delete) {
                    list.remove(index);
                    db.deleteRow(index,manga);
                    adapter.notifyDataSetChanged();
                    text.setText("");
                }
                if(item.getItemId() == R.id.menu_edit){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CommentAndReview.this);
                    builder.setTitle("Sửa Bình Luận");
                    View customView = getLayoutInflater().inflate(R.layout.customalertdialogupdatecomment,null);
                    // chuyển từ file xml -> view get id
                    builder.setView(customView);
                    EditText comment = customView.findViewById(R.id.updateText);
                    Button editbtn = customView.findViewById(R.id.btnUpdate);
                    comment.setText(list.get(index).getCommnent());
                    builder.setCancelable(true);
                    editbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String getComment = comment.getText().toString();
                            if(comment.getText().toString().isEmpty()){
                                Toast.makeText(CommentAndReview.this, "Nội Dung Không Được Để Trống", Toast.LENGTH_SHORT).show();
                            }
                            else if(db.updateComment(getComment,user_name,manga,index)){
                                list.get(index).setCommnent(getComment);
                                Toast.makeText(CommentAndReview.this, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            text.setText("");
                        }
                    });
                    builder.show();
                }
                return true;
            }
        });
        popupMenu.show();
    }
}