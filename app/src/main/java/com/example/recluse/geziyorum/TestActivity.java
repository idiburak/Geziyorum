package com.example.recluse.geziyorum;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
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

    private FloatingActionButton btnStartService,btnStopService,btnPhoto,btnVideo,btnNote,btnRecord;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btnStartService = findViewById(R.id.fabStartAndPauseTest);
        btnStopService = findViewById(R.id.fabStopTest);
        mImageView = findViewById(R.id.imageViewTest);
        btnPhoto = findViewById(R.id.fabAddPhotoTest);
        btnVideo = findViewById(R.id.fabAddVideoTest);

        if(!runtime_permissions()){
            btnStartService.setOnClickListener(view -> {
                Log.d("button","start");
                Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
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
                startActivityForResult(i, PHOTO_CAPTURE);
            }

        });

        btnVideo.setOnClickListener(view -> {
            Log.d("button","video");

        });


        LocalDbHelper myDb = new LocalDbHelper(this);

        //myDb.insertUser(new UserModel(2,"","","","","","","","",new Date(Calendar.getInstance().getTime().getTime()),new Date(Calendar.getInstance().getTime().getTime())));

        //ArrayList<UserModel> users = myDb.GetUsers();

    }
//TODO burdan kamera ayarları yapılacak
    private File createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(Calendar.getInstance().getTime().getTime()));
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
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
                    Log.d("gps", extras.get("longitude") + " - " + extras.get("latitude"));
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
