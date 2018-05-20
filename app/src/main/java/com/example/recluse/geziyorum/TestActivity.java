package com.example.recluse.geziyorum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
import com.example.recluse.geziyorum.models.UserModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        LocalDbHelper myDb = new LocalDbHelper(this);

        //myDb.insertUser(new UserModel(2,"","","","","","","","",new Date(Calendar.getInstance().getTime().getTime()),new Date(Calendar.getInstance().getTime().getTime())));

        //ArrayList<UserModel> users = myDb.GetUsers();


    }
}
