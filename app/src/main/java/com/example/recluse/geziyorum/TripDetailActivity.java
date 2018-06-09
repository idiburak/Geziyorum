package com.example.recluse.geziyorum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recluse.geziyorum.models.TripModel;
import com.google.gson.Gson;

public class TripDetailActivity extends AppCompatActivity {
    private final Gson gson = new Gson();

    private  TextView id,name,info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        id = findViewById(R.id.detailTripId);
        name = findViewById(R.id.detailTripName);
        info = findViewById(R.id.detailTripAbout);

        Bundle extras = this.getIntent().getExtras();

        String tripJson = (String) extras.get("trip");
        TripModel trip = gson.fromJson(tripJson,TripModel.class);

        id.setText(Integer.toString(trip.getId()));
        name.setText(trip.getName());
        info.setText(trip.getAbout());

    }
}
