package com.example.q.trialtwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;


public class ActualRanking extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_actual);

        //String l_name = getIntent().getStringExtra("name");
        String l_score = getIntent().getStringExtra("score");
        String l_time = getIntent().getStringExtra("time");
        //Log.d("name tabbb", l_name);
        Log.d("score tabbb", l_score);
        Log.d("time tabbb", l_time);

//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.info);
//
//        TextView value = new TextView(this);
//        value.setText(time + " " + name + " " + score);

//        linearLayout.addView(value);
        result = findViewById(R.id.result);
        result.setText(l_score + " " + l_time);


    }






}