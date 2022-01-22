package com.example.myshop.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myshop.R;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView camera;
    private ImageView printer;
    private ImageView accessories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        init();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProdActivity.class);
                intent.putExtra("category", "camera");
                startActivity(intent);
            }
        });

        printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProdActivity.class);
                intent.putExtra("category", "printer");
                startActivity(intent);
            }
        });

        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProdActivity.class);
                intent.putExtra("category", "accessories");
                startActivity(intent);
            }
        });

    }

    private void init(){

        camera = (ImageView)findViewById(R.id.camera);
        printer = (ImageView)findViewById(R.id.printer);
        accessories = (ImageView)findViewById(R.id.acces);
    }
}