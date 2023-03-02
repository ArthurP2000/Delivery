package com.example.deliveryapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    //create object of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Users.class.getSimpleName());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        final EditText fullName = findViewById(R.id.fullName);
        final EditText phoneNumber = findViewById(R.id.phoneNumber);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);

        final Button registerBtn = findViewById(R.id.registerBtn);
        final Button registerBtnDel = findViewById(R.id.registerBtnDel);
        final TextView loginNowBtn = findViewById(R.id.loginNowBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               //get data from EditText into String variable
                                               final String fullNameTxt = fullName.getText().toString();
                                               final String phoneTxt = phoneNumber.getText().toString();
                                               final String emailTxt = email.getText().toString();
                                               final String passwordTxt = password.getText().toString();

                                               //check if user full all the fields before sending the data into firebase
                                               if (fullNameTxt.isEmpty() || phoneTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                                                   Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                                               } else {

                                                   databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                           long maxId = (snapshot.getChildrenCount() + 1);
                                                           String cusId = String.valueOf(maxId);

                                                           databaseReference.orderByChild("phone").equalTo(phoneTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                   //check if phone number is not registered before
                                                                   if (snapshot.exists()) {
                                                                       Toast.makeText(Register.this, "Phone is already registered", Toast.LENGTH_SHORT).show();
                                                                   } else {
                                                                       databaseReference.orderByChild("email").equalTo(emailTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                           @Override
                                                                           public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                                               if (snapshot.exists()) {
                                                                                   Toast.makeText(Register.this, "Email has already registered", Toast.LENGTH_SHORT).show();
                                                                               } else {
                                                                                   //send data to realtime database
                                                                                   //using phone number as unique identifier of every user
                                                                                   //so all the other details of user comes under phone number
                                                                                   databaseReference.child(cusId).child("cusId").setValue(cusId);
                                                                                   databaseReference.child(cusId).child("phone").setValue(phoneTxt);
                                                                                   databaseReference.child(cusId).child("fullname").setValue(fullNameTxt);
                                                                                   databaseReference.child(cusId).child("email").setValue(emailTxt);
                                                                                   databaseReference.child(cusId).child("password").setValue(passwordTxt);
                                                                                   databaseReference.child(cusId).child("password").setValue(passwordTxt);
                                                                                   databaseReference.child(cusId).child("password").setValue(passwordTxt);


                                                                                   //show a success message then finish the activity
                                                                                   Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                                                                   finish();
                                                                               }
                                                                           }



                                                                           @Override
                                                                           public void onCancelled(@NonNull DatabaseError error) {
                                                                           }
                                                                       });

                                                                   }
                                                               }

                                                               @Override
                                                               public void onCancelled(@NonNull DatabaseError error) {

                                                               }
                                                           });


                                                       }

                                                       @Override
                                                       public void onCancelled(@NonNull DatabaseError error) {

                                                       }

                                                   });
                                               }
                                           }
                                       });


        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerBtnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomer();
            }
        });
    }
    public void openCustomer() {
        Intent intent = new Intent(this, Customer.class);
        startActivity(intent);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}