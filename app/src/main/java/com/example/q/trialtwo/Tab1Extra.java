package com.example.q.trialtwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Tab1Extra extends AppCompatActivity {

    EditText info;
    Button btn, back;
    TextView received;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1extra);

        info = (EditText) findViewById(R.id.info);
        btn = (Button) findViewById(R.id.btn);
        back = (Button) findViewById(R.id.back);
        received = (TextView) findViewById(R.id.received);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                received.setText(info.getText());
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.constraintLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }



}
