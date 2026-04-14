package com.example.testaplication.Manga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testaplication.API.ApiAttackOnTitan;
import com.example.testaplication.API.GetImageAPI;
import com.example.testaplication.Adapter.RecycleViewAdapter;
import com.example.testaplication.R;
import com.example.testaplication.Adapter.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttackOnTitanChap1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_clover_chapter3);
        recyclerView = findViewById(R.id.recyleView);
        get_image();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        textBack = findViewById(R.id.imgback);
        setChap = findViewById(R.id.setChap);
        setChap.setText("Chapter 1");
        imgNext = findViewById(R.id.imgnext);
    }
    public void get_image(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss)").create();
        GetImageAPI api_get = new Retrofit.Builder().baseUrl("https://myjsons.com/").addConverterFactory(GsonConverterFactory.create(gson)).build().create(GetImageAPI.class);
        Call<ApiAttackOnTitan> call = api_get.getImageAttackOnTitan();
        call.enqueue(new Callback<ApiAttackOnTitan>() {
            @Override
            public void onResponse(Call<ApiAttackOnTitan> call, Response<ApiAttackOnTitan> response) {
                if (response.isSuccessful()) {
                    ApiAttackOnTitan attackOnTitanData = response.body();
                    for(int i = 0;i<attackOnTitanData.getChapter1().size();i++){
                        chapter1.add(new Image(attackOnTitanData.getChapter1().get(i)));
                    }
                    RecycleViewAdapter adapter = new RecycleViewAdapter(chapter1);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ApiAttackOnTitan> call, Throwable t) {

            }
        });

    }
    private RecyclerView recyclerView;
    private List<Image> chapter1 = new ArrayList<>();
    private ImageView textBack;

    private Spinner spinner;
    private TextView setChap;
    private ImageView imgNext;
}