package com.example.q.trialtwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class game extends AppCompatActivity {

    TextView name, email, birthday, friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        Intent intent = getIntent();
        String s_name = intent.getStringExtra("name");
        String s_email = intent.getStringExtra("email");
        String s_birthday = intent.getStringExtra("birthday");
        String s_friends = intent.getStringExtra("friends");

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        birthday = (TextView) findViewById(R.id.birthday);
        friends = (TextView) findViewById(R.id.friends);

        name.setText("Name: " + s_name);
        email.setText("Email: " + s_email);
        birthday.setText("Birthday: " + s_birthday);
        friends.setText("Number of Friends: " + s_friends);



    }


}