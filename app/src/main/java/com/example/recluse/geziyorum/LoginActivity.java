package com.example.recluse.geziyorum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText inputUserName, inputPassword;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.inputUserName = findViewById(R.id.inputUserName);
        this.inputPassword = findViewById(R.id.inputPassword);
        this.buttonLogin = findViewById(R.id.buttonLogin);
    }

    public void LoginOnClick(View v){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
