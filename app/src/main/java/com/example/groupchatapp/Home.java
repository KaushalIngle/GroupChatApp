package com.example.groupchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String message = getIntent().getStringExtra("message");
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setTitle(" UTA Group Chat");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setLogo(R.drawable.logoactionbar);
        actionBar.setElevation(20);
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5D2940")));
        List<String> list = Arrays.asList("Group 1", "Poop Flingers", "Potato Farmers", "Casual Vegans", "Dallas Mavericks", "Slumdog Millionaires", "Plant Parents", "D", "Tomato Receipes", "Totally unsafe and Unwarranted");
        RecyclerView homerview = (RecyclerView) findViewById(R.id.homerecycler);
        HomeAdapter adapter = new HomeAdapter(list);
        homerview.setAdapter(adapter);
        homerview.setLayoutManager(new LinearLayoutManager(this));
//        LinearLayout homelinear = findViewById(R.id.homelinear);
//
//        for (int i = 0; i < 10; i++) {
//            homelinear.addView(home_group);
//        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
}