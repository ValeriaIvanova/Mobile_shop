package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.myshop.Users.HomeActivity;
import com.rey.material.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshop.Admin.AdminCategoryActivity;
import com.example.myshop.EnterUsers.Prevail;
import com.example.myshop.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private Button login_btn;
    private EditText LoginPhoneInput, LoginPasInput;
    private ProgressDialog loadingbar;
    private TextView AdminLink,UserLink;


    private String adminDb = "Users";
    private CheckBox cbRememberme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        login_btn = (Button)findViewById(R.id.login_btn);
        LoginPhoneInput = (EditText)findViewById(R.id.login_phone_input);
        LoginPasInput = (EditText)findViewById(R.id.login_pas_input);
        loadingbar = new ProgressDialog(this);
        cbRememberme = (CheckBox)findViewById(R.id.login_check);


        Paper.init(this);
        AdminLink = (TextView)findViewById(R.id.admin_panel);
        UserLink = (TextView)findViewById(R.id.not_admin_panel);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLink.setVisibility(View.INVISIBLE);
                UserLink.setVisibility(View.VISIBLE);
                login_btn.setText("Вход для админа");
                adminDb = "Admins";
            }
        });

        UserLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminLink.setVisibility(View.VISIBLE);
                UserLink.setVisibility(View.INVISIBLE);
                login_btn.setText("Войти");
                adminDb = "Users";
            }
        });
    }
    private void loginUser() {
        String phone = LoginPhoneInput.getText().toString();
        String password = LoginPasInput.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }

        //Диалоговое окно
        else
        {
            loadingbar.setTitle("Вход в приложение");
            loadingbar.setMessage("Пожалуйста, подождите");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
        }

        ValidateUser(phone,password);

    }

    //Проверка пользователя (существует или нет)
    private void ValidateUser(String phone, String password) {

        //Если нажат чек, то идет запись
        if (cbRememberme.isChecked()){
            Paper.book().write(Prevail.userPhonekey,phone);
            Paper.book().write(Prevail.userPaskey,password);
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(adminDb).child(phone).exists())
                {                                               //наследование данных
                    Users usersdata = dataSnapshot.child(adminDb).child(phone).getValue(Users.class);

                    //Проверка номера, а затем пароля
                    if (usersdata.getPhone().equals(phone))
                    {
                        if (usersdata.getPassword().equals(password)) {


                            if (adminDb.equals("Users")) //Пользователь
                            {
                                loadingbar.dismiss();
                                Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();

                                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(homeIntent);
                            }
                            else if (adminDb.equals("Admins"))  //Админ
                            {
                                loadingbar.dismiss();
                                Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_SHORT).show();

                                Intent homeIntent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(homeIntent);
                            }
                        }

                        else
                        {
                            loadingbar.dismiss();
                            Toast.makeText(LoginActivity.this, "Неверный пароль",Toast.LENGTH_SHORT).show();
                        }
                    }


                }

                else
                //Если поьзвателя с таким номером нет
                {
                    loadingbar.dismiss();
                    Toast.makeText(LoginActivity.this, "Пользователь с номером "+phone+" не существует", Toast.LENGTH_SHORT).show();

                    Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(regIntent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}