package com.example.testaplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testaplication.R;

import java.util.List;

public class DisplayVoteAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DisplayVote> list;

    public DisplayVoteAdapter(Context context, int layout, List<DisplayVote> list) {
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
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView userVote= view.findViewById(R.id.uservote);
        TextView points = view.findViewById(R.id.pointsvote);
        DisplayVote display = list.get(i);
        userVote.setText("Tên Tài Khoản: " + display.getUsername());
        points.setText("Số Điểm Vote: " + display.getPoints());
        return view;
    }
}
