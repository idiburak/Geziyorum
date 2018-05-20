package com.example.recluse.geziyorum.models;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Recluse on 1.05.2018.
 */

public class LocationModel {
    private int id;
    private int trip_id;
    private double longitude;
    private double latitude;
    private Date created_at;
    private int server_id;
    private int server_trip_id;

    public LocationModel(int id, int trip_id, double longitude, double latitude, Date created_at, int server_id, int server_trip_id) {
        this.id = id;
        this.trip_id = trip_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.created_at = created_at;
        this.server_id = server_id;
        this.server_trip_id = server_trip_id;
    }

    public LocationModel(int trip_id, double longitude, double latitude) {
        this(0,trip_id, longitude, latitude,new Date(Calendar.getInstance().getTime().getTime()),0,0);
    }

    //region Getters&Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public int getServer_trip_id() {
        return server_trip_id;
    }

    public void setServer_trip_id(int server_trip_id) {
        this.server_trip_id = server_trip_id;
    }


    //endregion
}
