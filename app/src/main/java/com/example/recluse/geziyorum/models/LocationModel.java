package com.example.recluse.geziyorum.models;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Recluse on 1.05.2018.
 */

public class LocationModel {
    private int location_id;
    private int trip_id;
    private int user_id;
    private Date created_at;
    private double longitude;
    private double latitude;

    public LocationModel(int location_id, int trip_id, int user_id, double longitude, double latitude) {
        this.location_id = location_id;
        this.trip_id = trip_id;
        this.user_id = user_id;
        this.created_at = new Date(Calendar.getInstance().getTime().getTime());
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //region Getters&Setters

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    //endregion
}
