package com.example.groupchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        String message = getIntent().getStringExtra("message");
        Button LoginButton = findViewById(R.id.login_button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Login Button on Register Activity");
                switchtoLoginActivity();
                finish();
            }
        });
        Button RegisterButton = findViewById(R.id.register_button);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the RegisterButton on Register Activity");
            }
        });
    }



    private void switchtoLoginActivity() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}