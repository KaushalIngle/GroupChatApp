package com.example.groupchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button LoginButton = findViewById(R.id.login_button);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Login Button on the Login Activity");
                switchtoHomeActivity();
                finish();
            }
        });
        Button RegisterButton = findViewById(R.id.register_button);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Register button on the Login Activity");
                switchtoRegisterActivity();
                finish();
            }
        });
        Button ForgetButton = findViewById(R.id.forgot_button);
        ForgetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Forgot Button on the Login Activity");
            }
        });
    }

    private void switchtoRegisterActivity() {
        Intent switchActivityIntent = new Intent(this, Register.class);
        switchActivityIntent.putExtra("message", "From: " + MainActivity.class.getSimpleName());
        startActivity(switchActivityIntent);
    }
    private void switchtoHomeActivity() {
        Intent switchActivityIntent = new Intent(this, Home.class);
        switchActivityIntent.putExtra("message", "From: " + MainActivity.class.getSimpleName());
        startActivity(switchActivityIntent);
    }

}