package com.example.groupchatapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Chat extends AppCompatActivity {
    String JWT;
    JSONObject chatlist;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        groupName = getIntent().getStringExtra("message");
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setTitle(groupName);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setElevation(20);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("UTA Group Chat", 0); // 0 - for private mode
        JWT = pref.getString("JWT", null); // getting String
        if (JWT == null){
            switchtoLoginActivity();
            finish();
        }

        chatlist= new JSONObject();
        getchat(groupName);

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


        } catch (JSONException e) {
            Log.d("exception",e.toString());
        }
        updateView(chatlist);
        Button sendButton = findViewById(R.id.sendbutton);

        EditText messageHolder = findViewById(R.id.edit_gchat_message);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage(String.valueOf(messageHolder.getText()));
            }
        });

    }
    private void sendMessage(String message){

        JSONObject reqObject = new JSONObject();
                        try {
                            reqObject.put("message", message);
                            reqObject.put("groupName", groupName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

        AndroidNetworking.post("http://10.0.2.2:3000/sendMessage")
                                .addJSONObjectBody(reqObject)
                                .setTag("test")
                                .addHeaders("Authorization", JWT)
                                .setPriority(Priority.MEDIUM)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // do anything with response
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("UTA Group Chat", 0);
                                        try {
                                            if (response.getBoolean("success")) {
                                                Snackbar snack = Snackbar.make(findViewById(R.id.main),"Group Created",Snackbar.LENGTH_SHORT);
                                                snack.show();
                                                finish();
                                                startActivity(getIntent());
                                            }
                                        } catch (JSONException e) {

                                        }
                                    }

                                    @Override
                                    public void onError(ANError error) {
                                        // handle error
                                        JSONObject response = null;
                                        String err = null;
                                        try {
                                            response = new JSONObject(error.getErrorBody());
                                            err = response.getString("status");

                                        } catch (JSONException e) {
                                            err = "Error Please Try again";
                                        }
                                        Log.d("fail", "login fail");
                                        Snackbar snack = Snackbar.make(findViewById(R.id.main),err,Snackbar.LENGTH_SHORT);
                                        snack.show();
                                    }
                                });
    }
    private void updateView(JSONObject chatlist) {
            RecyclerView chatrview = (RecyclerView) findViewById(R.id.chatrecycler);
            JSONArray chatarray = new JSONArray();
            try {
                chatarray = chatlist.getJSONArray("chats");
            }catch (Exception e){

            }
            ChatAdapter adapter = new ChatAdapter(chatarray);
            chatrview.setAdapter(adapter);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setReverseLayout(true);
            chatrview.setLayoutManager(llm);
            ItemClickSupport.addTo(chatrview).setOnItemLongClickListener(
                    new ItemClickSupport.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                            // do stuff
                            TextView message = v.findViewById(R.id.selfmessage);
                            String messageText = message.getText().toString();
                            if (messageText.equals("")){
                                return false;
                            }
                            Log.d("BUTTONS", "User long tapped the "+position+" button on the Chat Activity");
                            deleteChat(messageText);
//                            finish();
                            return true;
                        }
                    }
            );
    }
    private void getchat(String groupName) {
        Log.d("here","here");
        AndroidNetworking.get("http://10.0.2.2:3000/chat")
                 .addHeaders("Authorization", JWT)
                 .addQueryParameter("groupName", groupName)
                 .setTag("test")
                 .setPriority(Priority.LOW)
                 .build()
                 .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                      // do anything with response
                        Log.d("res", response.toString());
                      updateView(response);
                    }
                    @Override
                    public void onError(ANError error) {
                      // handle error
                      JSONObject response = null;
                        String err = null;
                        try {
                            response = new JSONObject(error.getErrorBody());
                            err = response.getString("status");

                        } catch (JSONException e) {
                            err = "Error Please Try again";
                        }
                        Log.d("fail", "login fail");
                        Snackbar snack = Snackbar.make(findViewById(R.id.main),err,Snackbar.LENGTH_SHORT);
                        snack.show();
                    }
                });
    
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
                finish();
                return true;
            case R.id.media:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void switchtoGroupActivity() {
        Intent switchActivityIntent = new Intent(this, Group.class);
        switchActivityIntent.putExtra("message", groupName);
        startActivity(switchActivityIntent);
//        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        switchtoHomeActivity();
        return true;
    }

    private void switchtoHomeActivity(){
        Intent switchActivityIntent = new Intent(this, Home.class);
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
    private void deleteChat(String messageText){


        new AlertDialog.Builder(this,R.style.AlertDialogStyle)
            .setTitle("Delete Message")
            .setMessage("")
            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    JSONObject reqObject = new JSONObject();
                    try {
                        reqObject.put("message", messageText);
                        reqObject.put("groupName", groupName);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AndroidNetworking.post("http://10.0.2.2:3000/deleteMessage")
                            .addJSONObjectBody(reqObject)
                            .setTag("test")
                            .addHeaders("Authorization", JWT)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // do anything with response
                                    Log.d("f","f");
                                    try {
                                        if (response.getBoolean("success")) {
                                            Snackbar snack = Snackbar.make(findViewById(R.id.main),"Message Deleted",Snackbar.LENGTH_SHORT);
                                            snack.show();
                                            finish();
                                        }
                                    } catch (JSONException e) {

                                    }
                                }

                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                    JSONObject response = null;
                                    String err = null;
                                    try {
                                        response = new JSONObject(error.getErrorBody());
                                        err = response.getString("status");

                                    } catch (JSONException e) {
                                        err = "Error Please Try again";
                                    }
                                    Log.d("fail", "login fail");
                                    Snackbar snack = Snackbar.make(findViewById(R.id.main),err,Snackbar.LENGTH_SHORT);
                                    snack.show();
                                }
                            });

                    finish();
                    startActivity(getIntent());

                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            })
            .show();


    }

}