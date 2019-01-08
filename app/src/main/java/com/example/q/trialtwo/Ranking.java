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


public class Ranking extends AppCompatActivity {

    TextView scoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);

        //Intent intent = getIntent();
        String totalScore = getIntent().getStringExtra("score");
        String userName = getIntent().getStringExtra("name");
        Log.d("totalscore received", totalScore);

        scoreboard = (TextView) findViewById(R.id.scoreboard);
        scoreboard.setText("Your score is: " + totalScore);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());



        Intent intent = new Intent(Ranking.this, ActualRanking.class);
        intent.putExtra("name", userName);
        intent.putExtra("score", totalScore);
        intent.putExtra("time", currentDateTimeString);



    }






}