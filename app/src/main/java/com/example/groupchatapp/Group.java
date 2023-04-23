package com.example.groupchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        String message = getIntent().getStringExtra("message");
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setTitle("Group NAme");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);

        actionBar.setElevation(20);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("UTA Group Chat", 0); // 0 - for private mode
        String JWT = pref.getString("JWT", null); // getting String
        if (JWT == null){
            switchtoLoginActivity();
            finish();
        }


        JSONObject memberlist= null;

        try {
            memberlist = new JSONObject(
                    "{" +
                            "\"members\" : " +
                            "[" +
                            "{" +
                            "\"name\" : \"Kaushal\"," +
                            "\"isAdmin\" : true,"+
                            "\"isSelf\" : true"+
                            "}," +
                            "{" +
                            "\"name\" : \"Potato\"," +
                            "\"isAdmin\" : false,"+
                            "\"isSelf\" : false"+
                            "}," +
                            "{" +
                            "\"name\" : \"Tomato\"," +
                            "\"isAdmin\" : false,"+
                            "isSelf : false"+
                            "}" +
                            "]" +
                            "}"
            );

            RecyclerView grouprview = (RecyclerView) findViewById(R.id.grouprecycler);


            JSONArray grouparray = memberlist.getJSONArray("members");

            GroupAdapter adapter = new GroupAdapter(grouparray);
            grouprview.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            grouprview.setLayoutManager(llm);
//            ItemClickSupport.addTo(grouprview).setOnItemClickListener(
//                    new ItemClickSupport.OnItemClickListener() {
//                        @Override
//                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                            // do stuff
//                            Log.d("BUTTONS", "User tapped the "+position+" button on the Chat Activity");
////                            deleteChat(position);
////                            finish();
//                        }
//                    }
//            );


        } catch (JSONException e) {
            Log.d("exception",e.toString());
        }
        }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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