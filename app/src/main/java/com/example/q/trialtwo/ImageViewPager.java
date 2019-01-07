package com.example.q.trialtwo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageViewPager extends Activity {
    // Declare Variable
    int position;
    private File[] files;
    private Uri[] uris;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set title for the ViewPager
        setTitle("ViewPager");
        // Get the view from view_pager.xml
        setContentView(R.layout.view_pager);

        // Retrieve data from MainActivity on item click event
        Intent p = getIntent();
        position = p.getExtras().getInt("id");

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
        } else {
            File dirPicture = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (dirPicture.isDirectory()) {
                files = dirPicture.listFiles();
                uris = new Uri[files.length];
                for (int i = 0; i < files.length; i++) {
                    String fileName = files[i].getName();
                    File file = new File(dirPicture, fileName);
                    uris[i] = Uri.fromFile(file);
                }
            }
        }

        ImageAdapter imageAdapter = new ImageAdapter(this, uris);
        List<ImageView> images = new ArrayList<>();

        // Retrieve all the images
        for (int i = 0; i < imageAdapter.getCount(); i++) {
            //ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
            ImageView imageView = new ImageView(this);
            RequestManager requestManager = Glide.with(imageAdapter.getContext());
            // Create request builder and load image.
            RequestBuilder requestBuilder = requestManager.load(imageAdapter.getItem(i));
            requestBuilder = requestBuilder.apply(new RequestOptions().override(250, 250));
            // Show image into target imageview.
            requestBuilder.into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            images.add(imageView);
        }

        // Set the images into ViewPager
        ImagePagerAdapter pageradapter = new ImagePagerAdapter(images);
        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        viewpager.setAdapter(pageradapter);
        // Show images following the position
        viewpager.setCurrentItem(position);
    }
}
