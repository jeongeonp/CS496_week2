package com.example.q.trialtwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class game extends AppCompatActivity {

    TextView name, email, birthday, friends, question, score;
    ImageView profile;
    Button yes, no;
    int totalScore, rn;
    boolean found;
    String quest, curAns, s_name;
    Map<String, String> questions;


    TextView timer2;
    Timer timer;
    Button start;
    int count = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        profile = (ImageView) findViewById(R.id.profile);



        Intent intent = getIntent();
        s_name = intent.getStringExtra("name");
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

        question = (TextView) findViewById(R.id.question);
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);

        //final Map<String, Boolean> questions = new HashMap<String, Boolean>();
        questions = new HashMap<>();

        final String[] rightQuestions = {"8 + 9 = 17", "77 × 3 = 231", "306 ÷ 18 = 17", "17 × 4 = 68", "35 – 17 = 18", "11 + 22 = 33", "13 + 49 = 62", "60 ÷ 5 = 12", "19 – 31 = -13",
                "11 × 13 = 143", "64 ÷ 4 = 16", "14 + 17 = 31", "105 – 13 = 92", "6 × 13 = 78", "17 + 2 = 19", "110 ÷ 11 = 10", "13 – 9 = 4", "8 × 6 = 48", "96 – 34 = 62", "28 ÷ 4 = 7"};

        final String[] wrongQuestions = {"77 – 44 = 32", "101 – 33 = 78", "3 – 87 = -94", "35 + 8 = 44", "19 + 14 = 32", "97 + 13 = 120", "16 × 5 = 70", "9 × 13 = 107", "27 × 5 = 120",
                "116 ÷ 7 = 18", "71 ÷ 9 = 8", "56 ÷ 4 = 13"};

        final String[] allQuestions = {"8 + 9 = 17", "77 × 3 = 231", "306 ÷ 18 = 17", "17 × 4 = 68", "35 – 17 = 18", "11 + 22 = 33", "13 + 49 = 62", "60 ÷ 5 = 12", "19 – 31 = -13",
                "11 × 13 = 143", "64 ÷ 4 = 16", "14 + 17 = 31", "105 – 13 = 92", "6 × 13 = 78", "17 + 2 = 19", "110 ÷ 11 = 10", "13 – 9 = 4", "8 × 6 = 48", "96 – 34 = 62", "28 ÷ 4 = 7",
                "77 – 44 = 32", "101 – 33 = 78", "3 – 87 = -94", "35 + 8 = 44", "19 + 14 = 32", "97 + 13 = 120", "16 × 5 = 70", "9 × 13 = 107", "27 × 5 = 120", "116 ÷ 7 = 18",
                "71 ÷ 9 = 8", "56 ÷ 4 = 13"};

        for (int k=0; k<rightQuestions.length; k++) {
            questions.put(rightQuestions[k], "true");
            Log.d("what is wrong? question", questions.get(rightQuestions[k]));
        }
        for (int l=0; l<wrongQuestions.length; l++) {
            questions.put(wrongQuestions[l], "false");
        }

        Log.d("hashmap question", questions.toString());

        totalScore =0;
        score = (TextView) findViewById(R.id.score);

        curAns = "";

        score.setText(Integer.toString(totalScore));

        rn = 0;
        question.setText(allQuestions[rn]);
        curAns = questions.get(allQuestions[rn]);
        Log.d("actual question", allQuestions[rn]);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rn = (int) (Math.random()*allQuestions.length);
                //Log.d("checking question", questions.get(quest));
                if (curAns.equals("true")) {
                    totalScore +=30;
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }
                else if (curAns.equals("false")) {
                    totalScore -=30;
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                }

                score.setText(Integer.toString(totalScore));

                rn = (int) (Math.random()*allQuestions.length);
                question.setText(allQuestions[rn]);
                curAns = questions.get(allQuestions[rn]);
                Log.d("actual question", allQuestions[rn]);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rn = (int) (Math.random()*allQuestions.length);
                if (curAns.equals("false")) {
                    totalScore +=30;
                    Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }
                else if (curAns.equals("true")) {
                    totalScore -=30;
                    Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                }
                found = false;
                String sTotalScore = Integer.toString(totalScore);
                score.setText(sTotalScore);
                rn = (int) (Math.random()*allQuestions.length);
                question.setText(allQuestions[rn]);
                quest = allQuestions[rn];
                curAns = questions.get(allQuestions[rn]);
                Log.d("actual question", allQuestions[rn]);
            }
        });



        //타이머
        timer = new Timer();
        start = findViewById(R.id.start);

        timer2 = (TextView) findViewById(R.id.timer);
        final Handler handler = new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t=new Thread(){
                    @Override
                    public void run(){
                        while(!isInterrupted()){
                            try {
                                Thread.sleep(1000);  //1000ms = 1 sec
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        count--;
                                        timer2.setText(String.valueOf(count) + " secs remaining");
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                t.start();


                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent2 = new Intent(game.this, Ranking.class);
                        intent2.putExtra("score", Integer.toString(totalScore));
                        intent2.putExtra("name", s_name);
                        Log.d("totalscore", Integer.toString(totalScore));
                        Log.d("totalscore2", intent2.getStringExtra("score"));
                        startActivity(intent2);
                        finish();
                    }
                }, 30000);
            }
        });





    }


}