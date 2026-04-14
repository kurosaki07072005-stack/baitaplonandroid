package com.example.testaplication.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.testaplication.Adapter.MangaInformation;
import com.example.testaplication.Adapter.ListFavoriteConstructor;
import com.example.testaplication.Adapter.PhotoCustomListView;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.MangaSQLiteHelper;
import com.example.testaplication.Sqlite.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {
    private List<MangaInformation> list = new ArrayList<>();
    private ListView listViewItem;
    private  PhotoCustomListView adapter;
    private Button btnadd;
    private SearchView searchView;
    private List<ListFavoriteConstructor> list_favorite = new ArrayList<>();
    private MyDatabaseHelper sqliteOpenHelper;
    public int index = -1;
    private MangaSQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_list,container,false);
        listViewItem = view.findViewById(R.id.ListViewItem);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        sqliteOpenHelper = new MyDatabaseHelper(getActivity());
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyUserName", Context.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("email", "");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            setHasOptionsMenu(true);
        }

        db = new MangaSQLiteHelper(getActivity());
        list = db.get_data();
        adapter = new PhotoCustomListView(getActivity(),R.layout.custom_listview,list);
        listViewItem.setAdapter(adapter);
        listViewItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thêm Vào Danh Sách Yêu Thích");
                if(i==0){
                    index = i;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = list.get(index).getNameAuthors();
                            String des = list.get(index).getDescription();
                            int image = list.get(index).getSource();
                            if (!sqliteOpenHelper.exists(R.drawable.naruto,user_name)) {
                                sqliteOpenHelper.insertData(String.valueOf(image), name, des,user_name);
                            } else {
                                Toast.makeText(getActivity(), "Truyện này đã tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                if(i==1){
                    index = i;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = list.get(index).getNameAuthors();
                            String des = list.get(index).getDescription();
                            int image = list.get(index).getSource();
                            if (!sqliteOpenHelper.exists(R.drawable.blcv,user_name)) {
                                sqliteOpenHelper.insertData(String.valueOf(image), name, des,user_name);
                            } else {
                                Toast.makeText(getActivity(), "Truyện này đã tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                if(i==2){
                    index = i;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String name = list.get(index).getNameAuthors();
                            String des = list.get(index).getDescription();
                            int image = list.get(index).getSource();
                            if (!sqliteOpenHelper.exists(R.drawable.onepice,user_name)) {
                                sqliteOpenHelper.insertData(String.valueOf(image), name, des,user_name);
                            } else {
                                Toast.makeText(getActivity(), "Truyện này đã tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                if(i==3){
                    index = i;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = list.get(index).getNameAuthors();
                            String des = list.get(index).getDescription();
                            int image = list.get(index).getSource();
                            if (!sqliteOpenHelper.exists(R.drawable.aot2,user_name)) {
                                sqliteOpenHelper.insertData(String.valueOf(image), name, des,user_name);
                            } else {
                                Toast.makeText(getActivity(), "Truyện này đã tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                if(i==4){
                    index = i;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = list.get(index).getNameAuthors();
                            String des = list.get(index).getDescription();
                            int image = list.get(index).getSource();
                            if (!sqliteOpenHelper.exists(R.drawable.aa,user_name)) {
                                sqliteOpenHelper.insertData(String.valueOf(image), name, des,user_name);
                            } else {
                                Toast.makeText(getActivity(), "Truyện này đã tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                if(i==5){
                    index = i;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = list.get(index).getNameAuthors();
                            String des = list.get(index).getDescription();
                            int image = list.get(index).getSource();
                            if (!sqliteOpenHelper.exists(R.drawable.kmy,user_name)) {
                                sqliteOpenHelper.insertData(String.valueOf(image), name, des,user_name);
                            } else {
                                Toast.makeText(getActivity(), "Truyện này đã tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
                if(i==6){
                    index = i;
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = list.get(index).getNameAuthors();
                            String des = list.get(index).getDescription();
                            int image = list.get(index).getSource();
                            if (!sqliteOpenHelper.exists(R.drawable.frbk,user_name)) {
                                sqliteOpenHelper.insertData(String.valueOf(image), name, des,user_name);
                            } else {
                                Toast.makeText(getActivity(), "Truyện này đã tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            }
        });
        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(getActivity().SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}