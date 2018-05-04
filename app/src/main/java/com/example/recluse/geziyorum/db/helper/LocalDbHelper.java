package com.example.recluse.geziyorum.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.TripModel;
import com.example.recluse.geziyorum.models.UserModel;

import java.text.SimpleDateFormat;

/**
 * Created by Recluse on 2.05.2018.
 */

public class LocalDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Geziyorum.db";
    public static final String USER_TABLE = "users";
    public static final String TRIP_TABLE = "trips";
    public static final String LOCATION_TABLE = "locations";

    private SQLiteDatabase readableDb;
    private SQLiteDatabase writableDb;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");

    public LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.readableDb = this.getReadableDatabase();
        this.writableDb = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlUser =
                "CREATE TABLE IF NOT EXISTS " + USER_TABLE +
                        " ( " +
                        "user_id INTEGER PRIMARY KEY NOT NULL, " +
                        "username VARCHAR(50) NOT NULL, " +
                        "email VARCHAR(50) NOT NULL, " +
                        "password VARCHAR NOT NULL, " +
                        "name_surname VARCHAR NOT NULL, " +
                        "photo_path VARCHAR(255), " +
                        "location VARCHAR(50), " +
                        "bio VARCHAR(255), " +
                        "birthdate DATE NOT NULL, " +
                        "created_at DATE NOT NULL " +
                        " ); "
                ;

        String sqlTrip =
                "CREATE TABLE IF NOT EXISTS " + TRIP_TABLE +
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
                        "); "
                ;

        String sqlLocation =
                "CREATE TABLE IF NOT EXISTS " + LOCATION_TABLE +
                        " ( " +
                        "location_id INTEGER NOT NULL, " +
                        "trip_id INTEGER NOT NULL, " +
                        "user_id INTEGER NOT NULL, " +
                        "longitude DECIMAL, " +
                        "latitude DECIMAL, " +
                        "created_at DATE, " +
                        "PRIMARY KEY (user_id,trip_id,location_id) " +
                        "); "
                ;

        db.execSQL(sqlUser);
        db.execSQL(sqlTrip);
        db.execSQL(sqlLocation);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE);
        onCreate(db);
    }

    //region Get Next Id Functions

    public int getNextTripId(int user_id){

        Cursor cursor = this.readableDb.rawQuery("SELECT trip_id FROM trips WHERE user_id = " + Integer.toString(user_id) + " ORDER BY trip_id DESC;",null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            return id + 1;
        }
        return 1;
    }

    public int getNextLocationId(int user_id, int trip_id){

        Cursor cursor = this.readableDb.rawQuery(
                "SELECT location_id FROM locations WHERE user_id = " + Integer.toString(user_id) +
                        " AND trip_id = " + Integer.toString(trip_id) +" ORDER BY location_id DESC",null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            return id + 1;
        }
        return 1;
    }

    //endregion

    //region Insert Functions

    public boolean insertLocation(LocationModel location){

        String date = sdf.format(location.getCreated_at());
        Log.d("Date",date);
        ContentValues values = new ContentValues();

        values.put("location_id",location.getLocation_id());
        values.put("trip_id", location.getTrip_id());
        values.put("user_id", location.getUser_id());
        values.put("longitude", location.getLongitude() );
        values.put("latitude", location.getLatitude());
        values.put("created_at", date);

        long insertedId = this.writableDb.insert(LOCATION_TABLE, null, values);
        Log.d("inserted location id", Long.toString(insertedId));
        if(insertedId == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertTrip(TripModel trip){

        String date = sdf.format(trip.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("trip_id", trip.getTrip_id());
        values.put("user_id", trip.getUser_id());
        values.put("trip_name", trip.getTrip_name());
        values.put("about", trip.getAbout());
        values.put("total_distance", trip.getTotal_distance());
        values.put("average_speed", trip.getAverage_speed());
        values.put("total_time", trip.getTotal_time());
        values.put("created_at", date);

        long insertedId = this.writableDb.insert(TRIP_TABLE, null, values);
        Log.d("inserted trip id", Long.toString(insertedId));

        if(insertedId == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertUser(UserModel user){

        String birth_date = sdf.format(user.getBirthdate());
        String created_date = sdf.format(user.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("user_id", user.getUser_id());
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("name_surname", user.getName_surname());
        values.put("photo_path", user.getPhoto_path());
        values.put("location", user.getLocation());
        values.put("bio", user.getBio());
        values.put("birthdate", birth_date);
        values.put("created_at", created_date);

        long insertedId = this.writableDb.insert(USER_TABLE, null, values);
        Log.d("inserted user id", Long.toString(insertedId));

        if(insertedId == -1){
            return false;
        }else{
            return true;
        }
    }


    //endregion

    //region Update Functions

    public boolean updateLocation(LocationModel location){

        String date = sdf.format(location.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("location_id",location.getLocation_id());
        values.put("trip_id", location.getTrip_id());
        values.put("user_id", location.getUser_id());
        values.put("longitude", location.getLongitude() );
        values.put("latitude", location.getLatitude());
        values.put("created_at", date);

        long insertedId = this.writableDb.update(LOCATION_TABLE, values,"trip_id = ? AND user_id = ? AND location_id = ? ",
                new String[] {Integer.toString(location.getTrip_id()), Integer.toString(location.getUser_id()),Integer.toString(location.getLocation_id())});

        if(insertedId == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateTrip(TripModel trip){
        String date = sdf.format(trip.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("trip_id", trip.getTrip_id());
        values.put("user_id", trip.getUser_id());
        values.put("trip_name", trip.getTrip_name());
        values.put("about", trip.getAbout());
        values.put("total_distance", trip.getTotal_distance());
        values.put("average_speed", trip.getAverage_speed());
        values.put("total_time", trip.getTotal_time());
        values.put("created_at", date);

        long insertedId = this.writableDb.update(TRIP_TABLE, values,"trip_id = ? AND user_id = ?", new String[] {Integer.toString(trip.getTrip_id()), Integer.toString(trip.getUser_id())});

        if(insertedId == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateUser(UserModel user) {

        String birth_date = sdf.format(user.getBirthdate());
        String created_date = sdf.format(user.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("user_id", user.getUser_id());
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("name_surname", user.getName_surname());
        values.put("photo_path", user.getPhoto_path());
        values.put("location", user.getLocation());
        values.put("bio", user.getBio());
        values.put("birthdate", birth_date);
        values.put("created_at", created_date);

        long insertedId = this.writableDb.update(USER_TABLE, values, "user_id = ?", new String[]{Integer.toString(user.getUser_id())});

        if (insertedId == 0) {
            return false;
        } else {
            return true;
        }
    }

    //endregion


}
