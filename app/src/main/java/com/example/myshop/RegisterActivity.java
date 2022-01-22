package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText RegisterUserName,RegisterPhoneInput, RegisterPasInput;
    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button)findViewById(R.id.reg_btn);

        RegisterUserName = (EditText)findViewById(R.id.reg_username_input);
        RegisterPhoneInput = (EditText)findViewById(R.id.reg_phone_input);
        RegisterPasInput = (EditText)findViewById(R.id.reg_pas_input);
        loadingbar = new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }
    //Создание аккаунта
    private void CreateAccount() {
        String username = RegisterUserName.getText().toString();
        String phone = RegisterPhoneInput.getText().toString();
        String password = RegisterPasInput.getText().toString();

        // Проверка, что поля не пустые
        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Введите имя", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
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
            loadingbar.setTitle("Создание аккаунта");
            loadingbar.setMessage("Пожалуйста, подождите");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
        }

        ValidatePhone(username,phone,password);
    }

    //проверка зарегистированных пользователей
    private void ValidatePhone(final String username, final String phone, final String password) {

        //Создание объектов в бд

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        //Проверка пользователя по номеру телефона
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    // Какие данные мы добавляем в бд
                    HashMap<String,Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone",phone);
                    userDataMap.put("name",username);
                    userDataMap.put("password",password);

                    RootRef.child("Users").child(phone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Вывод результата регистрации
                            if (task.isSuccessful())
                            {
                                loadingbar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(loginIntent);

                            }
                            //Вывод ошибки
                            else
                            {
                                loadingbar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Упс.. что-то пошло не так", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

                //Если зарегистрирован, то перессылаем на страницу входа
                else {
                    loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Номер "+phone+" уже зарегистрирован", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(loginIntent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}