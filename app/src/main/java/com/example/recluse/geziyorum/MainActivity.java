package com.example.recluse.geziyorum;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.TripModel;
import com.example.recluse.geziyorum.models.UserModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private CardView cardTravel, cardGallery, cardProfile, cardSettings, cardLogout;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Defining card views
        this.cardTravel = findViewById(R.id.cardTravel);
        this.cardGallery = findViewById(R.id.cardGallery);
        this.cardProfile = findViewById(R.id.cardProfile);
        this.cardSettings = findViewById(R.id.cardSettings);
        this.cardLogout = findViewById(R.id.cardLogout);

        //Add click listeners
        cardTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TravelActivity.class);
                startActivity(intent);
            }
        });
        cardGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TripsActivity.class);
                startActivity(intent);
            }
        });
        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked:", "4");
            }
        });
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked:", "5");
            }
        });
    }



}
