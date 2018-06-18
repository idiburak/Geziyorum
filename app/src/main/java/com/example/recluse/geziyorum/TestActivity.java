package com.example.recluse.geziyorum;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recluse.geziyorum.db.Constants;
import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
import com.example.recluse.geziyorum.models.LocationMediaModel;
import com.example.recluse.geziyorum.models.LocationModel;
import com.example.recluse.geziyorum.models.MediaModel;
import com.example.recluse.geziyorum.models.NoteModel;
import com.example.recluse.geziyorum.models.TripModel;
import com.example.recluse.geziyorum.models.UserModel;
import com.example.recluse.geziyorum.utils.AppHelper;
import com.example.recluse.geziyorum.utils.ExifUtil;
import com.example.recluse.geziyorum.utils.VolleyMultipartRequest;
import com.example.recluse.geziyorum.utils.VolleySingleton;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver;

    private final int PHOTO_CAPTURE = 0;
    private final int VIDEO_CAPTURE = 1;

    private String currentPhotoPath;

    private LocalDbHelper localDbHelper;

    private FloatingActionButton btnStartService, btnStopService, btnPhoto, btnVideo, btnNote, btnRecord;
    private ImageView mImageView;

    private int USER_ID = 0;
    private int TRIP_ID = 1;
    private int LOCATION_ID = 2;

    private String[] permissions = new String[]{

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private MediaModel photo, video;
    private ArrayList<LocationMediaModel> photos,videos;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        localDbHelper = new LocalDbHelper(this);
/*
        btnStartService = findViewById(R.id.fabStartAndPauseTest);
        btnStopService = findViewById(R.id.fabStopTest);
        mImageView = findViewById(R.id.imageViewTest);
        btnPhoto = findViewById(R.id.fabAddPhotoTest);
        btnVideo = findViewById(R.id.fabAddVideoTest);
        btnNote = findViewById(R.id.fabAddNoteTest);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if(!checkPermissions()){
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
                photo = new MediaModel(LOCATION_ID,TRIP_ID,USER_ID,getPictureName(), Constants.MEDIA_TYPE_PHOTO);
                File pictureDirectory = CreateDirectory(photo.getPath());
                File imageFile = new File(pictureDirectory,photo.getFile_name());
                Uri pictureUri = Uri.fromFile(imageFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT,pictureUri);
                startActivityForResult(i,PHOTO_CAPTURE);
            }

        });

        btnVideo.setOnClickListener(view -> {
            Intent i = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            if (i.resolveActivity(getPackageManager()) != null) {
                video = new MediaModel(LOCATION_ID,TRIP_ID,USER_ID,getVideoName(),Constants.MEDIA_TYPE_VIDEO);
                File videoDirectory = CreateDirectory(video.getPath());
                File videoFile = new File(videoDirectory,video.getFile_name());
                Uri videoUri = Uri.fromFile(videoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT,videoUri);

                startActivityForResult(i, Constants.VIDEO_CAPTURE);
            }

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

*/
        photos = localDbHelper.GetPhotos(1);
        File imageFile = new File(Constants.STORAGE_PATH + Environment.getExternalStorageDirectory() + "/Geziyorum/" + photos.get(0).getPath(), photos.get(0).getFile_name());
        String imageString = "";
        if (imageFile.exists()) {
            bitmap = ExifUtil.rotateBitmap(imageFile.getAbsolutePath(), BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
            imageString = getStringImage(bitmap);

        }

        videos = localDbHelper.GetVideos(4);
        File videoFile = new File(Constants.STORAGE_PATH + Environment.getExternalStorageDirectory() + "/Geziyorum/" + videos.get(0).getPath(), videos.get(0).getFile_name());
        if(videoFile.exists()){
            System.out.println();
        }else{
            System.out.println("file does not exist");
        }

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, Constants.MEDIA_STORE_URL,
                response -> {
                    String resultResponse = new String(response.data);
                    try {
                        JSONObject result = new JSONObject(resultResponse);
                        System.out.println("");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    error.printStackTrace();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", "1");
                params.put("trip_id", "1");
                params.put("location_id", "1");
                params.put("media_type_id", "3");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                //video/mp4
                //params.put("file", new VolleyMultipartRequest.DataPart(photos.get(0).getFile_name(), AppHelper.getBitmapData(bitmap), "image/jpeg"));
                params.put("file", new VolleyMultipartRequest.DataPart(videos.get(0).getFile_name(), AppHelper.getVideoData(videoFile.getAbsolutePath()), "video/mp4"));

                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }


    private File CreateDirectory(String path) {
        String filePath = Environment.getExternalStorageDirectory() + "/Geziyorum/" + path;
        File mediaDirectory = Environment.getExternalStoragePublicDirectory(filePath);
        if (!mediaDirectory.exists()) {
            if (!mediaDirectory.mkdirs()) {
                System.out.println("cannot create dir");
            }
        }

        return mediaDirectory;
    }
/*
    private String getVideoName(){
        String timeStamp = new SimpleDateFormat("ddMMyyyy_ssmmHH").format(new Date(Calendar.getInstance().getTime().getTime()));
        String fileName = LOCATION_ID + "_" + timeStamp + ".mp4";
        return fileName;
    }

    private String getPictureName(){
        String timeStamp = new SimpleDateFormat("ddMMyyyy_ssmmHH").format(new Date(Calendar.getInstance().getTime().getTime()));
        String imageFileName = LOCATION_ID + "_" + timeStamp + ".jpg";
        return imageFileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PHOTO_CAPTURE && resultCode == RESULT_OK) {
            localDbHelper.insertMedia(photo);
        }
        if (requestCode == Constants.VIDEO_CAPTURE && resultCode == RESULT_OK) {
            localDbHelper.insertMedia(video);
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
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
                        LOCATION_ID = (int)localDbHelper.insertLocation(new LocationModel(1,(Double) extras.get("longitude"),(Double) extras.get("latitude")));
                        System.out.println(LOCATION_ID);
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
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /// permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }

        // other 'case' lines to check for other
        // permissions this app might request
            return;
        }
    }

    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }

*/
}
