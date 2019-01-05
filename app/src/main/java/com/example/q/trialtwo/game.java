package com.example.q.trialtwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class game extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        try {
            JSONObject object = new JSONObject(getIntent().getStringExtra("extra"));
            //Log.e(TAG, "Example Item:" + object.getString("KEY"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}