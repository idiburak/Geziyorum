package com.example.recluse.geziyorum.models;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Recluse on 1.05.2018.
 */

public class TripModel {
    private int trip_id;
    private int user_id;
    private String trip_name;
    private String about;
    private double total_distance;
    private double average_speed;
    private double total_time;
    private Date created_at;

    public TripModel(int trip_id, int user_id, String trip_name, String about) {
        this.trip_id = trip_id;
        this.user_id = user_id;
        this.trip_name = trip_name;
        this.about = about;
        this.created_at = new Date(Calendar.getInstance().getTime().getTime());
        this.total_distance = 0;
        this.total_time = 0;
        this.average_speed = 0;
    }

    //region Getters&Setters

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

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public double getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(double total_distance) {
        this.total_distance = total_distance;
    }

    public double getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(double average_speed) {
        this.average_speed = average_speed;
    }

    public double getTotal_time() {
        return total_time;
    }

    public void setTotal_time(double total_time) {
        this.total_time = total_time;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    //endregion
}
