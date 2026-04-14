package com.example.testaplication.Account;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testaplication.MainActivity;
import com.example.testaplication.R;
import com.example.testaplication.Sqlite.LoginDataScoure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private static final int RESQUET_CODE = 1;
    private CheckBox checkBox;
    private Button btnSignUp;
    private Button btnLogin;
    private TextView textUsername;
    private TextView textPassword;
    private TextView textRePassword;
    private TextView textGmail;
    private  LoginDataScoure loginDataScoure;
    private Button btnBack;

    private ArrayAdapter adapter;

    private ListView listView;

    private  List<Account> list  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mapping();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = textPassword.getText().toString().trim();
                String repass = textRePassword.getText().toString().trim();

                if(!pass.equals(repass)){
                    Toast.makeText(SignUp.this, "Mật Khẩu Không Trùng Khớp Vui Lòng Nhập Lại", Toast.LENGTH_SHORT).show();
                }
                else if(!checkBox.isChecked()){
                    Toast.makeText(SignUp.this, "Vui Lòng Xác Nhận Đã Đủ 18 Tuoi", Toast.LENGTH_SHORT).show();
                }
                else{
                    checkPass();
                }
            }
        });
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);

            }
        });
    }
    public void checkPass(){
        String pass = textPassword.getText().toString().trim();
        String username = textGmail.getText().toString();
        Pattern patternPassword = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{6,28})");
        Matcher matcher = patternPassword.matcher(pass);
        Pattern checkEmail = Pattern.compile("^[\\w-_.+]*[\\w-_.]@[\\w]+(\\.[\\w]+)+$");

        Matcher matcherUser = checkEmail.matcher(username);
        if(patternPassword.matcher(pass).matches() == false){
            Toast.makeText(this, "Mật khẩu phải bao gồm ít nhất 1 chữ hoa và 1 kí tự cái đặc biệt độ dài từ 6-14 ký tự", Toast.LENGTH_SHORT).show();
        }
        else  if(checkEmail.matcher(username).matches() == false){
            Toast.makeText(this, "Vui lòng nhập đúng định dạng gmail", Toast.LENGTH_SHORT).show();
        }
        else{
            clickSignUp();
        }
    }
    public void mapping(){
        btnSignUp = findViewById(R.id.btnSignUpx);
        textPassword = findViewById(R.id.edtPassword);
        textGmail = findViewById(R.id.edtGmail);
        checkBox = findViewById(R.id.checkboxconfirm);
        textRePassword = findViewById(R.id.edtRePassword);
        btnBack = findViewById(R.id.btnLogin);
    }
    private void clickSignUp(){
        String password = textPassword.getText().toString().trim();
        String email = textGmail.getText().toString().trim();
        Account account = new Account(email, password);
        loginDataScoure = new LoginDataScoure(this);
        loginDataScoure.open();

        boolean createAccount = loginDataScoure.createAccount(account);
        if(createAccount) {
            Toast.makeText(SignUp.this, "Đăng Ký thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
            finishActivity(RESQUET_CODE);
        }
        else {
            Toast.makeText(SignUp.this, "Tài Khoản Đã Tồn Tại Trong Hệ Thống", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        loginDataScoure.close();
        super.onDestroy();
    }
}