package com.example.testaplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testaplication.R;

import java.util.List;

public class VoteAdapter extends BaseAdapter {
    private Context context;
    private List<Vote> list ;
    private int layout;

    public VoteAdapter(Context context, List<Vote> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
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
        ImageView img = view.findViewById(R.id.imageVote);
        TextView name = view.findViewById(R.id.nameVote);
        TextView vote = view.findViewById(R.id.sumvote);
        Vote get = list.get(i);
        img.setImageResource(get.getResource());
        name.setText(get.getName());
        vote.setText("Tổng Điểm Trung Bình: " + get.getTotalVote());
        return view;
    }
}
