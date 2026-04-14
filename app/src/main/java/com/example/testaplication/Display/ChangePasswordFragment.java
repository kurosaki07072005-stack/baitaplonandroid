package com.example.testaplication.Display;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.testaplication.Account.Account;
import com.example.testaplication.Account.ChangePassword;
import com.example.testaplication.Account.Login;
import com.example.testaplication.Adapter.AdapterFavoriteView;
import com.example.testaplication.Adapter.ListFavoriteConstructor;

import com.example.testaplication.R;
import com.example.testaplication.Sqlite.LoginDataScoure;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private LoginDataScoure loginDataScoure;
    private EditText passwordCofirm;
    private EditText pass;
    private Button change;
    private EditText repass;
    private EditText userverifi;
    private EditText passverifi;
    private String passUser;
    private String userName;
    private View view;
    private Account account;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_change_password, container, false);
        passwordCofirm = view.findViewById(R.id.passwordconfirm);
        pass = view.findViewById(R.id.password);
        repass = view.findViewById(R.id.passwordNew);
        change = view.findViewById(R.id.btnChangePass);

        loginDataScoure = new LoginDataScoure(getContext());
        loginDataScoure.open();

        sharedPreferences = getActivity().getSharedPreferences("MyUserName", MODE_PRIVATE);
        passUser = sharedPreferences.getString("password", "");
        userName = sharedPreferences.getString("username", ""); // Lấy userName từ SharedPreferences

        account = new Account(userName, passUser);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChangePass();
            }
        });
        return view;
    }

    private void onClickChangePass(){
        String oldPassowrd = pass.getText().toString().trim();
        String newPassword = repass.getText().toString().trim();
        String cofirmPass = passwordCofirm.getText().toString().trim();

        if(!oldPassowrd.equals("") && oldPassowrd.equals(passUser) && newPassword.equals(cofirmPass) && loginDataScoure.changePassword(account, newPassword)) {
            Toast.makeText(getContext(), "Đổi mật khẩu thành công vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        }
        else {
            Toast.makeText(getContext(), "Đổi mật khẩu không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginDataScoure.close();
    }
}
