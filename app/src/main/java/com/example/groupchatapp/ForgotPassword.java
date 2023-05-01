package com.example.groupchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        Button UpdateButton = findViewById(R.id.reset_button);
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Update button on the User Activity");
                Snackbar snack = Snackbar.make(findViewById(R.id.main),"Password Changed",Snackbar.LENGTH_SHORT);
                snack.show();new Timer().schedule(
                        new TimerTask(){

                            @Override
                            public void run(){

                                switchtoLoginActivity();
                                finish();
                            }

                        }, 1000);
            }
        });
    }

    private void switchtoLoginActivity() {


        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
        finish();
    }
}