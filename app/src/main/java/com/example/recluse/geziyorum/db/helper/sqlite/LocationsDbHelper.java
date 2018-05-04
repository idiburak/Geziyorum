package com.example.recluse.geziyorum.db.helper.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.TripModel;

import java.text.SimpleDateFormat;

/**
 * Created by Recluse on 2.05.2018.
 */

public class LocationsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Geziyorum.db";
    public static final String TABLE_NAME = "locations";

    public LocationsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(

                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " ( " +

                        "location_id INTEGER NOT NULL, " +
                        "trip_id INTEGER NOT NULL, " +
                        "user_id INTEGER NOT NULL, " +
                        "longitude DECIMAL, " +
                        "latitude DECIMAL, " +
                        "created_at DATE, " +
                        "PRIMARY KEY (user_id,trip_id,location_id) " +

                        ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int getNextLocationId(int user_id, int trip_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT location_id FROM locations WHERE user_id = " + Integer.toString(user_id) +
                        " AND trip_id = " + Integer.toString(trip_id) +" ORDER BY location_id DESC",null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            Log.d("index:",Integer.toString(id));
            return id + 1;
        }

        return 1;
    }

    public boolean insertLocation(LocationModel location){

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String date = sdf.format(location.getCreated_at());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("location_id",location.getLocation_id());
        values.put("trip_id", location.getTrip_id());
        values.put("user_id", location.getUser_id());
        values.put("longitude", location.getLongitude() );
        values.put("latitude", location.getLatitude());
        values.put("created_at", date);

        long insertedId = db.insert(TABLE_NAME, null, values);
        Log.d("inserted location id :", Long.toString(insertedId));
        if(insertedId == -1){
            return false;
        }else{
            return true;
        }

    }

    public boolean updateLocation(LocationModel location){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String date = sdf.format(location.getCreated_at());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("location_id",location.getLocation_id());
        values.put("trip_id", location.getTrip_id());
        values.put("user_id", location.getUser_id());
        values.put("longitude", location.getLongitude() );
        values.put("latitude", location.getLatitude());
        values.put("created_at", date);

        long insertedId = db.update(TABLE_NAME, values,"trip_id = ? AND user_id = ? AND location_id = ? ",
                new String[] {Integer.toString(location.getTrip_id()), Integer.toString(location.getUser_id()),Integer.toString(location.getLocation_id())});
        if(insertedId == 0){
            return false;
        }else{
            return true;
        }
    }
}
