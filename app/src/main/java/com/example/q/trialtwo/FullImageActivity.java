package com.example.q.trialtwo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FullImageActivity extends AppCompatActivity {

    ImageView fullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        fullImage = (ImageView)findViewById(R.id.full_image);

        String data = getIntent().getExtras().getString("img");

        fullImage.setImageURI(Uri.parse(data));

        Button btn = (Button) findViewById(R.id.button0);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("MyApp", "This is a magic log message!");
                Toast.makeText(getApplicationContext(), "it's a magic!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}