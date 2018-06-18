package com.example.recluse.geziyorum;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.MediaModel;
import com.example.recluse.geziyorum.models.NoteModel;
import com.example.recluse.geziyorum.models.TripModel;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.recluse.geziyorum.db.Constants;
import com.google.android.gms.maps.model.PolylineOptions;

public class TravelActivity extends FragmentActivity implements OnMapReadyCallback {



    private static int USER_ID;
    private static long TRIP_ID;
    private static long LOCATION_ID;

    private GoogleMap mMap;

    private BroadcastReceiver broadcastReceiver;

    private LocalDbHelper localDbHelper;

    private FloatingActionMenu btnAddMenu;

    private FloatingActionButton btnStartAndPauseService,btnStopService,btnPhoto,btnVideo,btnNote,btnRecord;

    private MediaModel photo,video;

    private LocationModel oldLocation, newLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        InitializeVariables();

        EnableGpsButtons();

        EnableAddButtons();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent i) {
                    Bundle extras = i.getExtras();
                    if( GetDistanceBetweenLastAndNew(extras) > 5){
                        oldLocation = localDbHelper.getLastLocation((int)TRIP_ID);
                        LOCATION_ID = localDbHelper.insertLocation(new LocationModel((int)TRIP_ID,(Double) extras.get("longitude"),(Double) extras.get("latitude")));
                        newLocation = localDbHelper.getLastLocation((int)TRIP_ID);
                        PrintLinesToMap();
                    }
                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


        // Add a marker in Sydney and move the camera
        //LatLng latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void EnableGpsButtons() {
        if(checkPermissions()){
            btnStartAndPauseService.setOnClickListener(view -> {
/*
                EditText editTripName = new EditText(this);
                editTripName.setHint("Name...");

                new AlertDialog.Builder(this)
                        .setTitle("ENTER A NAME FOR TRIP")
                        .setView(editTripName)
                        .setPositiveButton("Next", (dialog, whichButton) -> {

                            EditText editTripAbout = new EditText(this);
                            editTripAbout.setHint("About...");

                            new AlertDialog.Builder(this)
                                    .setTitle("ENTER AN ABOUT FOR TRIP")
                                    .setView(editTripAbout)
                                    .setPositiveButton("Start Trip", (dialog2, whichButton2) -> {
                                        TripModel tripModel = new TripModel(USER_ID,editTripName.getText().toString(),editTripAbout.getText().toString());
                                        TRIP_ID = localDbHelper.insertTrip(tripModel);

                                        Intent i = new Intent(getApplicationContext(),GPS_Service.class);

                                        btnAddMenu.setEnabled(true);
                                        btnAddMenu.setVisibility(View.VISIBLE);

                                        btnStopService.setEnabled(true);

                                        startService(i);

                                    })
                                    .setNegativeButton("Cancel", (dialog2, whichButton2) -> {
                                        dialog2.dismiss();
                                    })
                                    .show();


                        })
                        .setNegativeButton("Cancel", (dialog, whichButton) -> {
                            dialog.cancel();
                        })
                        .show();

                        */

                TripModel tripModel = new TripModel(USER_ID,"name","about");
                TRIP_ID = localDbHelper.insertTrip(tripModel);

                Intent i = new Intent(getApplicationContext(),GPS_Service.class);

                btnAddMenu.setEnabled(true);
                btnAddMenu.setVisibility(View.VISIBLE);

                btnStopService.setEnabled(true);
                btnStartAndPauseService.setEnabled(false);
                startService(i);
            });

            btnStopService.setOnClickListener(view -> {
                Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                btnAddMenu.setEnabled(false);
                btnAddMenu.setVisibility(View.INVISIBLE);
                btnStopService.setEnabled(false);
                btnStartAndPauseService.setEnabled(true);
                stopService(i);
                mMap.clear();
                localDbHelper.FinishTrip((int)TRIP_ID);
            });

        }
    }

    private void EnableAddButtons(){
        btnPhoto.setOnClickListener(view -> {
            Log.d("button","photo");
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getPackageManager()) != null) {
                photo = new MediaModel((int)LOCATION_ID,(int)TRIP_ID,USER_ID,getPictureName(), Constants.MEDIA_TYPE_PHOTO);
                File pictureDirectory = CreateDirectory(photo.getPath());
                File imageFile = new File(pictureDirectory,photo.getFile_name());
                Uri pictureUri = Uri.fromFile(imageFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                startActivityForResult(i,Constants.PHOTO_CAPTURE);
            }

        });

        btnVideo.setOnClickListener(view -> {
            Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            if (i.resolveActivity(getPackageManager()) != null) {
                video = new MediaModel((int)LOCATION_ID,(int)TRIP_ID,USER_ID,getVideoName(),Constants.MEDIA_TYPE_VIDEO);
                File videoDirectory = CreateDirectory(video.getPath());
                File videoFile = new File(videoDirectory,video.getFile_name());
                Uri videoUri = Uri.fromFile(videoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT,videoUri);

                startActivityForResult(i, Constants.VIDEO_CAPTURE);
            }

        });

        btnRecord.setOnClickListener(view -> {


        });

        btnNote.setOnClickListener(view -> {
            EditText note = new EditText(this);
            note.setHint("Enter Your Note Here");
            note.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            note.setSingleLine(false);
            note.setLines(6);
            note.setMaxLines(10);
            note.setFilters(new InputFilter[]{new InputFilter.LengthFilter(255)});
            note.setVerticalScrollBarEnabled(true);
            new AlertDialog.Builder(this)
                    .setTitle("Add Note to Location")
                    .setView(note)
                    .setPositiveButton("Add Note", (dialog, whichButton) -> {
                        String noteTxt = note.getText().toString();
                        NoteModel tmp = new NoteModel((int)LOCATION_ID,noteTxt);
                        localDbHelper.insertNote(tmp);
                        Toast.makeText(this, "Note added!", Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButton("Cancel", (dialog, whichButton) -> {

                    })
                    .show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PHOTO_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Photo captured!", Toast.LENGTH_SHORT).show();
            localDbHelper.insertMedia(photo);
        }else if (requestCode == Constants.VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Video captured!", Toast.LENGTH_SHORT).show();
            localDbHelper.insertMedia(video);
        }
    }

    private File CreateDirectory(String path){
        String filePath = Environment.getExternalStorageDirectory() + "/Geziyorum/" + path;
        File mediaDirectory = Environment.getExternalStoragePublicDirectory(filePath);
        if(!mediaDirectory.exists()){
            if(!mediaDirectory.mkdirs()){
                System.out.println("cannot create dir");
            }
        }

        return mediaDirectory;
    }

    private String getPictureName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(Calendar.getInstance().getTime().getTime()));
        String fileName = "JPEG_" + timeStamp + ".jpg";
        return fileName;
    }

    private String getVideoName(){
        String timeStamp = new SimpleDateFormat("ddMMyyyy_ssmmHH").format(new Date(Calendar.getInstance().getTime().getTime()));
        String fileName = LOCATION_ID + "_" + timeStamp + ".mp4";
        return fileName;
    }

    private void InitializeVariables(){
        localDbHelper = new LocalDbHelper(this);

        btnStartAndPauseService = findViewById(R.id.fabStartAndPause);
        btnStopService = findViewById(R.id.fabStop);
        btnPhoto = findViewById(R.id.fabAddPhoto);
        btnVideo = findViewById(R.id.fabAddVideo);
        btnNote = findViewById(R.id.fabAddNote);
        btnRecord = findViewById(R.id.fabAddRecord);
        btnAddMenu = findViewById(R.id.fabAddMenu);

        USER_ID = 1;
        TRIP_ID = 0;
        LOCATION_ID = 0;

        btnAddMenu.setEnabled(false);
        btnAddMenu.setVisibility(View.INVISIBLE);

        btnStopService.setEnabled(false);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    //region Request Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /// permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                checkPermissions();
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }

            // other 'case' lines to check for other
            // permissions this app might request
            return;
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : Constants.PERMISSIONS) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }
    //endregion

    //region Functions
    private float GetDistanceBetweenLastAndNew(Bundle extras){
        Location newLocation = new Location("New Location");
        newLocation.setLongitude((Double) extras.get("longitude"));
        newLocation.setLatitude((Double) extras.get("latitude"));

        LocationModel lastLocationModel = localDbHelper.getLastLocation((int)TRIP_ID);
        Location lastLocation = new Location("Last Location");
        lastLocation.setLongitude(lastLocationModel.getLongitude());
        lastLocation.setLatitude(lastLocationModel.getLatitude());
        return lastLocation.distanceTo(newLocation);

    }

    private void PrintLinesToMap(){
        if(oldLocation.getId() != 0){
            LatLng src = new LatLng(oldLocation.getLatitude(),oldLocation.getLongitude());
            LatLng dst = new LatLng(newLocation.getLatitude(),newLocation.getLongitude());

            mMap.addPolyline(new PolylineOptions().add(src,dst).width(3).color(Color.RED).geodesic(true));
        }

    }


    //endregion
}
