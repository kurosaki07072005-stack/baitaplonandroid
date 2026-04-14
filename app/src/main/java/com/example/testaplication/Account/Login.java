package com.example.testaplication.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testaplication.AdminManga;
import com.example.testaplication.MainActivity;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.LoginDataScoure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private static final int RESQUET_CODE = 1;
    private LoginDataScoure loginDataScoure;
    private Button forgotPass;
    private TextView gmail;
    private TextView user;
    private TextView pass;
    private Button btnLogin;
    private Button btnSignUp;

    private CheckBox rememberAccount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        mapping();
        //       forgotPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater inflater = getLayoutInflater();
//                final View alertLayout = inflater.inflate(R.layout.layoutforgotpass, null);
//                final EditText textGmail = alertLayout.findViewById(R.id.gmail);
//                Button send = alertLayout.findViewById(R.id.sendPass);
//                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
//                builder.setView(alertLayout);
//                builder.setCancelable(true);
//                send.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//                        auth.sendPasswordResetEmail(textGmail.getText().toString())
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(Login.this, "Mã Xác Nhận Đã Gửi Tới Email Của Bạn", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
        //       });

        SharedPreferences preferences = getSharedPreferences("remember-account", Context.MODE_PRIVATE);
        String userName = preferences.getString("username", "");
        String password = preferences.getString("password", "");
        boolean remember = preferences.getBoolean("remember", false);
        user.setText(userName);
        pass.setText(password);

        if(remember) {
            onClickLogin();
            rememberAccount.setChecked(true);
        }
        else {
            rememberAccount.setChecked(false);
        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Vui Long Nhap Day Du Thong Tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    onClickLogin();
                }
            }
        });
    }
    private void onClickLogin(){
        String email = user.getText().toString().trim();
        String password = pass.getText().toString().trim();

        Account account = new Account(email, password);
        loginDataScoure = new LoginDataScoure(this);
        loginDataScoure.open();

        if(email.equals("admin@gmail.com") && password.equals("Admin@")) {
            Intent intent = new Intent(Login.this, AdminManga.class);
            SharedPreferences sharedPreferences = getSharedPreferences("MyUserName", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.putString("username", account.getUsername());
            editor.putString("password", account.getPassword());
            editor.apply();

            if(rememberAccount.isChecked()) {
                SharedPreferences preferences = getSharedPreferences("remember-account", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("username", account.getUsername());
                edit.putString("password", account.getPassword());
                edit.putBoolean("remember", true);
                edit.apply();
            }

            startActivity(intent);
        }
        else if(loginDataScoure.existAccount(account)) {
            Toast.makeText(Login.this, "Đăng Nhập thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("MyUserName", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.putString("username", account.getUsername());
            editor.putString("password", account.getPassword());
            editor.apply();

            if(rememberAccount.isChecked()) {
                SharedPreferences preferences = getSharedPreferences("remember-account", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString("username", account.getUsername());
                edit.putString("password", account.getPassword());
                edit.putBoolean("remember", true);
                edit.apply();
            }

            startActivity(intent);
//            finishActivity(RESQUET_CODE);
        }
        else {
            Toast.makeText(Login.this, "Tên Tài Khoản Hoặc Mật Khẩu Không Chính Xác", Toast.LENGTH_SHORT).show();
        }
    }
    public void mapping(){
        rememberAccount = findViewById(R.id.remember_account);
        user = findViewById(R.id.textUsername);
        pass = findViewById(R.id.textPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUpx);
        gmail = findViewById(R.id.edtGmail);
    }

    @Override
    protected void onDestroy() {
        loginDataScoure.close();
        super.onDestroy();
    }
}
