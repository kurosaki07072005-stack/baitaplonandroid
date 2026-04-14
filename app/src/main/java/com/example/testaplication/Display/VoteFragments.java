package com.example.testaplication.Display;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testaplication.Adapter.Vote;
import com.example.testaplication.Adapter.VoteAdapter;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.SqlVoteManga;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VoteFragments extends Fragment {
    private View view;
    private List<Vote> list = new ArrayList<>();
    private ListView listview;
    private SqlVoteManga vote;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vote,container,false);
        vote = new SqlVoteManga(view.getContext());
        listview = view.findViewById(R.id.listvote);
        double totalVote = vote.totalVote("Black Clover");
        double totalVoteAot = vote.totalVote("Attack On Titan");
        DecimalFormat df = new DecimalFormat("#.###");
        double roundedTotalVote = Double.parseDouble(df.format(totalVote));
        double roundedTotalVoteAot = Double.parseDouble(df.format(totalVoteAot));
        list.add(new Vote(R.drawable.blcv,"Black Clover",String.valueOf(roundedTotalVote)));
        list.add(new Vote(R.drawable.aot2,"Attack On Titan",String.valueOf(roundedTotalVoteAot)));
        VoteAdapter adapter = new VoteAdapter(getActivity(),list, R.layout.custommanga);
        listview.setAdapter(adapter);
        return view;
    }
}
