package com.example.testaplication.Display;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.testaplication.Adapter.CategoryApdater;
import com.example.testaplication.Manga.Category;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.CategoryDataScoure;

import java.util.List;

public class ManagementCategoryFragment extends Fragment {
    private List<Category> categoryList;
    private CategoryDataScoure categoryDataScoure;
    private CategoryApdater categoryApdater;
    private ListView listView;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.management_category,container,false);
        listView = view.findViewById(R.id.listview);
        registerForContextMenu(listView);
        listView.setLongClickable(true);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            setHasOptionsMenu(true);
        }

        categoryDataScoure = new CategoryDataScoure(getContext());
        categoryDataScoure.open();

        categoryList = categoryDataScoure.getAllCategory();
        categoryApdater = new CategoryApdater(getContext(), R.layout.display_category, categoryList);
        listView.setAdapter(categoryApdater);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_caegory_management, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnu_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("Thêm thể loại");
            builder.setCancelable(false);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.layout_input_category,null);

            EditText edtName = (EditText)view.findViewById(R.id.edtName);
            EditText edtDescription = (EditText)view.findViewById(R.id.edtDescription);


            builder.setView(view);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = edtName.getText().toString().trim();
                    String description = edtDescription.getText().toString().trim();
                    Category category = new Category(name, description);
                    int id = categoryDataScoure.getID(name, description);
                    if(id > 0) {
                        Toast.makeText(getContext(), "The loai da ton tai: " + id, Toast.LENGTH_SHORT).show();
                    }
                    else if(categoryDataScoure.insert(name, description)) {
                        int ID = categoryDataScoure.getID(name, description);
                        category.setId(ID);
                        categoryList.add(category);
                        categoryApdater.setCategoryList(categoryList);
                        categoryApdater.notifyDataSetChanged();
                        Toast.makeText(getContext(), "them thanh cong", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "them khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater =  getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_update_delete_category,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if(id ==  R.id.mnu_update) {
            Category category = categoryList.get(info.position);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("Cập nhập thể loại");
            builder.setCancelable(false);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.layout_input_category, null);

            EditText edtName = (EditText) view.findViewById(R.id.edtName);
            EditText edtDescription = (EditText) view.findViewById(R.id.edtDescription);
            edtName.setText(category.getName());
            edtDescription.setText(category.getDescription());

            builder.setView(view);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    category.setName(edtName.getText().toString().trim());
                    category.setDescription(edtDescription.getText().toString().trim());

                    boolean update = categoryDataScoure.update(category.getId(), category.getName(), category.getDescription());
                    if(update) {
                        categoryList = categoryDataScoure.getAllCategory();
                        categoryApdater.setCategoryList(categoryList);
                        categoryApdater.notifyDataSetChanged();
                        Toast.makeText(getContext(), "update thanh cong", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "update khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });


            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        else if(id == R.id.mnu_delete) {
            Category category = categoryList.get(info.position);

            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());

            builder1.setTitle("Xóa thể loại");
            builder1.setCancelable(false);
            builder1.setMessage("Bạn có chắc muốn xóa thể loại \" " + category.getName() + "\"?");

            builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean delete = categoryDataScoure.delete(category.getId());
                    if(delete) {
                        categoryList = categoryDataScoure.getAllCategory();
                        categoryApdater.setCategoryList(categoryList);
                        categoryApdater.notifyDataSetChanged();
                        Toast.makeText(getContext(), "xoa thanh cong", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog1 = builder1.create();
            alertDialog1.show();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryDataScoure.close();
    }
}
