package com.example.testaplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.testaplication.Account.Login;
import com.example.testaplication.Adapter.DefaultFragment;
import com.example.testaplication.Adapter.ViewPagerAdapter;
import com.example.testaplication.Display.AdminFragments;
import com.example.testaplication.Display.CategoryFragment;
import com.example.testaplication.Display.ChangePasswordFragment;
import com.example.testaplication.Display.FavouriteFragment;
import com.example.testaplication.Display.FragmentList;
import com.example.testaplication.Display.HistoryFragment;
import com.example.testaplication.Display.HomeFragment;
import com.example.testaplication.Display.MangaFragment;
import com.example.testaplication.Display.VoteFragments;
import com.example.testaplication.Manga.Category;
import com.example.testaplication.Sqlite.CategoryDataScoure;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int HOME_FRAGMENT = 1;
    private static final int CATEGORY_FRAGMENT = 2;
    private static final int FAVOURITE_FRAGMENT = 3;
    private static final int HISTORY_FRAGMENT = 4;
    private static final int VOTE_FRAGMENTS = 5;
    private static final int CHANGE_PASSWORD_FRAGMENT = 6;
    private static final int FRAGMENT_LIST = 7;
    private static final int MANGA_FRAGMENT_LIST = 8;
    private static final int LOG_OUT = 9;
    private static final int ADMIN_FRAGMENT = 10;
    private int currentFragment = HOME_FRAGMENT;
    private ViewPagerAdapter viewPagerAdapter;
    private DrawerLayout drawerLayout;
    private  long backpress;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private String userName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.mviewPager);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.setFragment(new DefaultFragment());
        viewPager.setAdapter(viewPagerAdapter);
//        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                switch (position){
//                    case 0:
//                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
//                        break;
//                    case 1:
//                        bottomNavigationView.getMenu().findItem(R.id.favorite).setChecked(true);
//                        break;
//                    case 2:
//                        bottomNavigationView.getMenu().findItem(R.id.person).setChecked(true);
//                        break;
//                }
//            }
//        });
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//               if(item.getItemId() == R.id.action_home){
//                   viewPager.setCurrentItem(0);
//               }
//               if(item.getItemId() == R.id.favorite){
//                   viewPager.setCurrentItem(1);
//               }
//                if(item.getItemId() == R.id.person){
//                    viewPager.setCurrentItem(2);
//                }
//               return  true;
//            }
//        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        SharedPreferences sharedPreferences = getSharedPreferences("MyUserName", MODE_PRIVATE);
         userName = sharedPreferences.getString("username","User");
        View headerView = navigationView.getHeaderView(0);
        TextView displayUser = headerView.findViewById(R.id.UserName);
        displayUser.setText(userName);
    }

//    @Override
//    public  void onBackPressed() {
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//        else {
//            super.onBackPressed();
//        }
//
//        if(backpress + 2000 > System.currentTimeMillis()){
//            super.onBackPressed();
//            return;
//        }
//        else{
//            Toast.makeText(MainActivity.this, "CLick Again To Exit App", Toast.LENGTH_SHORT).show();
//        }
//        backpress = System.currentTimeMillis();
//    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home) {
            if(currentFragment != HOME_FRAGMENT) {
                Fragment homeFragment = new HomeFragment();
                viewPagerAdapter.setFragment(homeFragment);
                replaceFragment(homeFragment);
                currentFragment = HOME_FRAGMENT;
            }
        }
        else if(id == R.id.nav_category) {
            if(currentFragment != CATEGORY_FRAGMENT) {
                Fragment categoryFragment = new CategoryFragment();
                viewPagerAdapter.setFragment(categoryFragment);
                replaceFragment(categoryFragment);
                currentFragment = CATEGORY_FRAGMENT;
            }
        }
        else if(id == R.id.nav_favourite) {
            if(currentFragment != FAVOURITE_FRAGMENT) {
                Fragment favouriteFragment = new FavouriteFragment();
                viewPagerAdapter.setFragment(favouriteFragment);
                replaceFragment(favouriteFragment);
                currentFragment = FAVOURITE_FRAGMENT;
            }
        }
        else if(id == R.id.nav_history) {
            if(currentFragment != HISTORY_FRAGMENT ) {
                Fragment historyFragment = new HistoryFragment();
                viewPagerAdapter.setFragment(historyFragment);
                replaceFragment(historyFragment);
                currentFragment = HISTORY_FRAGMENT ;
            }
        }
        else if(id == R.id.nav_change_password) {
            if(currentFragment !=  CHANGE_PASSWORD_FRAGMENT ) {
                Fragment changePasswordFragment = new ChangePasswordFragment();
                viewPagerAdapter.setFragment(changePasswordFragment);
                replaceFragment(changePasswordFragment);
                currentFragment = CHANGE_PASSWORD_FRAGMENT;
            }
        }
        else if(id == R.id.nav_logout) {
            if(currentFragment !=  LOG_OUT ) {
                SharedPreferences preferences = getSharedPreferences("remember-account", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.clear();
                edit.commit();
                currentFragment = LOG_OUT;
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        }
        else if(id == R.id.nav_listManga){
            if(currentFragment != FRAGMENT_LIST){
                Fragment listManga = new FragmentList();
                viewPagerAdapter.setFragment(listManga);
                replaceFragment(listManga);
                currentFragment = FRAGMENT_LIST;
            }
        }
        else if(id == R.id.nav_search) {
            if(currentFragment != MANGA_FRAGMENT_LIST) {
                Fragment mangaFragment = new MangaFragment();
                viewPagerAdapter.setFragment(mangaFragment);
                replaceFragment(mangaFragment);
                currentFragment = MANGA_FRAGMENT_LIST;
            }
        }
        else if(id == R.id.nav_listVote){
            if(currentFragment != VOTE_FRAGMENTS) {
                Fragment VoteFragment = new VoteFragments();
                viewPagerAdapter.setFragment(VoteFragment);
                replaceFragment(VoteFragment);
                currentFragment = VOTE_FRAGMENTS;
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