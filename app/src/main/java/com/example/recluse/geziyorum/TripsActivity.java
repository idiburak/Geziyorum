package com.example.recluse.geziyorum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

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
        TripModel t1 = new TripModel(1,"1","2");
        TripModel t2 = new TripModel(2,"2","2");
        TripModel t3 = new TripModel(3,"3","2");

        tripList.add(t1);
        tripList.add(t2);
        tripList.add(t3);

        TripAdapter adapter = new TripAdapter(this,tripList);
        tripsRecyclerView.setAdapter(adapter);
    }
}
