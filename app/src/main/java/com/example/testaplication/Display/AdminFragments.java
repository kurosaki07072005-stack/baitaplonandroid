package com.example.testaplication.Display;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testaplication.Admin.AccFragment;
import com.example.testaplication.Admin.ManagementAddManga;
import com.example.testaplication.Admin.MangaManagement;
import com.example.testaplication.R;

public class AdminFragments extends Fragment {
    private View view;
    private Button managementManga;
    private Button managementCategory;
    private Button managementAccount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.adminmanagement, container,false);
         managementManga = view.findViewById(R.id.qltruyen);
         managementManga.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getActivity(), MangaManagement.class);
                 startActivity(intent);
             }
         });
         managementAccount = view.findViewById(R.id.qltaikhoan);
         managementAccount.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getActivity(), AccFragment.class);
                 startActivity(intent);
             }
         });

        return view;
    }
}
