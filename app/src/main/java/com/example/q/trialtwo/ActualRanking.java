package com.example.q.trialtwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class ActualRanking extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);

        String name = getIntent().getStringExtra("name");
        String score = getIntent().getStringExtra("score");
        String time = getIntent().getStringExtra("time");
        Log.d("Actualranking tab", name + score + time);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.info);

        TextView value = new TextView(this);
        value.setText(time + " " + name + " " + score);

        linearLayout.addView(value);


    }






}