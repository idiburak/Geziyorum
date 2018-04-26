package com.example.recluse.geziyorum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {

    RecyclerView tripsRecyclerView;
    ArrayList<TripModel> tripList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        this.tripsRecyclerView = findViewById(R.id.tripsRecyclerView);

        tripList.add(new TripModel(1,"trip1", "info1"));
        tripList.add(new TripModel(2,"trip2", "info2"));
        tripList.add(new TripModel(3,"trip2", "info2"));
        tripList.add(new TripModel(4,"trip1", "info1"));
        tripList.add(new TripModel(5,"trip2", "info2"));
        tripList.add(new TripModel(6,"trip2", "info2"));
        tripList.add(new TripModel(7,"trip2", "info2"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager recLayoutManager = layoutManager;

        tripsRecyclerView.setLayoutManager(recLayoutManager);

        TripAdapter adapter = new TripAdapter(this,tripList);
        tripsRecyclerView.setAdapter(adapter);
    }
}
