package com.example.testaplication.Manga;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testaplication.Adapter.DisplayVote;
import com.example.testaplication.Adapter.DisplayVoteAdapter;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.SqlVoteManga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.jar.Attributes;

public class TotalVoteManga extends AppCompatActivity {
    private Button btnVote;
    private EditText vote;
    private SqlVoteManga db;
    private ListView listview;
    private List<DisplayVote> listVote = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_vote_manga);
        btnVote = findViewById(R.id.btnVote);
        vote = findViewById(R.id.vote);
        db = new SqlVoteManga(TotalVoteManga.this);
        listview = findViewById(R.id.listview_vote);
        Intent intent = getIntent();
        String manga = intent.getStringExtra("Name");
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserName", Context.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("email", "");
        listVote = db.getdata(manga);
        DisplayVoteAdapter adapter = new DisplayVoteAdapter(TotalVoteManga.this,R.layout.customvoteview,listVote);
        listview.setAdapter(adapter);
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vote.getText().toString().isEmpty()) {
                    Toast.makeText(TotalVoteManga.this, "Vui Lòng Không Để Trống", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.valueOf(vote.getText().toString()) > 10){
                    Toast.makeText(TotalVoteManga.this, "Điểm Đánh Giá Không Quá 10", Toast.LENGTH_SHORT).show();
                }
                else if(db.check(user_name,manga)){
                    Toast.makeText(TotalVoteManga.this, "Mỗi Người Chỉ Có Thể Đánh Giá 1 Lần", Toast.LENGTH_SHORT).show();
                }
                else {
                    int sendVote = Integer.valueOf(vote.getText().toString());
                    db.insertVote(manga, sendVote,user_name);
                    listVote.add(new DisplayVote(user_name,vote.getText().toString()));
                    adapter.notifyDataSetChanged();
                    vote.setText(" ");
                    Toast.makeText(TotalVoteManga.this, "Đánh Giá Truyện Thành Công", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}