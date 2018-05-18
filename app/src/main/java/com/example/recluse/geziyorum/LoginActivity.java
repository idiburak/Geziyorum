package com.example.recluse.geziyorum;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recluse.geziyorum.db.bycrypt.BCrypt;
import com.example.recluse.geziyorum.db.helper.RemoteDbHelper;

import java.sql.*;

public class LoginActivity extends AppCompatActivity {
    private EditText inputUserName, inputPassword;
    private Button buttonLogin;
    private RemoteDbHelper rdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        this.inputUserName = findViewById(R.id.inputUserName);
        this.inputPassword = findViewById(R.id.inputPassword);
        this.buttonLogin = findViewById(R.id.buttonLogin);
    }

    public void LoginOnClick(View v){
        //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        //startActivity(intent);
        //CheckLogin checkLogin = new CheckLogin();
        //checkLogin.execute();



    }

    private class CheckLogin extends AsyncTask <String,String,Boolean>{

        private final String DB_URL = "jdbc:mysql://cemaltaskiran.com:3306/admin_geziyorum";
        private final String USER = "admin_geziyorum";
        private final String PASS = "qlkICJUwxhSTE2PY";
        private Connection conn = null;

        @Override
        protected void onPreExecute(){
            try {
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                //STEP 3: Open a connection
                //System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (Exception e) {
                //Handle errors for Class.forName
                System.out.println("hobaaaaa");
                e.printStackTrace();
            }
        }


        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                PreparedStatement ps = conn.prepareStatement("SELECT password FROM users WHERE username = ? OR email = ?");
                ps.setString(1, inputUserName.getText().toString());
                ps.setString(2, inputUserName.getText().toString());

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    //Retrieve by column name
                    String pass = rs.getString("password");
                    return  BCrypt.checkpw(inputPassword.getText().toString(), pass);

                }
                rs.close();
            } catch (Exception e) {
                //Handle errors for Class.forName
                System.out.println("-----------------------------------------------------------------------");
                e.printStackTrace();
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success){
            Log.d("post", Boolean.toString(success));
        }

    }



}
