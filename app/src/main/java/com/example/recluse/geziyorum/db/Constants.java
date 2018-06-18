package com.example.recluse.geziyorum.db;

import android.Manifest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Constants {
    public static final String[] PERMISSIONS = new String[]{

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final int PHOTO_CAPTURE = 0;
    public static final int VIDEO_CAPTURE = 1;

    public static final String MEDIA_TYPE_PHOTO = "Photos";
    public static final String MEDIA_TYPE_VIDEO = "Videos";
    public static final String MEDIA_TYPE_AUDIO = "Audios";

    //if emulator "storage/self/primary" else ""
    public static final String STORAGE_PATH = "storage/self/primary"; // for emulator
    //public static final String STORAGE_PATH = ""; // for phone


    public static final String SETTINGS_ACTIVE_USER = "active_user";
    public static final String SETTINGS_GPS_RATE = "gps_rate";

    public static final SimpleDateFormat RemoteDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    public static final SimpleDateFormat RemoteBirthDateFormat = new SimpleDateFormat("yyyy-dd-MM", Locale.US);

    public static final String LOGIN_URL = "http://geziyorum.cemaltaskiran.com/api/login";
    public static final String MEDIA_STORE_URL = "http://geziyorum.cemaltaskiran.com/api/media/store";
    public static final String MORE_INFO_URL = "http://geziyorum.cemaltaskiran.com/";
    public static final String REGISTER_URL = "http://geziyorum.cemaltaskiran.com/register";
    public static final String PASSWORD_RESET_URL = "http://geziyorum.cemaltaskiran.com/password/reset";

}
