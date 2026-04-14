package com.example.testaplication.Display;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.example.testaplication.Adapter.AdapterFavoriteView;
import com.example.testaplication.Adapter.ListFavoriteConstructor;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {
    private View view;
    private ListView list;
    List<ListFavoriteConstructor> list_favorite = new ArrayList<>();
    private MyDatabaseHelper sqliteOpenHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list_favorite,container,false);
        list = view.findViewById(R.id.list_view_favorite);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyUserName", Context.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("email", "");

        sqliteOpenHelper = new MyDatabaseHelper(view.getContext());
        list_favorite = sqliteOpenHelper.getResource(user_name);
        AdapterFavoriteView adapter = new AdapterFavoriteView(R.layout.custom_listview,list_favorite,view.getContext());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int index = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Bạn Có Chắc Chắc Muốn Xóa Khỏi Danh Sách Yêu Thích");
                builder.setCancelable(true);
                builder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String image = list_favorite.get(index).getImage();
                        sqliteOpenHelper.deleteWishList(user_name,image);
                        list_favorite.remove(index);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }

        });

        return view;
    }
}
