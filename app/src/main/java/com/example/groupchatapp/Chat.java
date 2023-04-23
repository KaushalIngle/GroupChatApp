package com.example.groupchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String message = getIntent().getStringExtra("message");
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setTitle("Group NAme");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setElevation(20);


        JSONObject chatlist= null;

        try {
            chatlist = new JSONObject(
                    "{" +
                        "\"chats\" : " +
                            "[" +
                                "{" +
                                    "\"name\" : \"Kaushal\"," +
                                    "\"message\" : \"Hi\","+
                                    "\"isSelf\" : true"+
                                "}," +
                                "{" +
                                    "\"name\" : \"Potato\"," +
                                    "\"message\" : \"Hello\","+
                                    "\"isSelf\" : false"+
                                "}," +
                                "{" +
                                "\"name\" : \"Kaushal\"," +
                                "\"message\" : \"Hi\","+
                                "\"isSelf\" : true"+
                                "}," +
                                "{" +
                                    "\"name\" : \"Tomato\"," +
                                    "\"message\" : \"big message big message big message big message big message\","+
                                    "isSelf : false"+
                                "}" +
                            "]" +
                    "}"
            );

            RecyclerView chatrview = (RecyclerView) findViewById(R.id.chatrecycler);


            JSONArray chatarray = chatlist.getJSONArray("chats");

            ChatAdapter adapter = new ChatAdapter(chatarray);
            chatrview.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setReverseLayout(true);
            chatrview.setLayoutManager(llm);
            ItemClickSupport.addTo(chatrview).setOnItemClickListener(
                    new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            // do stuff
                            Log.d("BUTTONS", "User tapped the "+position+" button on the Chat Activity");
                            deleteChat(position);
//                            finish();
                        }
                    }
            );
        } catch (JSONException e) {
            Log.d("exception",e.toString());
        }




    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.manage:
                switchtoGroupActivity();
                return true;
            case R.id.media:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void switchtoGroupActivity() {
        Intent switchActivityIntent = new Intent(this, Group.class);
        startActivity(switchActivityIntent);
//        finish();
    }
    private void deleteChat(int position){

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}