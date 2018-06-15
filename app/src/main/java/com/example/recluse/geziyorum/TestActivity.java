package com.example.recluse.geziyorum;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.NoteModel;
import com.example.recluse.geziyorum.models.TripModel;
import com.example.recluse.geziyorum.models.UserModel;
import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TestActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;
    private final int PHOTO_CAPTURE = 0;
    private final int VIDEO_CAPTURE = 1;

    private String currentPhotoPath;

    private LocalDbHelper localDbHelper;

    private FloatingActionButton btnStartService,btnStopService,btnPhoto,btnVideo,btnNote,btnRecord;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        localDbHelper = new LocalDbHelper(this);

        btnStartService = findViewById(R.id.fabStartAndPauseTest);
        btnStopService = findViewById(R.id.fabStopTest);
        mImageView = findViewById(R.id.imageViewTest);
        btnPhoto = findViewById(R.id.fabAddPhotoTest);
        btnVideo = findViewById(R.id.fabAddVideoTest);
        btnNote = findViewById(R.id.fabAddNoteTest);

        if(!runtime_permissions()){
            btnStartService.setOnClickListener(view -> {
                EditText editTripName = new EditText(this);
                editTripName.setHint("Enter Trip Name");

                final AlertDialog alertDialogName = new AlertDialog.Builder(this)
                        .setTitle("Add Name to Trip")
                        .setView(editTripName)
                        .setPositiveButton("Next", (dialog, whichButton) -> {

                            EditText editTripAbout = new EditText(this);
                            editTripAbout.setHint("Enter Trip About");

                            new AlertDialog.Builder(this)
                                    .setTitle("Add About to Trip")
                                    .setView(editTripAbout)
                                    .setPositiveButton("Start Trip", (dialog2, whichButton2) -> {
                                        TripModel tripModel = new TripModel(1,editTripName.getText().toString(),editTripAbout.getText().toString());
                                        long insertedId = localDbHelper.insertTrip(tripModel);

                                        Log.d("button","start");
                                        Intent i = new Intent(getApplicationContext(),GPS_Service.class);
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
                Log.d("button","stop");
                Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                stopService(i);
            });

        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_CAPTURE && resultCode == RESULT_OK) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent i) {
                    Bundle extras = i.getExtras();
                    Location newLocation = new Location("New Location");
                    newLocation.setLongitude((Double) extras.get("longitude"));
                    newLocation.setLatitude((Double) extras.get("latitude"));

                    LocationModel lastLocationModel = localDbHelper.getLastLocation(1);
                    Location lastLocation = new Location("Last Location");
                    lastLocation.setLongitude(lastLocationModel.getLongitude());
                    lastLocation.setLatitude(lastLocationModel.getLatitude());
                    float distance = lastLocation.distanceTo(newLocation);
                    if( distance > 5){
                        long locid = localDbHelper.insertLocation(new LocationModel(1,(Double) extras.get("longitude"),(Double) extras.get("latitude")));
                        System.out.println(locid);
                    }
                }
            };
            registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("destroy", "destroyed");

        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
            //
        }
    }



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


}
