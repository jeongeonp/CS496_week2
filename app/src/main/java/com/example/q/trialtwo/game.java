package com.example.q.trialtwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class game extends AppCompatActivity {

    TextView name, email, birthday, friends;
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        profile = (ImageView) findViewById(R.id.profile);

        Intent intent = getIntent();
        String s_name = intent.getStringExtra("name");
        String s_email = intent.getStringExtra("email");
        String s_birthday = intent.getStringExtra("birthday");
        String s_friends = intent.getStringExtra("friends");
        String s_id = intent.getStringExtra("id");

        URL profile_picture = null;
        try {
            profile_picture = new URL("https://graph.facebook.com/" + s_id +"/picture?width=250&height=250");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Picasso.with(this).load(profile_picture.toString()).into(profile);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        //birthday = (TextView) findViewById(R.id.birthday);
        //friends = (TextView) findViewById(R.id.friends);

        name.setText("Name: " + s_name);
        email.setText("Email: " + s_email);
        //birthday.setText("Birthday: " + s_birthday);
        //friends.setText("Number of Friends: " + s_friends);



    }


}