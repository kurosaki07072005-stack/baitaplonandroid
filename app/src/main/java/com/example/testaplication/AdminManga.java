package com.example.testaplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testaplication.Account.Login;
import com.example.testaplication.Adapter.DefaultFragment;
import com.example.testaplication.Adapter.ViewPagerAdapter;
import com.example.testaplication.Display.AdminFragments;
import com.example.testaplication.Display.AuthorFragment;
import com.example.testaplication.Display.CategoryFragment;
import com.example.testaplication.Display.ChangePasswordFragment;
import com.example.testaplication.Display.FavouriteFragment;
import com.example.testaplication.Display.FragmentList;
import com.example.testaplication.Display.HistoryFragment;
import com.example.testaplication.Display.HomeFragment;
import com.example.testaplication.Display.ManagementCategoryFragment;
import com.example.testaplication.Display.MangaFragment;
import com.example.testaplication.Sqlite.MangaSQLiteHelper;
import com.example.testaplication.Sqlite.MyDatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class AdminManga extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static int CATEGORY_MANAGEMENT = 1;
    private static int MANGA_MANAGEMENT = 2;
    private static int AUTHOR_MANAGEMENT = 3;
    private static int LOG_OUT = 4;
    private int currentFragment = CATEGORY_MANAGEMENT;
    private ViewPagerAdapter viewPagerAdapter;
    private DrawerLayout drawerLayout;
    private  long backpress;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        viewPager = findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.setFragment(new DefaultFragment());
        viewPager.setAdapter(viewPagerAdapter);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view_admin);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(new ManagementCategoryFragment());
        navigationView.getMenu().findItem(R.id.nav_category_management).setChecked(true);
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserName", MODE_PRIVATE);
        String userName = sharedPreferences.getString("username","User");
        View headerView = navigationView.getHeaderView(0);
        TextView displayUser = headerView.findViewById(R.id.UserName);
        displayUser.setText(userName);

    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame_admin, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_category_management){
            if(currentFragment != CATEGORY_MANAGEMENT) {
                Fragment categoryManagement = new ManagementCategoryFragment();
                viewPagerAdapter.setFragment(categoryManagement);
                replaceFragment(categoryManagement);
                currentFragment = CATEGORY_MANAGEMENT;
            }
        }
        else if(id == R.id.nav_manga_management) {
            if(currentFragment != MANGA_MANAGEMENT) {
                Fragment mangaManagement = new AdminFragments();
                viewPagerAdapter.setFragment(mangaManagement);
                replaceFragment(mangaManagement);
                currentFragment = MANGA_MANAGEMENT;
            }
        }
        else if(id == R.id.nav_author_management) {
            if(currentFragment != AUTHOR_MANAGEMENT) {
                Fragment authorManagement = new AuthorFragment();
                viewPagerAdapter.setFragment(authorManagement);
                replaceFragment(authorManagement);
                currentFragment = AUTHOR_MANAGEMENT;
            }
        }
        else if(id == R.id.nav_logout) {
            if(currentFragment != LOG_OUT) {
                SharedPreferences preferences = getSharedPreferences("remember-account", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.clear();
                edit.commit();
                currentFragment = LOG_OUT;
                Intent intent = new Intent(AdminManga.this, Login.class);
                startActivity(intent);
            }
        }
        viewPagerAdapter.notifyDataSetChanged();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}