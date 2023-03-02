package com.example.deliveryapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {


    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Users.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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


        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        if (sh.contains("email") && sh.contains("password") && sh.contains("cusId")  && sh.contains("name")  && sh.contains("phone") ) {
            //open MainActivity if success
            Intent intent = new Intent(this, ThirdPage.class);
            intent.putExtra("cusId", sh.getString("cusId", ""));
            intent.putExtra("name", sh.getString("name", ""));
            intent.putExtra("phone", sh.getString("phone", ""));
            startActivity(intent);
            finish();
        }
        else
        {

            final Button loginBtn = findViewById(R.id.loginBtn);
            final TextView createAccBtn = findViewById(R.id.txtCreate);


            final EditText email = findViewById(R.id.email);
            final EditText password = findViewById(R.id.password);


            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String emailTxt = email.getText().toString();
                    String passwordTxt = password.getText().toString();

                    //Check if input field empty
                    if (emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                        Toast.makeText(Login.this, "Please enter email or password!", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReference.orderByChild("email").equalTo(emailTxt).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //check if pone number is exist in database
                                if (snapshot.exists()) {

                                    com.example.deliveryapp.Users user = new com.example.deliveryapp.Users();
                                    //mobile is exist in firebase database
                                    // now get password of user from firebase and match it with user entered password
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        user = dataSnapshot.getValue(com.example.deliveryapp.Users.class);
                                    }
                                    final String getPassword = user.getPassword();

                                    if (getPassword.equals(passwordTxt)) {
                                        Toast.makeText(Login.this, "Successful Logged in", Toast.LENGTH_SHORT).show();
                                        //get user details
                                        String idTxt = user.getcusId();
                                        String nameTxt = user.getFullname();
                                        String phoneTxt = user.getPhone();


                                        // Storing data into SharedPreferences
                                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

                                        // Creating an Editor object to edit(write to the file)
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                        // Storing the key and its value as the data fetched from edittext
                                        myEdit.putString("cusId", idTxt);
                                        myEdit.putString("email", emailTxt);
                                        myEdit.putString("password", passwordTxt);
                                        myEdit.putString("name", nameTxt);
                                        myEdit.putString("phone", phoneTxt);

                                        // Once the changes have been made,
                                        // we need to commit to apply those changes made,
                                        // otherwise, it will throw an error
                                        myEdit.commit();

                                        //open MainActivity if success
                                        Intent intent = new Intent(Login.this, ThirdPage.class);
                                        intent.putExtra("cusId", sh.getString("cusId", ""));
                                        intent.putExtra("name", sh.getString("name", ""));
                                        intent.putExtra("phone", sh.getString("phone", ""));
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Login.this, "Email not registered", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Login.this, "Cannot fetch data from database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });

            createAccBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //open register activity
                    startActivity(new Intent(Login.this, com.example.deliveryapp.Register.class));
                }
            });
        }


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