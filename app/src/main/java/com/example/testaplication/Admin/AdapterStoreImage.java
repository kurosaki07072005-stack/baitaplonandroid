package com.example.testaplication.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.testaplication.Admin.ImageList;
import com.example.testaplication.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterStoreImage extends BaseAdapter {
    private Context context;
    private int layout;
    private List<ImageList> list = new ArrayList<>();

    public AdapterStoreImage(Context context, int layout, List<ImageList> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
       return list.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        ImageView image = view.findViewById(R.id.imageStore);
        ImageList get = list.get(i);
        image.setImageResource(get.getId());
        return view;
    }
}
