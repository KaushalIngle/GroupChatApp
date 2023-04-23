package com.example.groupchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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

        SharedPreferences pref = getApplicationContext().getSharedPreferences("UTA Group Chat", 0); // 0 - for private mode
        String JWT = pref.getString("JWT", null); // getting String
        if (JWT == null){
            switchtoLoginActivity();
            finish();
        }




        List<String> list = Arrays.asList("Group 1", "Poop Flingers", "Potato Farmers", "Casual Vegans", "Dallas Mavericks", "Slumdog Millionaires", "Plant Parents", "D", "Tomato Receipes", "Totally unsafe and Unwarranted");







        RecyclerView homerview = (RecyclerView) findViewById(R.id.homerecycler);
        HomeAdapter adapter = new HomeAdapter(list);
        homerview.setAdapter(adapter);
        homerview.setLayoutManager(new LinearLayoutManager(this));
        ItemClickSupport.addTo(homerview).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // do stuff
                        Log.d("BUTTONS", "User tapped the "+position+" button on the Home Activity");
                        switchtoChatActivity();
//                        finish();
                    }
                }
        );
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                switchtoLoginActivity();
                return true;
            case R.id.account:
                switchtoUserActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void switchtoChatActivity() {
        Intent switchActivityIntent = new Intent(this, Chat.class);
        startActivity(switchActivityIntent);
    }
    private void switchtoUserActivity() {
        Intent switchActivityIntent = new Intent(this, User.class);
        startActivity(switchActivityIntent);
    }
    private void switchtoLoginActivity() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("UTA Group Chat", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();


        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
        finish();
    }
}