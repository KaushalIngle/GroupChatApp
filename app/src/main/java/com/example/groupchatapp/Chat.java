package com.example.groupchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
        actionBar.setLogo(R.drawable.logoactionbar);
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

        } catch (JSONException e) {
            Log.d("exception",e.toString());
        }




    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }
}