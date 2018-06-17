package com.example.recluse.geziyorum.models;

public class LocationMediaModel {
    private int location_id;
    private int trip_id;
    private int media_id;
    private double longitude;
    private double latitude;
    private String path;
    private String file_name;


    public LocationMediaModel(int location_id, int trip_id, int media_id, double longitude, double latitude, String path, String file_name) {
        this.location_id = location_id;
        this.trip_id = trip_id;
        this.media_id = media_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.path = path;
        this.file_name = file_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
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

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
