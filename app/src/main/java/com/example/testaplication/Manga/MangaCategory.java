package com.example.testaplication.Manga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import com.example.testaplication.Adapter.MangaAdapter;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.MangaSQLiteHelper;

import java.util.List;

public class MangaCategory extends AppCompatActivity {
    private TextView categoryName;
    private ListView listView;

    private MangaSQLiteHelper mangaSQLiteHelper;
    private MangaAdapter mangaAdapter;
    private List<Manga> mangaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_catergory_manga);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryName = findViewById(R.id.category_name);
        listView = findViewById(R.id.listview_category_manga);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String category = bundle.getString("category");
        categoryName.setText(category);

        mangaSQLiteHelper = new MangaSQLiteHelper(MangaCategory.this);
        mangaList = mangaSQLiteHelper.displayCategoryManga(category);
        mangaAdapter = new MangaAdapter(MangaCategory.this, R.layout.display_manga_category, mangaList);
        listView.setAdapter(mangaAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Manga manga = mangaList.get(i);
                if(manga.getName().equals("Black CLover")) {
                    Intent intent = new Intent(MangaCategory.this, BlackClover.class);
                    startActivity(intent);
                }
                else if(manga.getName().equals("Attack On Titan")) {
                    Intent intent = new Intent(MangaCategory.this, AttackOnTitan.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mangaList = mangaSQLiteHelper.search(s, categoryName.getText().toString().trim());
                mangaAdapter.setList(mangaList);
                mangaAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}