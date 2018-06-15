package com.example.recluse.geziyorum;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.NoteModel;
import com.example.recluse.geziyorum.models.TripModel;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TravelActivity extends FragmentActivity implements OnMapReadyCallback {

    private final int PHOTO_CAPTURE = 0;
    private final int VIDEO_CAPTURE = 1;

    private static int USER_ID;
    private static long TRIP_ID;
    private static long LOCATION_ID;

    private GoogleMap mMap;

    private BroadcastReceiver broadcastReceiver;

    private LocalDbHelper localDbHelper;

    private FloatingActionMenu btnAddMenu;

    private FloatingActionButton btnStartAndPauseService,btnStopService,btnPhoto,btnVideo,btnNote,btnRecord;

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

        ArrayList<LocationModel> locations = localDbHelper.GetLocations(3);

        System.out.println();


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
                        LOCATION_ID = localDbHelper.insertLocation(new LocationModel((int)TRIP_ID,(Double) extras.get("longitude"),(Double) extras.get("latitude")));
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
        if(!runtime_permissions()){
            btnStartAndPauseService.setOnClickListener(view -> {

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

                                        Log.d("button","start");
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
            });

            btnStopService.setOnClickListener(view -> {
                Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                btnAddMenu.setEnabled(false);
                btnAddMenu.setVisibility(View.INVISIBLE);
                btnStopService.setEnabled(false);
                stopService(i);
                localDbHelper.FinishTrip((int)TRIP_ID);
            });

        }
    }

    private void EnableAddButtons(){
        btnPhoto.setOnClickListener(view -> {
            Log.d("button","photo");
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getPackageManager()) != null) {
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureName = getPictureName();
                File imageFile = new File(pictureDirectory,pictureName);
                Uri pictureUri = Uri.fromFile(imageFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                startActivityForResult(i,PHOTO_CAPTURE);
            }

        });

        btnVideo.setOnClickListener(view -> {
            LocationModel locationModel = new LocationModel(1,2,3);
            localDbHelper.insertLocation(locationModel);

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
                        NoteModel tmp = new NoteModel(localDbHelper.getLastLocation(1).getId(),noteTxt);
                        localDbHelper.insertNote(tmp);

                    })
                    .setNegativeButton("Cancel", (dialog, whichButton) -> {

                    })
                    .show();
        });
    }

    private String getPictureName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(Calendar.getInstance().getTime().getTime()));
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        return imageFileName;
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

    }

    //region Request Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"GPS Permission Result is OK!",Toast.LENGTH_SHORT);
            }else {
                runtime_permissions();
            }
        }
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
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
    //endregion
}
