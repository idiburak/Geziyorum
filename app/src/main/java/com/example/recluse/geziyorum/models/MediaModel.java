package com.example.recluse.geziyorum.models;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Recluse on 1.05.2018.
 */

public class MediaModel {
    private int id;
    private int location_id;
    private int trip_id;
    private int user_id;
    private String file_name;
    private String path;
    private String media_type;
    private Date created_at;
    private int server_id;
    private int server_location_id;
    private int server_trip_id;

    public MediaModel(int id, int location_id, int trip_id, int user_id, String file_name, String media_type, Date created_at, int server_id, int server_location_id, int server_trip_id) {
        this.id = id;
        this.location_id = location_id;
        this.trip_id = trip_id;
        this.user_id = user_id;
        this.file_name = file_name;
        this.media_type = media_type;
        this.created_at = created_at;
        this.server_id = server_id;
        this.server_location_id = server_location_id;
        this.server_trip_id = server_trip_id;
        this.path = user_id + "\\" + media_type + "\\" + trip_id + "\\" + location_id + "\\" + file_name;
    }

    public MediaModel(int location_id, int trip_id, int user_id, String file_name, String media_type) {
        this(0,location_id,trip_id,user_id,file_name,media_type,new Date(Calendar.getInstance().getTime().getTime()),0,0,0);
    }

    //region Getter&Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
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

    public int getServer_location_id() {
        return server_location_id;
    }

    public void setServer_location_id(int server_location_id) {
        this.server_location_id = server_location_id;
    }

    public int getServer_trip_id() {
        return server_trip_id;
    }

    public void setServer_trip_id(int server_trip_id) {
        this.server_trip_id = server_trip_id;
    }


    //endregion



}
