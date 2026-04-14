package com.example.testaplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testaplication.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCustom extends BaseAdapter {
    Context context;
    int layout;
    List<MangaInformation> list = new ArrayList<>();

    public AdapterCustom(Context context, int layout, List<MangaInformation> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }


    @Override
    public int getCount() {
        return  list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout,null);
        ImageView img = view.findViewById(R.id.imgview);
        TextView  author = view.findViewById(R.id.textFisrt);
        TextView  des = view.findViewById(R.id.textSecond);
        MangaInformation custom = list.get(i);
        img.setImageResource(custom.getSource());
        author.setText(custom.getNameAuthors());
        des.setText(custom.getDescription());
        return view;
    }
}
