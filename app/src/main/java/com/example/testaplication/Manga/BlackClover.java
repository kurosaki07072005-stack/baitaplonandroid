package com.example.testaplication.Manga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testaplication.R;
import com.example.testaplication.Sqlite.SqlVoteManga;

import java.util.ArrayList;
import java.util.List;

public class BlackClover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_clover);
        listView = findViewById(R.id.listviewblack);
        sendComment = findViewById(R.id.sendComment);
        List<String> list = new ArrayList<>();
        list.add("Chapter 1");
        list.add("Chapter 2");
        list.add("Chapter 3");
        list.add("Chapter 4");
        list.add("Chapter 5");
        ArrayAdapter adapter = new ArrayAdapter(BlackClover.this, android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(BlackClover.this, BlackCloverChap1.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(BlackClover.this, Chap2BlackClover.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(BlackClover.this, BlackCloverChapter3.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent4 = new Intent(BlackClover.this, Chapter4BlackClover.class);
                        startActivity(intent4);
                        break;
                }
            }
        });
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlackClover.this, CommentAndReview.class);
                intent.putExtra("Name", "Black Clover");
                startActivity(intent);

            }
        });
        vote = findViewById(R.id.Vote);
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlackClover.this, TotalVoteManga.class);
                intent.putExtra("Name", "Black Clover");
                startActivity(intent);
            }
        });

    }
    private ListView listView;
    private Button sendComment;
    private Button vote;
    private TextView vote2;
    private SqlVoteManga voteManga;
}