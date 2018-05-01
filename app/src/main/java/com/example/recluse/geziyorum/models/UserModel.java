package com.example.recluse.geziyorum.models;

import java.sql.Date;

/**
 * Created by Recluse on 1.05.2018.
 */

public class UserModel {
    private int user_id;
    private String username;
    private String email;
    private String password;
    private String name_surname;
    private String location;
    private String bio;
    private String created_at;
    private String photo_path;
    private String remember_token;
    private Date birthdate;

    public UserModel(int user_id, String username, String email, String password, String name_surname, String location, String bio, String created_at, String photo_path, Date birthdate) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name_surname = name_surname;
        this.location = location;
        this.bio = bio;
        this.created_at = created_at;
        this.photo_path = photo_path;
        this.birthdate = birthdate;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getName_surname() {
        return name_surname;
    }

    public void setName_surname(String name_surname) {
        this.name_surname = name_surname;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
