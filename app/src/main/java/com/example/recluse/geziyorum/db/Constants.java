package com.example.recluse.geziyorum.db;

import android.Manifest;

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
    public static final String STORAGE_PATH = "storage/self/primary";

    public static final String SETTINGS_ACTIVE_USER = "active_user";
    public static final String SETTINGS_GPS_RATE = "gps_rate";

}
