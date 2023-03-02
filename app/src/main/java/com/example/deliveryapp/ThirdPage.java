package com.example.deliveryapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ThirdPage extends AppCompatActivity {

    private ImageView qr_code;
    private TextView nameTxt;
    private TextView phoneTxt;
    private TextView countTxt;
    private Button btn_display;
    private Button logout;

    private String user_id;
    private String user_name;
    private String user_phone;

     DatabaseReference dbParcel = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Parcel.class.getSimpleName());
     DatabaseReference dbUser = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference(Users.class.getSimpleName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_page_design);

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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getString("cusId");
            user_name = extras.getString("name");
            user_phone = extras.getString("phone");
        }

        qr_code = (ImageView) findViewById(R.id.idIVQrcode);
        //view_details = (TextView) findViewById(R.id.view_details);
        countTxt = (TextView) findViewById(R.id.textView11);
        nameTxt = (TextView) findViewById(R.id.textView4);
        phoneTxt = (TextView) findViewById(R.id.textView5);
        //edit_id = (EditText) findViewById(R.id.edit_id);
        //btn_id = (Button) findViewById(R.id.btn_id);


        btn_display = (Button) findViewById(R.id.btn_display);
        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDisplay(user_id);
            }
        });

        //Create QR
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        new CreateQR().create(manager, qr_code, user_id);

        nameTxt.setText(user_name);
        phoneTxt.setText(user_phone);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //open register activity
                startActivity(new Intent(ThirdPage.this, Login.class));
                sh.edit().clear().commit();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbParcel.orderByChild("cusId").equalTo(user_id).addValueEventListener(listener);
    }

    @Override
    protected void onStop() {
        dbParcel.orderByChild("cusId").equalTo(user_id).removeEventListener(listener);
        super.onStop();
    }

    private ValueEventListener listener = new  ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            //Show loading
            AlertDialog.Builder builder = new AlertDialog.Builder(ThirdPage.this);
            builder.setCancelable(false); // if you want user to wait for some process to finish,
            builder.setView(R.layout.layout_dialog);
            final AlertDialog dialog = builder.create();
            dialog.show();

            if (snapshot.exists()){
                String count = String.valueOf(snapshot.getChildrenCount());
                countTxt.setText(count);

            }
            else
            {
                countTxt.setText("0");
            }
            dialog.dismiss();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public void openMain() {
        Intent intent = new Intent(this, ThirdPage.class);
        startActivity(intent);
    }

    public void openDisplay(String user_id) {
        Intent intent = new Intent(this, ThirdPageView.class);
        intent.putExtra("cusId",user_id);
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