package com.example.testaplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testaplication.Manga.Author;
import com.example.testaplication.Manga.Category;
import com.example.testaplication.R;

import java.util.List;

public class AuthorAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Author> authorList;

    public AuthorAdapter(Context context, int layout, List<Author> authorList) {
        this.context = context;
        this.layout = layout;
        this.authorList = authorList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public List<Author> getCategoryList() {
        return authorList;
    }

    public void setCategoryList(List<Author> categoryList) {
        this.authorList = categoryList;
    }

    @Override
    public int getCount() {
        return authorList.size();
    }

    @Override
    public Object getItem(int i) {
        return authorList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return authorList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.layout_author, null);

        Author author = authorList.get(i);

        EditText name = view.findViewById(R.id.edtName);
        name.setText(author.getName());

        EditText gender = view.findViewById(R.id.edtgioitinh);
        gender.setText(author.getGender());

        EditText country = view.findViewById(R.id.edtCountry);
        country.setText(author.getCountry());
        return view;
    }
}