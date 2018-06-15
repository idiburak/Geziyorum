package com.example.recluse.geziyorum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
import com.example.recluse.geziyorum.models.TripModel;

import java.util.ArrayList;

public class TripsActivity extends AppCompatActivity {

    private RecyclerView tripsRecyclerView;
    private ArrayList<TripModel> tripList;
    private LocalDbHelper localDbHelper;
    private static int USER_ID = 1;
    private TripAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        this.localDbHelper = new LocalDbHelper(this);

        this.tripsRecyclerView = findViewById(R.id.tripsRecyclerView);

        this.tripList = localDbHelper.GetTrips(USER_ID);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager recLayoutManager = layoutManager;

        tripsRecyclerView.setLayoutManager(recLayoutManager);

        adapter = new TripAdapter(this,tripList);
        tripsRecyclerView.setAdapter(adapter);
    }

}
