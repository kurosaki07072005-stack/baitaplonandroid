package com.example.testaplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testaplication.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoCustomListView extends BaseAdapter implements Filterable {
    Context context;
    int layout;
    List<MangaInformation> list = new ArrayList<>();
    List<MangaInformation> listori = new ArrayList<>();


    public PhotoCustomListView(Context context, int layout, List<MangaInformation> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.listori = list;
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
        ImageView img = view.findViewById(R.id.imagelv);
        TextView name = view.findViewById(R.id.namelv);
        TextView author = view.findViewById(R.id.authorlv);
        MangaInformation db = list.get(i);
        img.setImageResource(db.getSource());
        name.setText(db.getNameAuthors());
        author.setText(db.getDescription());
        return view;
    }

    @Override
    public Filter getFilter() {
        return  new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searching_name = charSequence.toString().toLowerCase();
                if(searching_name.isEmpty()) {
                    list = listori;
                }
                    else{
                        List<MangaInformation> list2 = new ArrayList<>();
                        for(MangaInformation custom : listori){
                            if(custom.getNameAuthors().toLowerCase().contains(searching_name)){
                                list2.add(custom);
                            }
                        }
                        list = list2;

                    }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                    return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) { // sau khi loc tu cai tren se ra cai nay do
                list = (List<MangaInformation>) filterResults.values; // gan no cho list ne
                notifyDataSetChanged(); // thong bao du lieu thay doi ne
            }
        };
    }
}
