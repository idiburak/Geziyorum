package com.example.recluse.geziyorum.db.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import com.example.recluse.geziyorum.db.Constants;
import com.example.recluse.geziyorum.db.bycrypt.BCrypt;
import com.example.recluse.geziyorum.models.LocationMediaModel;
import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.MediaModel;
import com.example.recluse.geziyorum.models.NoteModel;
import com.example.recluse.geziyorum.models.TripModel;
import com.example.recluse.geziyorum.models.UserModel;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Recluse on 2.05.2018.
 */

public class LocalDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Geziyorum.db";
    public static final String USER_TABLE = "users";
    public static final String TRIP_TABLE = "trips";
    public static final String LOCATION_TABLE = "locations";
    public static final String MEDIA_TABLE = "media";
    public static final String NOTES_TABLE = "notes";
    public static final String SETTINGS_TABLE = "settings";


    private SQLiteDatabase readableDb;
    private SQLiteDatabase writableDb;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");

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
                        "id INTEGER PRIMARY KEY NOT NULL, " +
                        "username VARCHAR(50) NOT NULL, " +
                        "email VARCHAR(50) NOT NULL, " +
                        "password VARCHAR(50) NOT NULL, " +
                        "remember_token VARCHAR(255) NOT NULL, "+
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
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "user_id INTEGER NOT NULL, " +
                        "name VARCHAR(255) NOT NULL, " +
                        "about TEXT, " +
                        "total_distance DECIMAL, " +
                        "total_time DECIMAL, " +
                        "average_speed DECIMAL, " +
                        "created_at DATETIME, " +
                        "server_id INTEGER " +
                        "); "
                ;

        String sqlLocation =
                "CREATE TABLE IF NOT EXISTS " + LOCATION_TABLE +
                        " ( " +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "trip_id INTEGER NOT NULL, " +
                        "longitude DECIMAL, " +
                        "latitude DECIMAL, " +
                        "created_at DATE, " +
                        "server_id INTEGER, " +
                        "server_trip_id " +
                        "); "
                ;
        String sqlMedia =
                "CREATE TABLE IF NOT EXISTS " + MEDIA_TABLE +
                        " ( " +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "location_id INTEGER NOT NULL, " +
                        "trip_id INTEGER NOT NULL, " +
                        "user_id INTEGER NOT NULL, " +
                        "file_name VARCHAR(255) NOT NULL, " +
                        "path VARCHAR(255), " +
                        "media_type VARCHAR(50), " +
                        "created_at DATETIME NOT NULL, " +
                        "server_id INTEGER, " +
                        "server_location_id INTEGER, " +
                        "server_trip_id " +
                        " ); "
                ;

        String sqlNote =
                "CREATE TABLE IF NOT EXISTS " + NOTES_TABLE +
                        " ( " +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "location_id INTEGER NOT NULL, " +
                        "note VARCHAR(255) NOT NULL, " +
                        "created_at DATETIME NOT NULL," +
                        "server_id INTEGER," +
                        "server_location_id" +
                        " ); "
                ;

        String sqlSettings =
                "CREATE TABLE IF NOT EXISTS " + SETTINGS_TABLE +
                        " ( " +
                        "type VARCHAR(25), " +
                        "value INTEGER" +
                        " ); "
                ;

        db.execSQL(sqlUser);
        db.execSQL(sqlTrip);
        db.execSQL(sqlLocation);
        db.execSQL(sqlMedia);
        db.execSQL(sqlNote);
        db.execSQL(sqlSettings);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEDIA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(db);
    }


    //region Insert Functions

    public long insertUser(UserModel user){

        String birth_date = sdf.format(user.getBirthdate());
        String created_date = sdf.format(user.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("id", user.getId());
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("remember_token", user.getRemember_token());
        values.put("name_surname", user.getName_surname());
        values.put("photo_path", user.getPhoto_path());
        values.put("location", user.getLocation());
        values.put("bio", user.getBio());
        values.put("birthdate", birth_date);
        values.put("created_at", created_date);

        long insertedId = this.writableDb.insert(USER_TABLE, null, values);

        return insertedId;
    }

    public long insertTrip(TripModel trip){

        String date = sdf.format(trip.getCreated_at());

        ContentValues values = new ContentValues();

        //values.put("id", trip.getId());
        values.put("user_id", trip.getUser_id());
        values.put("name", trip.getName());
        values.put("about", trip.getAbout());
        values.put("total_distance", trip.getTotal_distance());
        values.put("total_time", trip.getTotal_time());
        values.put("average_speed", trip.getAverage_speed());
        values.put("created_at", date);
        values.put("server_id",trip.getServer_id());

        return this.writableDb.insert(TRIP_TABLE, null, values);

    }

    public long insertLocation(LocationModel location){

        String date = sdf.format(location.getCreated_at());

        ContentValues values = new ContentValues();

        //values.put("id",location.getId());
        values.put("trip_id", location.getTrip_id());
        values.put("longitude", location.getLongitude() );
        values.put("latitude", location.getLatitude());
        values.put("created_at", date);
        values.put("server_id", location.getServer_id());
        values.put("server_trip_id", location.getServer_trip_id());

        return this.writableDb.insert(LOCATION_TABLE, null, values);

    }

    public long insertMedia(MediaModel media){

        String date = sdf.format(media.getCreated_at());

        ContentValues values = new ContentValues();

        //values.put("id",media.getId());
        values.put("location_id",media.getLocation_id());
        values.put("trip_id",media.getTrip_id());
        values.put("user_id",media.getUser_id());
        values.put("file_name",media.getFile_name());
        values.put("path",media.getPath());
        values.put("media_type",media.getMedia_type());
        values.put("created_at",date);
        values.put("server_id",media.getServer_id());
        values.put("server_location_id",media.getServer_location_id());
        values.put("server_trip_id",media.getServer_trip_id());


        return this.writableDb.insert(MEDIA_TABLE, null, values);

    }

    public long insertNote(NoteModel note){
        String date = sdf.format(note.getCreated_at());

        ContentValues values = new ContentValues();

        //values.put("id",note.getId());
        values.put("location_id",note.getLocation_id());
        values.put("note",note.getNote());
        values.put("created_at",date);
        values.put("server_id",note.getServer_id());
        values.put("server_location_id",note.getServer_location_id());


        return this.writableDb.insert(NOTES_TABLE, null, values);

    }


    //endregion

    //region Update Functions

    public long updateUser(UserModel user) {

        String birth_date = sdf.format(user.getBirthdate());
        String created_date = sdf.format(user.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("id", user.getId());
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("remember_token", user.getRemember_token());
        values.put("name_surname", user.getName_surname());
        values.put("photo_path", user.getPhoto_path());
        values.put("location", user.getLocation());
        values.put("bio", user.getBio());
        values.put("birthdate", birth_date);
        values.put("created_at", created_date);

        long insertedId = this.writableDb.update(USER_TABLE, values, "id = ?", new String[]{Integer.toString(user.getId())});

        return insertedId;

    }

    public boolean updateTrip(TripModel trip){
        String date = sdf.format(trip.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("id", trip.getId());
        values.put("user_id", trip.getUser_id());
        values.put("name", trip.getName());
        values.put("about", trip.getAbout());
        values.put("total_distance", trip.getTotal_distance());
        values.put("total_time", trip.getTotal_time());
        values.put("average_speed", trip.getAverage_speed());
        values.put("created_at", date);
        values.put("server_id",trip.getServer_id());

        long insertedId = this.writableDb.update(TRIP_TABLE, values," id = ? ", new String[] {Integer.toString(trip.getId())});

        if(insertedId == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateLocation(LocationModel location){

        String date = sdf.format(location.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("id",location.getId());
        values.put("trip_id", location.getTrip_id());
        values.put("longitude", location.getLongitude() );
        values.put("latitude", location.getLatitude());
        values.put("created_at", date);
        values.put("server_id", location.getServer_id());
        values.put("server_trip_id", location.getServer_trip_id());

        long insertedId = this.writableDb.update(LOCATION_TABLE, values," id = ? ",
                new String[] {Integer.toString(location.getId())});

        if(insertedId == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateMedia(MediaModel media){

        String date = sdf.format(media.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("id",media.getId());
        values.put("location_id",media.getLocation_id());
        values.put("trip_id",media.getTrip_id());
        values.put("user_id",media.getUser_id());
        values.put("file_name",media.getFile_name());
        values.put("path",media.getPath());
        values.put("media_type",media.getMedia_type());
        values.put("created_at",date);
        values.put("server_id",media.getServer_id());
        values.put("server_location_id",media.getServer_location_id());
        values.put("server_trip_id",media.getServer_trip_id());

        long insertedId = this.writableDb.update(MEDIA_TABLE, values," id = ? ",
                new String[] {Integer.toString(media.getId())});

        if(insertedId == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateNotes(NoteModel note){

        String date = sdf.format(note.getCreated_at());

        ContentValues values = new ContentValues();

        values.put("id",note.getId());
        values.put("location_id",note.getLocation_id());
        values.put("note",note.getNote());
        values.put("created_at",date);
        values.put("server_id",note.getServer_id());
        values.put("server_location_id",note.getServer_location_id());

        long insertedId = this.writableDb.update(NOTES_TABLE, values," id = ? ",
                new String[] {Integer.toString(note.getId())});

        if(insertedId == 0){
            return false;
        }else{
            return true;
        }
    }

    //endregion

    //region Get Functions

    public ArrayList<UserModel> GetUsers(){
        ArrayList<UserModel> users = new ArrayList<>();

        String query = "SELECT id,username,email,password,remember_token,name_surname,photo_path,location,bio,birthdate,created_at FROM " + USER_TABLE + ";";
        Cursor cursor = this.readableDb.rawQuery(query,null);

        UserModel user;

        while(cursor.moveToNext()){
            user = new UserModel(
                    cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5),
                    cursor.getString(6),cursor.getString(7),cursor.getString(8),
                    GetSqlDateFromStr(cursor.getString(9)),GetSqlDateFromStr(cursor.getString(10))
            );
            users.add(user);
        }
        return users;
    }

    public ArrayList<TripModel> GetTrips(int user_id){
        ArrayList<TripModel> trips = new ArrayList<>();

        String query = "SELECT id,user_id,name,about,total_distance,total_time,average_speed,created_at,server_id FROM " + TRIP_TABLE + " WHERE user_id = ? ;";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{Integer.toString(user_id)});

        TripModel trip;

        while(cursor.moveToNext()){
            trip = new TripModel(
                    cursor.getInt(0),cursor.getInt(1),cursor.getString(2),
                    cursor.getString(3),cursor.getDouble(4),cursor.getDouble(5),
                    cursor.getDouble(6),GetSqlDateFromStr(cursor.getString(7)),cursor.getInt(8)
            );
            trips.add(trip);
        }

        return trips;
    }

    public ArrayList<LocationModel> GetLocations(int trip_id){
        ArrayList<LocationModel> locations = new ArrayList<>();

        String query = "SELECT id,trip_id,longitude,latitude,created_at,server_id,server_trip_id FROM " + LOCATION_TABLE + " WHERE trip_id = ? ORDER BY id ASC;";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{Integer.toString(trip_id)});

        LocationModel location;

        while(cursor.moveToNext()){



            location = new LocationModel(
                    cursor.getInt(0),cursor.getInt(1),cursor.getDouble(2),
                    cursor.getDouble(3), GetSqlDateFromStr(cursor.getString(4)),cursor.getInt(5),
                    cursor.getInt(6)
            );


            locations.add(location);
        }

        cursor.close();

        return locations;
    }

    public ArrayList<MediaModel> GetMedia(int location_id){
        ArrayList<MediaModel> media = new ArrayList<>();

        String query = "SELECT id,location_id,trip_id,user_id,file_name,path,media_type,created_at,server_id,server_location_id,server_trip_id FROM " + MEDIA_TABLE + " WHERE location_id = ? ;";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{Integer.toString(location_id)});

        MediaModel med;

        while(cursor.moveToNext()){
            med = new MediaModel(
                    cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),
                    cursor.getInt(3),cursor.getString(4),cursor.getString(6),
                    GetSqlDateFromStr(cursor.getString(7)),cursor.getInt(8),cursor.getInt(9),
                    cursor.getInt(10)
            );
            media.add(med);
        }

        return media;

    }

    public ArrayList<NoteModel> GetNotes(int location_id){
        ArrayList<NoteModel> notes = new ArrayList<>();

        String query = "SELECT id,location_id,note,created_at,server_id,server_location_id FROM " + NOTES_TABLE + " WHERE location_id = ? ;";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{Integer.toString(location_id)});

        NoteModel note;
        while (cursor.moveToNext()){
            note = new NoteModel(
                    cursor.getInt(0),cursor.getInt(1),cursor.getString(2),
                    GetSqlDateFromStr(cursor.getString(3)), cursor.getInt(4),cursor.getInt(5)
            );
            notes.add(note);

        }

        return notes;

    }

    public ArrayList<LocationMediaModel> GetPhotos(int trip_id){
        ArrayList<LocationMediaModel> photos = new ArrayList<>();

        String query = "SELECT l.id, m.id, l.longitude, l.latitude, m.path, m.file_name" +
                " FROM " + LOCATION_TABLE + " as l, " + MEDIA_TABLE + " as m WHERE l.id = m.location_id AND l.trip_id = ? AND m.media_type = ?;";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{Integer.toString(trip_id), Constants.MEDIA_TYPE_PHOTO});

        LocationMediaModel locationMediaModel;
        while(cursor.moveToNext()){
            locationMediaModel = new LocationMediaModel(cursor.getInt(0),trip_id,cursor.getInt(1),
                    cursor.getDouble(2),cursor.getDouble(3),cursor.getString(4),cursor.getString(5));
            photos.add(locationMediaModel);
        }

        return photos;
    }

    public ArrayList<LocationMediaModel> GetVideos(int trip_id){
        ArrayList<LocationMediaModel> videos = new ArrayList<>();

        String query = "SELECT l.id, m.id, l.longitude, l.latitude, m.path, m.file_name" +
                " FROM " + LOCATION_TABLE + " as l, " + MEDIA_TABLE + " as m WHERE l.id = m.location_id AND l.trip_id = ? AND m.media_type = ?;";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{Integer.toString(trip_id), Constants.MEDIA_TYPE_VIDEO});

        LocationMediaModel locationMediaModel;
        while(cursor.moveToNext()){
            locationMediaModel = new LocationMediaModel(cursor.getInt(0),trip_id,cursor.getInt(1),
                    cursor.getDouble(2),cursor.getDouble(3),cursor.getString(4),cursor.getString(5));
            videos.add(locationMediaModel);
        }

        return videos;
    }


    //endregion

    //region Check User Pass

    public boolean CheckUserName(String usernameOrEmail, String password){
        String query = "SELECT password FROM users WHERE username = ? OR email = ?";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{usernameOrEmail,usernameOrEmail});

        if(cursor.moveToNext()){
            return  BCrypt.checkpw(password, cursor.getString(0));
        }

        return false;
    }

    //endregion

    //region Single Get Functions
    public LocationModel getLastLocation(int trip_id){
        String query = "SELECT id,trip_id,longitude,latitude,created_at,server_id,server_trip_id FROM " + LOCATION_TABLE + " WHERE trip_id = ? ORDER BY id DESC;";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{Integer.toString(trip_id)});

        LocationModel location;

        if(cursor.moveToNext()){
            location = new LocationModel(
                    cursor.getInt(0),cursor.getInt(1),cursor.getDouble(2),
                    cursor.getDouble(3), GetSqlDateFromStr(cursor.getString(4)),cursor.getInt(5),
                    cursor.getInt(6)
            );
            return location;
        }

        return new LocationModel(0,0,0);

    }

    public TripModel getTrip(int trip_id){
        String query = "SELECT id,user_id,name,about,total_distance,total_time,average_speed,created_at,server_id FROM " + TRIP_TABLE + " WHERE id = ? ;";
        Cursor cursor = this.readableDb.rawQuery(query,new String[]{Integer.toString(trip_id)});

        TripModel trip;

        if(cursor.moveToNext()){
            trip = new TripModel(
                    cursor.getInt(0),cursor.getInt(1),cursor.getString(2),
                    cursor.getString(3),cursor.getDouble(4),cursor.getDouble(5),
                    cursor.getDouble(6),GetSqlDateFromStr(cursor.getString(7)),cursor.getInt(8)
            );
            return trip;
        }

        return new TripModel(0,"","");
    }





    //endregion

    public void FinishTrip(int trip_id){
        ArrayList<LocationModel> locations = GetLocations(trip_id);
        TripModel trip = getTrip(trip_id);

        float totalDistance = 0;
        for(int i=1 ; i<locations.size() ; i++){
            totalDistance = totalDistance + DistanceBetweenTwoLocation(locations.get(i-1),locations.get(i));
        }

        Date start = locations.get(0).getCreated_at();
        Date stop = locations.get(locations.size()-1).getCreated_at();
        long totalTime = (stop.getTime() - start.getTime())/1000;
        if(totalTime <= 0)
            totalTime = 1;
        trip.setTotal_distance(totalDistance);
        trip.setTotal_time((double)totalTime);
        trip.setAverage_speed(totalDistance/totalTime);

        updateTrip(trip);

        System.out.println(totalDistance + " " + totalTime);


    }

    private float DistanceBetweenTwoLocation(LocationModel loc1, LocationModel loc2){

        Location firstLocation = new Location("First Location");
        firstLocation.setLongitude(loc1.getLongitude());
        firstLocation.setLatitude(loc1.getLatitude());

        Location secondLocation = new Location("Second Location");
        secondLocation.setLongitude(loc2.getLongitude());
        secondLocation.setLatitude(loc2.getLatitude());

        return firstLocation.distanceTo(secondLocation);
    }

    private Date GetSqlDateFromStr(String dateStr){

        try {
            return new Date(sdf.parse(dateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(0);
    }
}
