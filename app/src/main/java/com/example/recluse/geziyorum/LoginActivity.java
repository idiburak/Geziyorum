package com.example.recluse.geziyorum;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recluse.geziyorum.db.Constants;
import com.example.recluse.geziyorum.db.bycrypt.BCrypt;
import com.example.recluse.geziyorum.db.helper.LocalDbHelper;
import com.example.recluse.geziyorum.db.helper.RemoteDbHelper;
import com.example.recluse.geziyorum.models.UserModel;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText inputUserName, inputPassword;
    private Button buttonLogin,buttonRegister,buttonMoreInfo;
    private TextView buttonForgetPass;
    private ProgressBar progressBar;
    private LocalDbHelper localDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        this.localDbHelper = new LocalDbHelper(this);

        this.inputUserName = findViewById(R.id.inputUserName);
        this.inputPassword = findViewById(R.id.inputPassword);
        this.buttonLogin = findViewById(R.id.buttonLogin);
        this.buttonRegister = findViewById(R.id.loginRegister);
        this.buttonMoreInfo = findViewById(R.id.loginMoreInfo);
        this.buttonForgetPass = findViewById(R.id.loginForgotPass);
        this.progressBar = findViewById(R.id.loginProcessBar);
        progressBar.setVisibility(View.INVISIBLE);

        buttonLogin.setOnClickListener(view -> {
            CheckLogin("cemaltaskiran@gmail.com","secret");
        });
        buttonMoreInfo.setOnClickListener(view -> {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse(Constants.MORE_INFO_URL));
            startActivity(viewIntent);
        });
        buttonRegister.setOnClickListener(view -> {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse(Constants.REGISTER_URL));
            startActivity(viewIntent);
        });
        buttonForgetPass.setOnClickListener(view -> {
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse(Constants.PASSWORD_RESET_URL));
            startActivity(viewIntent);
        });

    }

    private void CheckLogin(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject user = new JSONObject(response);

                        String password1 = inputPassword.getText().toString();
                        String remember_token = "remember2";

                        Date birthdate = new Date(Constants.RemoteBirthDateFormat.parse(user.getString("birthdate")).getTime()) ;
                        Date created_at = new Date(Constants.RemoteDateFormat.parse(user.getString("created_at")).getTime()) ;

                        UserModel userModel = new UserModel(user.getInt("id"),user.getString("username"),user.getString("email")
                                , password1,remember_token,user.getString("name_surname"),user.getString("photo_path")
                                ,user.getString("location"),user.getString("bio"),birthdate,created_at);
                        long res = localDbHelper.insertUser(userModel);
                        if(res < 0){
                            res = localDbHelper.updateUser(userModel);
                        }
                        if(res < 0){
                            Toast.makeText(this,"Something went wrong!",Toast.LENGTH_LONG);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Check your email or password!", Toast.LENGTH_LONG).show();
                    error.printStackTrace();

                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);

    }



}
