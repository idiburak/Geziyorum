package com.example.recluse.geziyorum.db.helper.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.recluse.geziyorum.models.TripModel;

import java.text.SimpleDateFormat;

/**
 * Created by Recluse on 1.05.2018.
 */

public class TripsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Geziyorum.db";
    public static final String TABLE_NAME = "trips";

    public TripsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(

                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        " ( " +

                        "trip_id INTEGER NOT NULL, " +
                        "user_id INTEGER NOT NULL, " +
                        "trip_name VARCHAR(255) NOT NULL, " +
                        "about TEXT, " +
                        "total_distance DECIMAL, " +
                        "average_speed DECIMAL, " +
                        "total_time DECIMAL, " +
                        "created_at DATETIME, " +
                        "PRIMARY KEY (user_id,trip_id) " +

                        ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int getNextTripId(int user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(user_id) FROM trips WHERE user_id = " + Integer.toString(user_id) + " GROUP BY user_id",null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            Log.d("index:",Integer.toString(id));
            return id + 1;
        }




        return 0;
    }

    public boolean insertTrip(TripModel trip){

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String date = sdf.format(trip.getCreated_at());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trip_id", trip.getTrip_id());
        values.put("user_id", trip.getUser_id());
        values.put("trip_name", trip.getTrip_name());
        values.put("about", trip.getAbout());
        values.put("total_distance", trip.getTotal_distance());
        values.put("average_speed", trip.getAverage_speed());
        values.put("total_time", trip.getTotal_time());
        values.put("created_at", date);

        long insertedId = db.insert(TABLE_NAME, null, values);
        if(insertedId == -1){
            return false;
        }else{
            return true;
        }

    }

    public boolean updateTrip(TripModel trip){
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String date = sdf.format(trip.getCreated_at());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("trip_id", trip.getTrip_id());
        values.put("user_id", trip.getUser_id());
        values.put("trip_name", trip.getTrip_name());
        values.put("about", trip.getAbout());
        values.put("total_distance", trip.getTotal_distance());
        values.put("average_speed", trip.getAverage_speed());
        values.put("total_time", trip.getTotal_time());
        values.put("created_at", date);

        long insertedId = db.update(TABLE_NAME, values,"trip_id = ? AND user_id = ?", new String[] {Integer.toString(trip.getTrip_id()), Integer.toString(trip.getUser_id())});
        if(insertedId == 0){
            return false;
        }else{
            return true;
        }
    }
}
