package com.example.q.trialtwo;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

import static com.facebook.FacebookSdk.getApplicationContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import org.apache.commons.io.IOUtils;

public class Tab2Gallery extends Fragment {
    private File[] files;
    private Uri[] uris;
    public final int REQUEST_CODE = 1;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        // Set title for the GridView
        getActivity().setTitle("GridView");
        // Get the view from grid_view.xml
        view = inflater.inflate(R.layout.grid_view, container, false);
        //setContentView(R.layout.grid_view);
        loadOrRequestPermission();


        return view;
    }


    public String img2Text(){
        String base64="";
        try{
            InputStream iSteamReader = new FileInputStream("featured-700x467.png");
            byte[] imageBytes = IOUtils.toByteArray(iSteamReader);
            base64 = Base64.getEncoder().encodeToString(imageBytes);
            System.out.println(base64);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "data:image/png;base64,"+base64;
    }

    public void loadOrRequestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            doLoad();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    public void doLoad()
    {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getActivity(), "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
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

        // Set the images from ImageAdapter.java to GridView
        GridView gridview = view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity(), uris));

        // Listening to GridView item click
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Launch ImageViewPager.java on selecting GridView Item
                Intent i = new Intent(getApplicationContext(), ImageViewPager.class);

                // Send the click position to ImageViewPager.java using intent
                i.putExtra("id", position);

                // Start ImageViewPager
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch(requestCode)
        {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    doLoad();
                }
                else
                {
                    Toast.makeText(getActivity(), "Need to allow access!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
