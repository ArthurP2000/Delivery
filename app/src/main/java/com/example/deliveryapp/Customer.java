package com.example.deliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Customer extends AppCompatActivity {

    EditText fullName, phoneNumber, password;
    Button registerBtnDel, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        fullName = (EditText) findViewById(R.id.fullName);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        registerBtnDel = (Button) findViewById(R.id.registerBtnDel);
        login = (Button) findViewById(R.id.login);

        registerBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}