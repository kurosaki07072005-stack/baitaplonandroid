package com.example.testaplication.Manga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.testaplication.R;

import java.util.ArrayList;
import java.util.List;

public class AttackOnTitan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_on_titan);
        listView = findViewById(R.id.listviewblack);
        sendComment = findViewById(R.id.sendComment);
        List<String> list = new ArrayList<>();
        list.add("Chapter 1");
        list.add("Chapter 2");
        list.add("Chapter 3");
        list.add("Chapter 4");
        ArrayAdapter adapter = new ArrayAdapter(AttackOnTitan.this, android.R.layout.simple_expandable_list_item_1,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(AttackOnTitan.this, AttackOnTitanChap1.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    sendComment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent  = new Intent(AttackOnTitan.this,CommentAndReview.class);
            intent.putExtra("Name","Attack On TiTan");
            startActivity(intent);

        }
    });
        vote = findViewById(R.id.Vote);
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AttackOnTitan.this, TotalVoteManga.class);
                intent.putExtra("Name", "Attack On Titan");
                startActivity(intent);
            }
        });
    }
    private ListView listView;
    private Button sendComment;
    private Button vote;
    }