package com.example.recluse.geziyorum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Recluse on 1.05.2018.
 */

public class TripsDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Geziyorum.db";
    public static final String TABLE_NAME = "trips";
    public static final String user_id = "user_id";
    public static final String trip_id = "trip_id";
    public static final String trip_name = "trip_name";
    public static final String about = "about";
    public static final String created_at = "created_at";



    public TripsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME  + " ( " +
                user_id + " INTEGER NOT NULL, " +
                trip_id + " INTEGER NOT NULL, " +
                trip_name + " VARCHAR(255) NOT NULL , " +
                about + " TEXT, " +
                created_at + " DATETIME, " +
                "PRIMARY KEY (" + user_id + "," + trip_id +")" +
                " );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
