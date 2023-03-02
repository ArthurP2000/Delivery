package com.example.deliveryapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThirdPageView extends AppCompatActivity {
    private RecyclerView recyclerView;
    ParcelAdapter adapter; // Create Object of the Adapter class
    DatabaseReference dbase; // Create object of the
    // Firebase Realtime Database
    //Customer id from display page
    String cusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page_view);

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
            cusId = extras.getString("cusId");
            //The key argument here must match that used in the other activity
        }


        // Create a instance of the database and get
        // its reference
        dbase = FirebaseDatabase.getInstance("https://fir-f9b19-default-rtdb.firebaseio.com/").getReference("Parcel");

        dbase.orderByChild("cusId").equalTo(cusId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists())
                { }
                else {
                    Toast.makeText(ThirdPageView.this, "No parcel available ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //Create view
        recyclerView = findViewById(R.id.recycler1);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));


        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        FirebaseRecyclerOptions<Parcel> options
                = new FirebaseRecyclerOptions.Builder<Parcel>()
                .setQuery(dbase.orderByChild("cusId").equalTo(cusId), Parcel.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new ParcelAdapter(options);
        recyclerView.setAdapter(adapter);

    }
    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
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
