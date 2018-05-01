package com.example.recluse.geziyorum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.recluse.geziyorum.models.TripModel;

import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {

    RecyclerView tripsRecyclerView;
    ArrayList<TripModel> tripList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        this.tripsRecyclerView = findViewById(R.id.tripsRecyclerView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager recLayoutManager = layoutManager;

        tripsRecyclerView.setLayoutManager(recLayoutManager);

        TripAdapter adapter = new TripAdapter(this,tripList);
        tripsRecyclerView.setAdapter(adapter);
    }
}
