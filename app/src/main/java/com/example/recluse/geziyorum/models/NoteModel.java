package com.example.recluse.geziyorum.models;

import java.sql.Date;
import java.util.Calendar;

public class NoteModel {
    private int id;
    private int location_id;
    private String note;
    private Date created_at;
    private int server_id;
    private int server_location_id;

    public NoteModel(int id, int location_id, String note, Date created_at, int server_id, int server_location_id) {
        this.id = id;
        this.location_id = location_id;
        this.note = note;
        this.created_at = created_at;
        this.server_id = server_id;
        this.server_location_id = server_location_id;
    }

    public NoteModel(int location_id, String note) {
        this(0,location_id,note,new Date(Calendar.getInstance().getTime().getTime()),0,0);
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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


    //endregion
}
