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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.testaplication.Adapter.AuthorAdapter;
import com.example.testaplication.Adapter.CategoryApdater;
import com.example.testaplication.Manga.Author;
import com.example.testaplication.Manga.Category;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.AuthorDataScoure;
import com.example.testaplication.Sqlite.CategoryDataScoure;

import java.util.List;

public class AuthorFragment extends Fragment {
    private View view;

    private List<Author> authors;
    private AuthorDataScoure authorDataScoure;
    private AuthorAdapter authorAdapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_author_fragment,container,false);
        listView = view.findViewById(R.id.listview);
        registerForContextMenu(listView);
        listView.setLongClickable(true);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            setHasOptionsMenu(true);
        }

        authorDataScoure = new AuthorDataScoure(getContext());
        authorDataScoure.open();

        authors = authorDataScoure.getAllAuthors();
        authorAdapter = new AuthorAdapter(getContext(), R.layout.display_category, authors);
        listView.setAdapter(authorAdapter);

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

            builder.setTitle("Thêm tac gia");
            builder.setCancelable(false);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.layout_input_author,null);

            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            RadioButton male = (RadioButton) view.findViewById(R.id.radioButtonMale);
            RadioButton female = (RadioButton) view.findViewById(R.id.radioButtonFemale);
            EditText edtName = (EditText)view.findViewById(R.id.edtName);
            EditText edtCountry = (EditText)view.findViewById(R.id.edtCountry);
            builder.setView(view);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = edtName.getText().toString().trim();
                    String country = edtCountry.getText().toString().trim();
                    String gender = "Nam";
                    if(male.isChecked()) {
                        gender = male.getText().toString().trim();
                    }
                    if(female.isChecked()) {
                        gender = female.getText().toString().trim();
                    }
                    Author author = new Author(name, gender, country);
                    int id = authorDataScoure.getID(name, gender, country);
                    if(id > 0) {
                        Toast.makeText(getContext(), "Tac gia da ton tai: " + id, Toast.LENGTH_SHORT).show();
                    }
                    else if(authorDataScoure.insert(name, gender, country)) {
                        int ID = authorDataScoure.getID(name, gender, country);
                        author.setId(ID);
                        authors.add(author);
                        authorAdapter.setCategoryList(authors);
                        authorAdapter.notifyDataSetChanged();
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
            Author author = authors.get(info.position);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle("Cập nhập tac gia");
            builder.setCancelable(false);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(R.layout.layout_input_author, null);

            EditText edtName = (EditText) view.findViewById(R.id.edtName);
            EditText edtCountry = (EditText)view.findViewById(R.id.edtCountry);
            edtName.setText(author.getName());
            edtCountry.setText(author.getCountry());

            RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
            RadioButton male = (RadioButton) view.findViewById(R.id.radioButtonMale);
            RadioButton female = (RadioButton) view.findViewById(R.id.radioButtonFemale);

            String gender = author.getGender();
            if(gender.equals("Nam")) {
                male.setSelected(true);
            }
            else {
                female.setSelected(true);
            }

            builder.setView(view);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    author.setName(edtName.getText().toString().trim());
                    author.setCountry(edtCountry.getText().toString().trim());
                    String gioitinh = "Nam";
                    if(male.isChecked()) {
                        gioitinh = male.getText().toString().trim();
                    }
                    else {
                        gioitinh = female.getText().toString().trim();
                    }
                    author.setGender(gioitinh);
                    boolean update = authorDataScoure.update(author.getId(), author.getName(), author.getGender(), author.getCountry());
                    if(update) {
                        authors = authorDataScoure.getAllAuthors();
                        authorAdapter.setCategoryList(authors);
                        authorAdapter.notifyDataSetChanged();
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
            Author author = authors.get(info.position);

            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());

            builder1.setTitle("Xóa tac gia");
            builder1.setCancelable(false);
            builder1.setMessage("Bạn có chắc muốn xóa tac gia \" " + author.getName() + "\"?");

            builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean delete = authorDataScoure.delete(author.getId());
                    if(delete) {
                        authors = authorDataScoure.getAllAuthors();
                        authorAdapter.setCategoryList(authors);
                        authorAdapter.notifyDataSetChanged();
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
        authorDataScoure.close();
    }
}
