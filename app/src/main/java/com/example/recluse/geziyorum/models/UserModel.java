package com.example.recluse.geziyorum.models;

import java.sql.Date;

/**
 * Created by Recluse on 1.05.2018.
 */

public class UserModel {
    private int id;
    private String username;
    private String email;
    private String password;
    private String remember_token;
    private String name_surname;
    private String photo_path;
    private String location;
    private String bio;
    private Date birthdate;
    private Date created_at;

    public UserModel(int id, String username, String email, String password, String remember_token, String name_surname, String photo_path, String location, String bio, Date birthdate, Date created_at) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.remember_token = remember_token;
        this.name_surname = name_surname;
        this.photo_path = photo_path;
        this.location = location;
        this.bio = bio;
        this.birthdate = birthdate;
        this.created_at = created_at;
    }


    //region Getters&Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) {
        this.name_surname = name_surname;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }


    //endregion
}
