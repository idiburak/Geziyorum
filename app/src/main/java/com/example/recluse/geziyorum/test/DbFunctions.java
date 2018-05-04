package com.example.recluse.geziyorum.test;

import android.util.Log;

import com.example.recluse.geziyorum.db.helper.sqlite.LocationsDbHelper;
import com.example.recluse.geziyorum.db.helper.sqlite.TripsDbHelper;
import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.TripModel;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Recluse on 2.05.2018.
 */

public class DbFunctions {

    private void addTrip(){

        TripsDbHelper myDb = new TripsDbHelper(null); // not null change to this

        int trip_id = myDb.getNextTripId(1);

        TripModel trip = new TripModel(trip_id,1,"trip1","about1");


        boolean asd = myDb.insertTrip(trip);
        String result;
        if(asd){
            result = "yep";
        }else{
            result = "no";
        }
        Log.d("deb", result);

    }

    private void addLocation(){
        LocationsDbHelper myDb = new LocationsDbHelper(null);

        int location_id = myDb.getNextLocationId(1,1);

        LocationModel location = new LocationModel(location_id,1,1, 0,0);

        boolean query = myDb.insertLocation(location);

        Log.d("Add Location", Boolean.toString(query));

    }
}
