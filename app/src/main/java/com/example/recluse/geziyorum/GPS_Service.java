package com.example.recluse.geziyorum;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Recluse on 20.04.2018.
 */

public class GPS_Service extends Service {
    private LocationListener listener;
    private LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(){
        this.listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location",location.toString());
                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());
                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,listener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(locationManager != null){

            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }
}
