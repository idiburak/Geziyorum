package com.example.recluse.geziyorum.models;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Recluse on 1.05.2018.
 */

public class TripModel {
    private int id;
    private int user_id;
    private String name;
    private String about;
    private double total_distance;
    private double total_time;
    private double average_speed;
    private Date created_at;
    private int server_id;

    public TripModel(int id, int user_id, String name, String about, double total_distance, double total_time, double average_speed, Date created_at, int server_id) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.about = about;
        this.total_distance = total_distance;
        this.total_time = total_time;
        this.average_speed = average_speed;
        this.created_at = created_at;
        this.server_id = server_id;
    }

    public TripModel(int user_id, String name, String about) {
        this(0,user_id,name,about,0,0,0,new Date(Calendar.getInstance().getTime().getTime()),0);
    }

    //region Getters&Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getTotal_time() {
        return total_time;
    }

    public void setTotal_time(double total_time) {
        this.total_time = total_time;
    }

    public double getAverage_speed() {
        return average_speed;
    }

    public void setAverage_speed(double average_speed) {
        this.average_speed = average_speed;
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


    //endregion
}
